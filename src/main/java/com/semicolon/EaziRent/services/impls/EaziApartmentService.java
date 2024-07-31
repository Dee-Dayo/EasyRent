package com.semicolon.EaziRent.services.impls;

import com.semicolon.EaziRent.dtos.requests.AddApartmentRequest;
import com.semicolon.EaziRent.dtos.responses.AddApartmentResponse;
import com.semicolon.EaziRent.dtos.responses.EaziRentAPIResponse;
import org.springframework.stereotype.Service;

@Service
public class EaziApartmentService implements ApartmentService {
    @Override
    public EaziRentAPIResponse<AddApartmentResponse> addApartment(AddApartmentRequest request) {

        return null;
    }
}
