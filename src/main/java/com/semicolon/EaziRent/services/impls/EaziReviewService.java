package com.semicolon.EaziRent.services.impls;

import com.semicolon.EaziRent.data.models.*;
import com.semicolon.EaziRent.data.repositories.ReviewRepository;
import com.semicolon.EaziRent.dtos.requests.RateUserRequest;
import com.semicolon.EaziRent.dtos.requests.RatePropertyRequest;
import com.semicolon.EaziRent.dtos.responses.RateUserResponse;
import com.semicolon.EaziRent.dtos.responses.RatePropertyResponse;
import com.semicolon.EaziRent.services.*;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EaziReviewService implements ReviewService {
    private final ReviewRepository reviewRepository;
    private RenterService renterService;
    private LandlordService landlordService;
    private PropertyService propertyService;
    private final ModelMapper modelMapper;
    private final BioDataService bioDataService;

    @Autowired
    public EaziReviewService(
            ReviewRepository reviewRepository, ModelMapper modelMapper, BioDataService bioDataService){
        this.reviewRepository = reviewRepository;
        this.modelMapper = modelMapper;
        this.bioDataService = bioDataService;
    }
    @Autowired
    @Lazy
    public void setRenterService(RenterService renterService) {
        this.renterService = renterService;
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

    @Override
    public RatePropertyResponse rateProperty(RatePropertyRequest request) {
        Renter renter = renterService.findById(request.getRenterId());
        BioData reviewer = bioDataService.findBioDataBy(renter.getBioData().getId());
        Property property = propertyService.getPropertyBy(request.getPropertyId());
        Review review = new Review();
        modelMapper.map(request, Review.class);
        review.setReviewer(reviewer);
        review.setProperty(property);
        reviewRepository.save(review);
        return mapResponse(property, renter, review);
    }

    private Renter findRenter(Long id) {
        return renterService.findById(id);
    }

    @Override
    public RateUserResponse rateRenter(RateUserRequest request) {
        Landlord landlord = landlordService.findLandlordById(request.getLandlordId());
        Renter renter = findRenter(request.getRenterId());
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
        Renter renter = renterService.findById(renterId);
        return reviewRepository.finAllReviewsFor(renter.getBioData().getId());
    }

    @Override
    public List<Review> findLandlordReviews(Long landlordId) {
        return List.of();
    }

    @Override
    public List<Review> getPropertyReviews(Long propertyId) {
        return reviewRepository.findAllReviewsFor(propertyId);
    }


    private static RatePropertyResponse mapResponse(Property property, Renter renter, Review review) {
        RatePropertyResponse response = new RatePropertyResponse();
        response.setPropertyId(property.getId());
        response.setRenterId(renter.getId());
        response.setPropertyRate(review.getRating());
        return response;
    }

}
