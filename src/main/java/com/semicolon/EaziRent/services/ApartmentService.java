package com.semicolon.EaziRent.services;

import com.semicolon.EaziRent.dtos.requests.AddApartmentRequest;
import com.semicolon.EaziRent.dtos.responses.AddApartmentResponse;
import com.semicolon.EaziRent.dtos.responses.EaziRentAPIResponse;

import java.io.IOException;

public interface ApartmentService {
    EaziRentAPIResponse<AddApartmentResponse> addApartment(AddApartmentRequest request) throws IOException;
}
