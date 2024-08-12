package com.semicolon.EaziRent.services;

import com.semicolon.EaziRent.data.models.Landlord;
import com.semicolon.EaziRent.data.models.Review;
import com.semicolon.EaziRent.dtos.requests.AddAccountDetailsRequest;
import com.semicolon.EaziRent.dtos.requests.ReviewUserRequest;
import com.semicolon.EaziRent.dtos.requests.RegisterRequest;
import com.semicolon.EaziRent.dtos.requests.UpdateRequest;
import com.semicolon.EaziRent.dtos.responses.*;

import java.util.List;

public interface LandlordService {
    RegisterResponse register(RegisterRequest request);
    Landlord findLandlordBy(String email);
    EaziRentAPIResponse<UpdateDataResponse> update(UpdateRequest request, String email);

    EaziRentAPIResponse<AddAccountDetailsResponse> addAccountDetails(AddAccountDetailsRequest request, String email);

    Landlord findLandlordById(Long landlordId);

    List<Review> findLandlordReviews(Long landlordId);
    RateUserResponse reviewRenter(ReviewUserRequest request);

    List<Review> findRenterReviews(Long renterId);

    Landlord findBy(Long landlordId);
}
