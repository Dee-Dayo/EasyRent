package com.semicolon.EaziRent.services;

import com.github.fge.jsonpatch.JsonPatch;
import com.semicolon.EaziRent.data.models.BioData;
import com.semicolon.EaziRent.dtos.requests.RegisterRequest;
import com.semicolon.EaziRent.dtos.responses.UpdateDataResponse;

public interface BioDataService {

    BioData register(RegisterRequest request);

    BioData getBioDataBy(String email);

    BioData findBioDataBy(Long bioDataId);

    UpdateDataResponse update(Long id, JsonPatch jsonPatch);
}
