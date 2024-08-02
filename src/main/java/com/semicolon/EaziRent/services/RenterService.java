package com.semicolon.EaziRent.services;

import com.semicolon.EaziRent.data.models.Renter;
import com.semicolon.EaziRent.dtos.requests.RegisterRequest;
import com.semicolon.EaziRent.dtos.requests.UpdateRequest;
import com.semicolon.EaziRent.dtos.responses.RegisterResponse;
import com.semicolon.EaziRent.dtos.responses.UpdateDataResponse;

public interface RenterService {

    RegisterResponse register(RegisterRequest request);

    UpdateDataResponse update(Long renterId, UpdateRequest request);

    Renter getRenterBy(String email);
}
