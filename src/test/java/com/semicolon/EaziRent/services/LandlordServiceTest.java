package com.semicolon.EaziRent.services;

import com.semicolon.EaziRent.data.repositories.LandlordRepository;
import com.semicolon.EaziRent.dtos.requests.RegisterRequest;
import com.semicolon.EaziRent.dtos.responses.RegisterResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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
}