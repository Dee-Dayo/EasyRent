package com.semicolon.EaziRent.services.impls;

import com.semicolon.EaziRent.data.models.*;
import com.semicolon.EaziRent.data.repositories.RenterRepository;
import com.semicolon.EaziRent.data.repositories.ReviewRepository;
import com.semicolon.EaziRent.dtos.requests.*;
import com.semicolon.EaziRent.dtos.responses.*;
import com.semicolon.EaziRent.exceptions.EasyRentBaseException;
import com.semicolon.EaziRent.exceptions.ResourceNotFoundException;
import com.semicolon.EaziRent.exceptions.UserNotFoundException;
import com.semicolon.EaziRent.services.*;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.semicolon.EaziRent.data.constants.Role.RENTER;

@Service
public class EaziRenterService implements RenterService {
    private final RenterRepository renterRepository;
    private final ModelMapper modelMapper;
    private final BioDataService bioDataService;
    private LandlordService landlordService;
    private final ReviewRepository reviewRepository;
    private PropertyService propertyService;
    private ApartmentService apartmentService;


    public EaziRenterService(RenterRepository renterRepository,
                             ModelMapper modelMapper,
                             BioDataService bioDataService,
                             ReviewRepository reviewRepository) {
        this.renterRepository = renterRepository;
        this.modelMapper = modelMapper;
        this.bioDataService = bioDataService;
        this.reviewRepository = reviewRepository;
    }

    @Autowired
    @Lazy
    public void setLandlordService(LandlordService landlordService) {
        this.landlordService = landlordService;
    }

    @Autowired
    @Lazy
    public void setPropertyService(PropertyService propertyService) {
        this.propertyService = propertyService;
    }
    @Autowired
    public void setApartmentService(ApartmentService apartmentService) {
        this.apartmentService = apartmentService;
    }

    @Override
    public RegisterResponse register(RegisterRequest request) throws IOException {
        request.setRole(RENTER);
        BioData data = bioDataService.register(request);
        Renter renter = modelMapper.map(request, Renter.class);
        renter.setOccupation(request.getOccupation());
        renter.setBioData(data);
        renter = renterRepository.save(renter);
        RegisterResponse response = modelMapper.map(renter, RegisterResponse.class);
        response.setMessage("Renter successfully registered");
        response.setDateCreated(data.getDateRegistered());
        return response;
    }


    @Override
    public UpdateDataResponse update(Long renterId, UpdateRequest request) {
        Renter renter = renterRepository.findById(renterId).orElseThrow();
        renter.setOccupation(request.getOccupation());
        BioData bioData = renter.getBioData();
        UpdateDataResponse response = bioDataService.update(bioData.getId(), request);
        bioData = bioDataService.findBioDataBy(bioData.getId());
        renter.setBioData(bioData);
        renterRepository.save(renter);
        return response;
    }

    @Override
    public Renter findById(Long renterId) {
        return renterRepository.findById(renterId)
                .orElseThrow(()->new UserNotFoundException("renter not found"));
    }

    @Override
    public RateUserResponse reviewLandlord(ReviewUserRequest request) {
        Landlord landlord = landlordService.findBy(request.getLandlordId());
        Renter renter = findById(request.getRenterId());

        BioData reviewee = bioDataService.findBioDataBy(landlord.getBioData().getId());
        BioData reviewer = bioDataService.findBioDataBy(renter.getBioData().getId());
        Review review = map(request, reviewer, reviewee);
        reviewRepository.save(review);
        review = reviewRepository.save(review);
        reviewee.getReviews().add(review);
        bioDataService.save(reviewee);
        return map(review, landlord, renter);
    }

    private @NotNull Review map(ReviewUserRequest request, BioData reviewer, BioData reviewee) {
        Review review = modelMapper.map(request, Review.class);
        review.setReviewer(reviewer);
        review.setReviewee(reviewee);
        return review;
    }

