package com.semicolon.EaziRent.services;

import com.semicolon.EaziRent.data.models.BioData;
import com.semicolon.EaziRent.dtos.requests.RegisterRequest;

public interface BioDataService {

    BioData register(RegisterRequest request);

    BioData getBioDataBy(String email);
}
