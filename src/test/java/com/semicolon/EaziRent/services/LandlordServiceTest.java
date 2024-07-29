package com.semicolon.EaziRent.services;

import com.semicolon.EaziRent.data.models.Landlord;
import com.semicolon.EaziRent.data.repositories.LandlordRepository;
import com.semicolon.EaziRent.dtos.requests.RegisterRequest;
import com.semicolon.EaziRent.dtos.requests.UpdateRequest;
import com.semicolon.EaziRent.dtos.responses.EaziRentAPIResponse;
import com.semicolon.EaziRent.dtos.responses.RegisterResponse;
import com.semicolon.EaziRent.dtos.responses.UpdateDataResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static com.semicolon.EaziRent.utils.TestUtils.buildUpdateRequest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Sql(scripts = {"/db/data.sql"})
public class LandlordServiceTest {
    @Autowired
    private LandlordService landlordService;
    @Autowired
    private LandlordRepository landlordRepository;

    @Test
    public void testRegisterLandlord(){
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

    private void assertBioData(Landlord landlord, String expectedFirstName, String expectedLastName) {
        assertThat(landlord.getBioData().getFirstName()).isEqualTo(expectedFirstName);
        assertThat(landlord.getBioData().getLastName()).isEqualTo(expectedLastName);
    }

}