    private @NotNull RateUserResponse map(Review review, Landlord landlord, Renter renter) {
        RateUserResponse response = modelMapper.map(review, RateUserResponse.class);
        response.setLandLordId(landlord.getId());
        response.setRenterId(renter.getId());
        return response;
    }

    @Override
    public ReviewListResponse getRenterReviews(Long renterId) {
        Renter renter = findById(renterId);
        Long bioDataId = renter.getBioData().getId();
        List<Review>reviews = reviewRepository.findRenterReviews(bioDataId);
        List<ReviewResponse> responses =reviews
                .stream().map(ReviewResponse::new)
                .collect(Collectors.toList());
        ReviewListResponse response = new ReviewListResponse();
        response.setReviews(responses);
        return response;
    }

    @Override
    public ReviewListResponse getLandlordReviews(Long landlordId) {
        List<Review>reviews = landlordService.findLandlordReviews(landlordId);
        return mapReviewResponses(reviews);
    }

    @Override
    @Transactional
    public ReviewPropertyResponse reviewProperty(ReviewPropertyRequest request) {
        Property property = propertyService.getPropertyBy(request.getPropertyId());
        Renter renter = getRenterBy(request.getEmail());
        BioData reviewer = renter.getBioData();
        Review review = modelMapper.map(request, Review.class);
        review.setProperty(property);
        review.setReviewer(reviewer);
        review = reviewRepository.save(review);
        property.getReviews().add(review);
        propertyService.save(property);
        return map(review);
    }

    @Override
    public ReviewListResponse findPropertyReviews(Long propertyId) {
        List<Review>reviews = reviewRepository.findPropertyReviews(propertyId);
        return mapReviewResponses(reviews);
    }

    @Override
    public ReviewApartmentResponse reviewApartment(ReviewApartmentRequest request) {
        propertyService.getPropertyBy(request.getPropertyId());
        Apartment apartment = apartmentService.getApartmentBy(request.getApartmentId());
        Renter renter = findById(request.getRenterId());
        if(!apartment.getRenter().getId().equals(renter.getId()))
            throw new EasyRentBaseException("review when you rent the apartment");
        BioData reviewer = bioDataService.findBioDataBy(renter.getBioData().getId());
        Review review = map(request, apartment, reviewer);
        return map(renter, review);
    }

    @Override
    public Renter getRenterBy(String email) {
        BioData bioData = bioDataService.getBioDataBy(email);
        return renterRepository.findRenterBy(bioData.getId())
                .orElseThrow(()-> new ResourceNotFoundException("Renter not found with email: " + email));
    }

    @Override
    public ReviewListResponse getApartmentReviews(Long apartmentId) {
        List<Review> reviews = reviewRepository.findApartmentReviews(apartmentId);
        return mapReviewResponses(reviews);
    }

    private static @NotNull ReviewListResponse mapReviewResponses(List<Review> reviews) {
        List<ReviewResponse> reviewResponses
                = reviews.stream().map(ReviewResponse::new)
                .collect(Collectors.toList());
        ReviewListResponse response = new ReviewListResponse();
        response.setReviews(reviewResponses);
        return response;
    }


    private @NotNull ReviewPropertyResponse map(Review review) {
        ReviewPropertyResponse response = modelMapper.map(review, ReviewPropertyResponse.class);
        response.setPropertyId(review.getProperty().getId());
        response.setRenterId(review.getReviewer().getId());
        response.setPropertyRate(review.getRating());
        String comment = review.getComment() != null ? review.getComment() : "";
        response.setComment(comment);
        return response;
    }


    private @NotNull ReviewApartmentResponse map(Renter renter, Review review) {
        ReviewApartmentResponse response = modelMapper.map(renter, ReviewApartmentResponse.class);
        response.setApartmentId(review.getApartment().getId());
        response.setRenterId(review.getReviewer().getId());
        return response;
    }

    private @NotNull Review map(ReviewApartmentRequest request, Apartment apartment, BioData reviewer) {
        Review review = modelMapper.map(request, Review.class);
        review.setApartment(apartment);
        review.setReviewer(reviewer);
        reviewRepository.save(review);
        return review;
    }


}
