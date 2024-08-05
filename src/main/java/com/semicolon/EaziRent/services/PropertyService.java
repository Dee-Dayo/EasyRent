package com.semicolon.EaziRent.services;

import com.semicolon.EaziRent.data.models.Property;
import com.semicolon.EaziRent.data.models.Review;
import com.semicolon.EaziRent.dtos.requests.AddPropertyRequest;
import com.semicolon.EaziRent.dtos.requests.RatePropertyRequest;
import com.semicolon.EaziRent.dtos.responses.AddPropertyResponse;
import com.semicolon.EaziRent.dtos.responses.EaziRentAPIResponse;
import com.semicolon.EaziRent.dtos.responses.RatePropertyResponse;
import com.semicolon.EaziRent.dtos.responses.RateUserResponse;

import java.io.IOException;
import java.util.List;

public interface PropertyService {
    EaziRentAPIResponse<AddPropertyResponse> addProperty(AddPropertyRequest request, String email) throws IOException;

    Property getPropertyBy(Long id);

    RatePropertyResponse reviewProperty(RatePropertyRequest request);

    List<Review> getPropertyReviews(long propertyId);
}
