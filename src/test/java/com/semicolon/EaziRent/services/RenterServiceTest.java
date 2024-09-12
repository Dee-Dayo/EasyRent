package com.semicolon.EaziRent.services;

import com.semicolon.EaziRent.data.models.Property;
import com.semicolon.EaziRent.dtos.requests.*;
import com.semicolon.EaziRent.dtos.responses.*;
import com.semicolon.EaziRent.exceptions.EasyRentBaseException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Sql(scripts = {"/db/data.sql"})
public class RenterServiceTest {
    @Autowired
    private RenterService renterService;

    @Autowired
    private PropertyService propertyService;

    @Test
    public void testRegisterRenter() throws IOException {
        RegisterRequest request = new RegisterRequest();
        request.setFirstName("first name");
        request.setLastName("last name");
        request.setEmail("sampleemail@email.com");
        request.setPassword("password");
        RegisterResponse response = renterService.register(request);
        assertThat(response).isNotNull();
        assertThat(response.getId()).isNotNull();
    }

    @Test
    public void registerRenterWithWrongEmailFormat_throwsException(){
        RegisterRequest request = new RegisterRequest();
        request.setFirstName("first name");
        request.setLastName("last name");
        request.setEmail("sampleemailemail.com");
        request.setPassword("password");
        assertThrows(EasyRentBaseException.class, ()->renterService.register(request));
    }

    @Test
    public void updateRenterDetailsTest() {
        UpdateRequest request = new UpdateRequest();
        request.setFirstName("updated name");
        request.setLastName("last name");
        request.setOccupation("Banker");

        UpdateDataResponse response = renterService.update(200L, request);
        assertThat(response.getFirstName()).isEqualTo("updated name");
        assertThat(response.getLastName()).isEqualTo("last name");
    }
    @Test
    public void rateLandlordTest(){
        ReviewUserRequest request = new ReviewUserRequest();
        request.setRenterId(200L);
        request.setRating(5);
        request.setLandlordId(301L);
        request.setComment("understanding landlord");
        RateUserResponse response = renterService.reviewLandlord(request);
        assertThat(response).isNotNull();
        assertThat(response.getRenterId()).isEqualTo(200L);
    }

    @Test
    public void ratePropertyTest(){
        ReviewPropertyRequest request = new ReviewPropertyRequest();
        request.setPropertyId(500L);
        request.setEmail("colesam@gmail.com");
        request.setRating(4);
        request.setComment("trying it");

        Property property = propertyService.getPropertyBy(500L);
        assertThat(property.getReviews().size()).isEqualTo(3);

        ReviewPropertyResponse response = renterService.reviewProperty(request);
        assertThat(response).isNotNull();
        property = propertyService.getPropertyBy(500L);
        assertThat(property.getReviews().size()).isEqualTo(4);
    }




}