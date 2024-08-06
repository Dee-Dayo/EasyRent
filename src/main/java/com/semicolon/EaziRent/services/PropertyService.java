package com.semicolon.EaziRent.services;

import com.semicolon.EaziRent.data.models.Property;
import com.semicolon.EaziRent.dtos.requests.AddPropertyRequest;
import com.semicolon.EaziRent.dtos.responses.AddPropertyResponse;
import com.semicolon.EaziRent.dtos.responses.EaziRentAPIResponse;

import java.io.IOException;

public interface PropertyService {
    EaziRentAPIResponse<AddPropertyResponse> addProperty(AddPropertyRequest request, String email) throws IOException;

    Property getPropertyBy(Long id);


}
