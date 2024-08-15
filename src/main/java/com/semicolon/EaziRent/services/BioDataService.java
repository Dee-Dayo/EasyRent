package com.semicolon.EaziRent.services;

import com.semicolon.EaziRent.data.models.BioData;
import com.semicolon.EaziRent.dtos.requests.RegisterRequest;
import com.semicolon.EaziRent.dtos.requests.UpdateRequest;
import com.semicolon.EaziRent.dtos.responses.UpdateDataResponse;

import java.io.IOException;

public interface BioDataService {

    BioData register(RegisterRequest request) throws IOException;

    BioData getBioDataBy(String email);

    BioData findBioDataBy(Long bioDataId);

    UpdateDataResponse update(Long id, UpdateRequest request);

    BioData update(BioData bioData, UpdateRequest request);

    BioData save(BioData reviewee);
}
