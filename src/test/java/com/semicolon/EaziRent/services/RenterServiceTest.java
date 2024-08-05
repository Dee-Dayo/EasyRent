package com.semicolon.EaziRent.services;

import com.fasterxml.jackson.databind.node.TextNode;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import com.github.fge.jackson.jsonpointer.JsonPointerException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchOperation;
import com.github.fge.jsonpatch.ReplaceOperation;
import com.semicolon.EaziRent.data.repositories.ReviewRepository;
import com.semicolon.EaziRent.dtos.requests.RateUserRequest;
import com.semicolon.EaziRent.dtos.requests.RegisterRequest;
import com.semicolon.EaziRent.dtos.requests.UpdateRequest;
import com.semicolon.EaziRent.dtos.requests.UploadImageRequest;
import com.semicolon.EaziRent.dtos.responses.RateUserResponse;
import com.semicolon.EaziRent.dtos.responses.RegisterResponse;
import com.semicolon.EaziRent.dtos.responses.UpdateDataResponse;
import com.semicolon.EaziRent.exceptions.EasyRentBaseException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Sql(scripts = {"/db/data.sql"})
public class RenterServiceTest {
    @Autowired
    private RenterService renterService;

    @Autowired
    private ReviewRepository reviewRepository;

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
        request.setLandlordId(105L);
        request.setComment("understanding landlord");
        RateUserResponse response = renterService.reviewLandlord(request);
        assertThat(response).isNotNull();
        assertThat(response.getRenterId()).isEqualTo(200L);
        assertThat(renterService.getRenterReviews(200L)).size().isEqualTo(1);
        System.out.println(renterService.getRenterReviews(200L));

        RateUserRequest request2 = new RateUserRequest();
        request2.setRenterId(200L);
        request2.setRating(5);
        request2.setLandlordId(104L);
        request2.setComment("understanding landlord");
        RateUserResponse response2 = renterService.reviewLandlord(request2);
        assertThat(response2).isNotNull();
        assertThat(response2.getRenterId()).isEqualTo(200L);
        assertThat(renterService.getRenterReviews(200L)).size().isEqualTo(2);
        System.out.println(renterService.getRenterReviews(200L));
    }

    @Test
    public void ratePropertyTest(){

    }
}