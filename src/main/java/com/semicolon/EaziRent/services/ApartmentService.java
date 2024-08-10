package com.semicolon.EaziRent.services;

import com.semicolon.EaziRent.data.models.Apartment;
import com.semicolon.EaziRent.dtos.requests.AddApartmentRequest;
import com.semicolon.EaziRent.dtos.requests.RentApartmentRequest;
import com.semicolon.EaziRent.dtos.responses.AddApartmentResponse;
import com.semicolon.EaziRent.dtos.responses.EaziRentAPIResponse;
import com.semicolon.EaziRent.dtos.responses.RentApartmentResponse;

import java.io.IOException;
import java.util.List;

public interface ApartmentService {
    EaziRentAPIResponse<AddApartmentResponse> addApartment(AddApartmentRequest request) throws IOException;

    Apartment getApartmentBy(Long id);


}
