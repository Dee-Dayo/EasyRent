package com.semicolon.EaziRent.services;

import com.semicolon.EaziRent.data.models.Landlord;
import com.semicolon.EaziRent.dtos.requests.RegisterRequest;
import com.semicolon.EaziRent.dtos.responses.RegisterResponse;

public interface LandlordService {
    RegisterResponse register(RegisterRequest request);
    Landlord findLandlordBy(String email);
}
