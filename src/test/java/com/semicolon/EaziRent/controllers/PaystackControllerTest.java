package com.semicolon.EaziRent.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.semicolon.EaziRent.dtos.requests.LoginRequest;
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

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql("/db/data.sql")
public class PaystackControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ModelMapper modelMapper;

    @Test
    void pay() {
    }

    @Test
    public void verifyPaymentTest() throws Exception {
        String token = getToken();
        mockMvc.perform(post("/api/v1/paystack/verify/cufwe78a2b?apartmentId=800")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    private String getToken() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setEmail("colesam@gmail.com");
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