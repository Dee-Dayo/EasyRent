package com.semicolon.EaziRent.services;

import com.semicolon.EaziRent.dtos.requests.RateUserRequest;
import com.semicolon.EaziRent.dtos.requests.RatePropertyRequest;
import com.semicolon.EaziRent.dtos.responses.RateUserResponse;
import com.semicolon.EaziRent.dtos.responses.RatePropertyResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ReviewServiceTest {
    @Autowired
    private ReviewService reviewService;

    @Test
    public void ratePropertyTest(){
        RatePropertyRequest request = new RatePropertyRequest();
        request.setPropertyId(500L);
        request.setRenterId(200L);
        request.setRate(5);
        RatePropertyResponse response = reviewService.rateProperty(request);
        System.out.println(response);
        assertThat(response.getPropertyId()).isEqualTo(500L);
        assertThat(response).isNotNull();
    }

    @Test
    public void rateLandLordTest(){
        RateUserRequest request = new RateUserRequest();
        request.setRenterId(200L);
        request.setRate(5);
        request.setLandlordId(104L);
        request.setComment("very understanding landlord");
        RateUserResponse response = reviewService.rateRenter(request);
        assertThat(response).isNotNull();
        assertThat(response.getRenterId()).isEqualTo(200L);
    }

}