package com.semicolon.EaziRent.services;

import com.semicolon.EaziRent.data.models.Landlord;
import com.semicolon.EaziRent.data.repositories.LandlordRepository;
import com.semicolon.EaziRent.dtos.requests.AddAccountDetailsRequest;
import com.semicolon.EaziRent.dtos.requests.ReviewUserRequest;
import com.semicolon.EaziRent.dtos.requests.RegisterRequest;
import com.semicolon.EaziRent.dtos.requests.UpdateRequest;
import com.semicolon.EaziRent.dtos.responses.*;
import com.semicolon.EaziRent.exceptions.InvalidDataException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.io.IOException;

import static com.semicolon.EaziRent.utils.TestUtils.buildUpdateRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Sql(scripts = {"/db/data.sql"})
public class LandlordServiceTest {
    @Autowired
    private LandlordService landlordService;
    @Autowired
    private LandlordRepository landlordRepository;

    @Test
    public void testRegisterLandlord() throws IOException {
        RegisterRequest request = new RegisterRequest();
        request.setFirstName("first name");
        request.setLastName("last name");
        request.setEmail("sampleemail@email.com");
        request.setPassword("password");
        RegisterResponse response = landlordService.register(request);
        assertThat(response).isNotNull();
        assertThat(response.getId()).isNotNull();
        assertThat(landlordRepository.count()).isEqualTo(4);
    }

    @Test
    public void testUpdateLandlordBioData() {
        UpdateRequest request = buildUpdateRequest("updatedFirstName", "updatedLastName");
        String email = "jamespalmer@gmail.com";
        Landlord landlord = landlordService.findLandlordBy(email);
        assertBioData(landlord, "Palmer", "James");
        EaziRentAPIResponse<UpdateDataResponse> response = landlordService.update(request, email);
        assertThat(response).isNotNull();
        landlord = landlordService.findLandlordBy(email);
        assertBioData(landlord, "updatedFirstName", "updatedLastName");
    }

    @Test
    public void testAddAccountDetails() {
        AddAccountDetailsRequest request = new AddAccountDetailsRequest();
        request.setAccountName("accountName");
        request.setAccountNumber("1234567890");
        request.setBankName("bankName");
        String email = "jamespalmer@gmail.com";
        EaziRentAPIResponse<AddAccountDetailsResponse> response = landlordService.addAccountDetails(request, email);
        assertThat(response).isNotNull();
        assertThat(response.getData().getAccountNumber()).isNotNull();
    }

    @Test
    public void testThatAccountNumberContainsOnly10DigitNumbers() {
        AddAccountDetailsRequest request = new AddAccountDetailsRequest();
        request.setAccountNumber("12345678901");
        request.setBankName("bankName");
        String email = "jamespalmer@gmail.com";
        assertThrows(InvalidDataException.class, ()-> landlordService.addAccountDetails(request, email));
        request.setAccountNumber("12345S6789");
        assertThrows(InvalidDataException.class, ()-> landlordService.addAccountDetails(request, email));
        request.setAccountNumber("123456789");
        assertThrows(InvalidDataException.class, ()-> landlordService.addAccountDetails(request, email));
    }

    @Test
    public void reviewLandlordTest(){
        ReviewUserRequest request = new ReviewUserRequest();
        request.setLandlordId(104L);
        request.setRenterId(201L);
        request.setRating(5);
        request.setComment("good tenant");
        RateUserResponse response = landlordService.reviewRenter(request);
        assertThat(response.getRating()).isEqualTo(5);
        assertThat(response.getRenterId()).isEqualTo(201);
        assertThat(landlordService.findRenterReviews(201L).size()).isEqualTo(1);
    }
    private void assertBioData(Landlord landlord, String expectedFirstName, String expectedLastName) {
        assertThat(landlord.getBioData().getFirstName()).isEqualTo(expectedFirstName);
        assertThat(landlord.getBioData().getLastName()).isEqualTo(expectedLastName);
    }

}