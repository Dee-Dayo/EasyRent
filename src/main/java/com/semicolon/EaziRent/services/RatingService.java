package com.semicolon.EaziRent.services;

import com.semicolon.EaziRent.dtos.requests.RatePropertyRequest;
import com.semicolon.EaziRent.dtos.responses.RatePropertyResponse;

public interface RatingService {

    RatePropertyResponse rateProperty(RatePropertyRequest request);

}
