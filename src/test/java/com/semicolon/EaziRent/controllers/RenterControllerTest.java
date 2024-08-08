package com.semicolon.EaziRent.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @WithMockUser
    public void testUpdateRenter()throws Exception {
        String requestBody = "{\"firstName\": \"updated name\", \"occupation\": \"Business Man\"}";
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/renter/update{renterId}", 200)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.firstName").value("updated name"))
                .andExpect(jsonPath("$.data.lastName").value("John"))
                .andDo(print());
    }

    @Test
    public void testReviewProperty()throws Exception {
        String requestBody = "{\"propertyId\":\"500\", \"renterId\":\"201\", \"rating\":\"5\", \"comment\": \"very secure\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/renter/reviewProperty")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andDo(print());
    }
    @Test
    public void testReviewLandlord()throws Exception{
        String requestBody = "{\"landlordId\":\"300\", \"renterId\":\"201\", \"rating\":\"4\", \"comment\": \"understanding landlord\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/renter/reviewLandlord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    public void testGetLandlordReviews()throws Exception {
        createLandlordReview();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/renter/getLandlordReviews{landlordId}", 300)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testGetPropertyReviews()throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/renter/getPropertyReviews{propertyId}", 300)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
    @Test
    public void testGetApartmentReviews()throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/renter/getApartmentReviews{apartmentId}", 300)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    private void createLandlordReview() throws Exception {
        String requestBody = "{\"landlordId\":\"300\", \"renterId\":\"201\", \"rating\":\"5\", \"comment\": \"very secure\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/renter/reviewLandlord")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andDo(print());
    }



}