package com.semicolon.EaziRent.services.impls;

import com.semicolon.EaziRent.data.models.Property;
import com.semicolon.EaziRent.data.models.Rating;
import com.semicolon.EaziRent.data.models.Renter;
import com.semicolon.EaziRent.data.repositories.RateRepository;
import com.semicolon.EaziRent.dtos.requests.RatePropertyRequest;
import com.semicolon.EaziRent.dtos.responses.RatePropertyResponse;
import com.semicolon.EaziRent.services.LandlordService;
import com.semicolon.EaziRent.services.PropertyService;
import com.semicolon.EaziRent.services.RatingService;
import com.semicolon.EaziRent.services.RenterService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EaziRatingService implements RatingService {
    private final RateRepository rateRepository;
    private final RenterService renterService;
    private final LandlordService landlordService;
    private final PropertyService propertyService;

    @Autowired
    public EaziRatingService(
            RateRepository rateRepository,
            RenterService renterService,
            LandlordService landlordService, PropertyService propertyService, ModelMapper modelMapper){
        this.rateRepository = rateRepository;
        this.renterService = renterService;
        this.landlordService = landlordService;
        this.propertyService = propertyService;
    }

    @Override
    public RatePropertyResponse rateProperty(RatePropertyRequest request) {
        Renter renter = renterService.findById(request.getRenterId());
        Property property = propertyService.getPropertyBy(request.getPropertyId());
        Rating rating = new Rating();
        map(request, rating, renter, property);
        return mapResponse(property, renter, rating);
    }


    private static RatePropertyResponse mapResponse(Property property, Renter renter, Rating rating) {
        RatePropertyResponse response = new RatePropertyResponse();
        response.setPropertyId(property.getId());
        response.setRenterId(renter.getId());
        response.setPropertyRate(rating.getRating());
        return response;
    }

    private void map(RatePropertyRequest request, Rating rating, Renter renter, Property property) {
        rating.setRating(request.getRate());
        rating.setRenter(renter);
        rating.setProperty(property);
        rateRepository.save(rating);
    }

}
