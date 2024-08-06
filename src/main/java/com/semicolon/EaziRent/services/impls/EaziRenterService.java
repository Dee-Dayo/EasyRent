package com.semicolon.EaziRent.services.impls;

import com.semicolon.EaziRent.data.models.*;
import com.semicolon.EaziRent.data.repositories.RenterRepository;
import com.semicolon.EaziRent.data.repositories.ReviewRepository;
import com.semicolon.EaziRent.dtos.requests.*;
import com.semicolon.EaziRent.dtos.responses.*;
import com.semicolon.EaziRent.exceptions.ResourceNotFoundException;
import com.semicolon.EaziRent.exceptions.UserNotFoundException;
import com.semicolon.EaziRent.services.*;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    @Transactional
    public RegisterResponse register(RegisterRequest request) {
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
    @Transactional
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
    public RateUserResponse reviewLandlord(RateUserRequest request) {
        Landlord landlord = landlordService.findLandlordById(request.getLandlordId());
        Renter renter = findById(request.getRenterId());
        BioData reviewer = bioDataService.findBioDataBy(landlord.getBioData().getId());
        BioData reviewee = bioDataService.findBioDataBy(renter.getBioData().getId());
        Review review = map(request, reviewer, reviewee);
        reviewRepository.save(review);
        return map(review, landlord, renter);
    }

    private @NotNull Review map(RateUserRequest request, BioData reviewer, BioData reviewee) {
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
    public List<Review> getRenterReviews(Long renterId) {
        Renter renter = findById(renterId);
        return reviewRepository.findRenterReviews(renter.getBioData().getId());
    }

    @Override
    public List<Review> getLandlordReviews(Long landlordId) {
        return landlordService.findLandlordReviews(landlordId);
    }

    @Override
    public RatePropertyResponse reviewProperty(ReviewPropertyRequest request) {
        Property property = propertyService.getPropertyBy(request.getPropertyId());
        Renter renter = findById(request.getRenterId());
        BioData reviewer = bioDataService.findBioDataBy(renter.getBioData().getId());
        Review review = modelMapper.map(request, Review.class);
        review.setProperty(property);
        review.setReviewer(reviewer);
        reviewRepository.save(review);
        return map(review);
    }

    @Override
    public List<Review> findPropertyReviews(Long propertyId) {
        return reviewRepository.findPropertyReviews(propertyId);
    }

    @Override
    public ReviewApartmentResponse reviewApartment(ReviewApartmentRequest request) {
        Property property = propertyService.getPropertyBy(request.getPropertyId());
        Apartment apartment = apartmentService.getApartmentBy(request.getApartmentId());
        Renter renter = findById(request.getRenterId());
        BioData reviewer = bioDataService.findBioDataBy(renter.getBioData().getId());
        //validateExistingApartment(property, apartment);
        Review review = map(request, apartment, reviewer);
        return map(renter, review);
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

    private void validateExistingApartment(Property property, Apartment apartment) {
        List<Apartment> propertyApartments = apartmentService.findPropertyApartments(property.getId());
        if(!propertyApartments.contains(apartment))
            throw new ResourceNotFoundException("no such apartment for this property");
    }

    @Override
    public List<Review> getApartmentReviews(Long apartmentId) {
        return reviewRepository.findApartmentReviews(apartmentId);
    }

    private @NotNull RatePropertyResponse map(Review review) {
        RatePropertyResponse response = modelMapper.map(review, RatePropertyResponse.class);
        response.setPropertyId(review.getProperty().getId());
        response.setRenterId(review.getReviewer().getId());
        return response;
    }

    @Override
    public Renter getRenterBy(String email) {
        BioData bioData = bioDataService.getBioDataBy(email);
        return renterRepository.findRenterBy(bioData.getId())
                .orElseThrow(()-> new ResourceNotFoundException("Renter not found with email: " + email));
    }

}
