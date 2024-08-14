package com.semicolon.EaziRent.services;

import com.semicolon.EaziRent.data.models.Property;
import com.semicolon.EaziRent.data.models.Review;
import com.semicolon.EaziRent.dtos.requests.AddPropertyRequest;
import com.semicolon.EaziRent.dtos.responses.AddPropertyResponse;
import com.semicolon.EaziRent.dtos.responses.EaziRentAPIResponse;
import com.semicolon.EaziRent.dtos.responses.PropertyResponse;
import com.semicolon.EaziRent.dtos.responses.ViewPropertyResponse;

import java.io.IOException;
import java.util.List;

public interface PropertyService {
    EaziRentAPIResponse<AddPropertyResponse> addProperty(AddPropertyRequest request, String email) throws IOException;

    Property getPropertyBy(Long id);

    ViewPropertyResponse findAll();

    void addReview(Property property, Review review);

    PropertyResponse findBy(Long id);

    Property save(Property property);
}
