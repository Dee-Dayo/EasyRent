package com.semicolon.EaziRent.services;

import com.semicolon.EaziRent.data.models.Renter;
import com.semicolon.EaziRent.dtos.requests.*;
import com.semicolon.EaziRent.dtos.responses.*;

import java.io.IOException;

public interface RenterService {

    RegisterResponse register(RegisterRequest request) throws IOException;

    UpdateDataResponse update(Long renterId, UpdateRequest request);

    Renter getRenterBy(String email);

    Renter findById(Long renterId);

    RateUserResponse reviewLandlord(ReviewUserRequest request);

    ReviewListResponse getRenterReviews(Long renterId);

    ReviewListResponse getLandlordReviews(Long landlordId);

    ReviewPropertyResponse reviewProperty(ReviewPropertyRequest request);

    ReviewListResponse findPropertyReviews(Long propertyId);

    RenterResponse findByEmail(FindRenterRequest request);
}
