package com.semicolon.EaziRent.services;

import com.semicolon.EaziRent.dtos.requests.RateLandlordRequest;
import com.semicolon.EaziRent.dtos.requests.RatePropertyRequest;
import com.semicolon.EaziRent.dtos.responses.RateLandResponse;
import com.semicolon.EaziRent.dtos.responses.RatePropertyResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class RatingServiceTest {
    @Autowired
    private RatingService ratingService;

    @Test
    public void ratePropertyTest(){
        RatePropertyRequest request = new RatePropertyRequest();
        request.setPropertyId(500L);
        request.setRenterId(200L);
        request.setRate(5);
        RatePropertyResponse response = ratingService.rateProperty(request);
        assertThat(response.getPropertyId()).isEqualTo(500L);
        assertThat(response).isNotNull();
    }

    @Test
    public void rateLandLordTest(){
        RateLandlordRequest request = new RateLandlordRequest();
        request.setRenterId(200L);
        request.setRate(5);
        request.setLandlordId(104L);
        RateLandResponse response = ratingService.rateLandlord(request);

    }

}