package com.semicolon.EaziRent.services;

import com.semicolon.EaziRent.data.repositories.ApartmentRepository;
import com.semicolon.EaziRent.data.repositories.PropertyRepository;
import com.semicolon.EaziRent.data.repositories.ReviewRepository;
import com.semicolon.EaziRent.dtos.requests.*;
import com.semicolon.EaziRent.dtos.responses.*;
import com.semicolon.EaziRent.exceptions.EasyRentBaseException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Sql(scripts = {"/db/data.sql"})
public class RenterServiceTest {
    @Autowired
    private RenterService renterService;

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private PropertyRepository propertyRepository;
    @Autowired
    private ApartmentRepository apartmentRepository;

    @Test
    public void testRegisterRenter(){
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
        RateUserRequest request = new RateUserRequest();
        request.setRenterId(200L);
        request.setRating(5);
        request.setLandlordId(301L);
        request.setComment("understanding landlord");
        RateUserResponse response = renterService.reviewLandlord(request);
        assertThat(response).isNotNull();
        assertThat(response.getRenterId()).isEqualTo(200L);
        assertThat(renterService.getLandlordReviews(301L)).size().isEqualTo(1);
        System.out.println(renterService.getLandlordReviews(301L));
    }

    @Test
    public void ratePropertyTest(){
        ReviewPropertyRequest request = new ReviewPropertyRequest();
        request.setPropertyId(500L);
        request.setRenterId(200L);
        request.setRating(5);
        request.setComment("good conditions");
        ReviewPropertyResponse response = renterService.reviewProperty(request);
        assertThat(response).isNotNull();
        System.out.println(renterService.findPropertyReviews(500L).size());
        assertThat(renterService.findPropertyReviews(500L).size()).isEqualTo(3);
    }


    @Test
    public void reviewApartmentTest(){
        ReviewApartmentRequest request = new ReviewApartmentRequest();
        request.setPropertyId(500L);
        request.setApartmentId(800L);
        request.setRenterId(200L);
        request.setRating(5);
        request.setComment("good conditions");
        ReviewApartmentResponse response = renterService.reviewApartment(request);
        assertThat(response).isNotNull();
        assertThat(renterService.getApartmentReviews(800L).size()).isEqualTo(1);
    }

}