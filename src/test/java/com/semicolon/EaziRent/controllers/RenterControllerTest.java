package com.semicolon.EaziRent.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = {"/db/data.sql"})
public class RenterControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testRegisterRenter() throws Exception {
        String requestBody = "{\"firstname\": \"first name\",\"lastname\": \"last name\",\"email\": \"newemail@email.com\", \"password\":\"password\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/renter/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isCreated());
    }


}