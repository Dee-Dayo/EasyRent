package com.semicolon.EaziRent.services;

import com.semicolon.EaziRent.data.models.Renter;
import com.semicolon.EaziRent.data.models.Review;
import com.semicolon.EaziRent.dtos.requests.RateUserRequest;
import com.semicolon.EaziRent.dtos.requests.RegisterRequest;
import com.semicolon.EaziRent.dtos.requests.UpdateRequest;
import com.semicolon.EaziRent.dtos.responses.RateUserResponse;
import com.semicolon.EaziRent.dtos.responses.RegisterResponse;
import com.semicolon.EaziRent.dtos.responses.UpdateDataResponse;

import java.util.List;

public interface RenterService {

    RegisterResponse register(RegisterRequest request);

    UpdateDataResponse update(Long renterId, UpdateRequest request);

    Renter getRenterBy(String email);

    Renter findById(Long renterId);

    RateUserResponse rateLandlord(RateUserRequest request);

    List<Review> getRenterReviews(Long renterId);

    List<Review> getLandlordReviews(long landlordId);

    RateUserResponse reviewRenter(RateUserRequest request);

}
