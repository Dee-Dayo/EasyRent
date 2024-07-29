package com.semicolon.EaziRent.services;

import com.semicolon.EaziRent.data.models.Landlord;
import com.semicolon.EaziRent.dtos.requests.RegisterRequest;
import com.semicolon.EaziRent.dtos.requests.UpdateRequest;
import com.semicolon.EaziRent.dtos.responses.EaziRentAPIResponse;
import com.semicolon.EaziRent.dtos.responses.RegisterResponse;
import com.semicolon.EaziRent.dtos.responses.UpdateDataResponse;

public interface LandlordService {
    RegisterResponse register(RegisterRequest request);
    Landlord findLandlordBy(String email);
    EaziRentAPIResponse<UpdateDataResponse> update(UpdateRequest request, String email);
}
