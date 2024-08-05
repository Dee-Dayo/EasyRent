package com.semicolon.EaziRent.services;

import com.semicolon.EaziRent.data.models.Review;
import com.semicolon.EaziRent.dtos.requests.RateUserRequest;
import com.semicolon.EaziRent.dtos.requests.RatePropertyRequest;
import com.semicolon.EaziRent.dtos.responses.RateUserResponse;
import com.semicolon.EaziRent.dtos.responses.RatePropertyResponse;

import java.util.List;

public interface ReviewService {

    RatePropertyResponse rateProperty(RatePropertyRequest request);

    RateUserResponse rateRenter(RateUserRequest request);


    List<Review> getRenterReviews(Long renterId);

    List<Review> findLandlordReviews(Long landlordId);

    List<Review> getPropertyReviews(Long propertyId);
}
