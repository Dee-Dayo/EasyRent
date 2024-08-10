package com.semicolon.EaziRent.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.semicolon.EaziRent.dtos.requests.*;
import com.semicolon.EaziRent.dtos.responses.EaziRentAPIResponse;
import com.semicolon.EaziRent.dtos.responses.LoginResponse;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static com.semicolon.EaziRent.utils.TestUtils.buildUpdateRequest;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Sql(scripts = {"/db/data.sql"})
@AutoConfigureMockMvc
public class LandlordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ModelMapper modelMapper;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testRegisterLandlordController() throws Exception {
        RegisterRequest request = new RegisterRequest();
        request.setFirstName("first name");
        request.setLastName("last name");
        request.setEmail("sampleemail@email.com");
        request.setPassword("password");
        mockMvc.perform(post("/api/v1/landlord/register")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    public void testUpdateLandlordDetails() throws Exception {
        UpdateRequest updateRequest = buildUpdateRequest("john", "doe");
        String token = getToken();
        mockMvc.perform(patch("/api/v1/landlord/update")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(updateRequest))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.firstName").value("john"))
                .andExpect(jsonPath("$.data.lastName").value("doe"))
                .andDo(print());
    }

    @Test
    public void reviewRenterTest() throws Exception {
        ReviewUserRequest request = new ReviewUserRequest();
        request.setRenterId(201L);
        request.setLandlordId(300L);
        request.setRating(4);
        request.setComment("stubborn tenant");
        mockMvc.perform(post("/api/v1/landlord/review-tenant")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void addAccountDetailsTest() throws Exception {
        AddAccountDetailsRequest request = new AddAccountDetailsRequest();
        request.setAccountName("accountName");
        request.setAccountNumber("1234567890");
        request.setBankName("bankName");
        String token = getToken();
        mockMvc.perform(post("/api/v1/landlord/add-account-details")
                        .content(objectMapper.writeValueAsBytes(request))
                        .contentType(APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    private String getToken() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setEmail("jamespalmer@gmail.com");
        request.setPassword("password");
        byte[] content = objectMapper.writeValueAsBytes(request);
        MvcResult result = mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk())
                .andReturn();
        EaziRentAPIResponse<?> response = objectMapper.readValue(
                result.getResponse().getContentAsByteArray(), EaziRentAPIResponse.class);
        LoginResponse loginResponse = modelMapper.map(response.getData(), LoginResponse.class);
        return loginResponse.getToken();
    }



}