package com.semicolon.EaziRent.services;

import com.semicolon.EaziRent.data.models.Renter;
import com.semicolon.EaziRent.data.models.Review;
import com.semicolon.EaziRent.dtos.requests.*;
import com.semicolon.EaziRent.dtos.responses.*;

import java.util.List;

public interface RenterService {

    RegisterResponse register(RegisterRequest request);

    UpdateDataResponse update(Long renterId, UpdateRequest request);

    Renter getRenterBy(String email);

    Renter findById(Long renterId);

    RateUserResponse reviewLandlord(ReviewUserRequest request);

    List<Review> getRenterReviews(Long renterId);

    ReviewListResponse getLandlordReviews(Long landlordId);


    ReviewPropertyResponse reviewProperty(ReviewPropertyRequest request);

    ReviewListResponse findPropertyReviews(Long propertyId);

    ReviewApartmentResponse reviewApartment(ReviewApartmentRequest request);

    ReviewListResponse getApartmentReviews(Long l);
}
