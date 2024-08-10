package com.semicolon.EaziRent.controllers;

import com.semicolon.EaziRent.dtos.requests.AddApartmentRequest;
import com.semicolon.EaziRent.dtos.requests.RentApartmentRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockPart;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import static com.semicolon.EaziRent.utils.TestUtils.buildAddApartmentRequest;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = "/db/data.sql")
public class ApartmentControllerTest {
    @Autowired
    private MockMvc mockMvc;


    @Test
    @WithMockUser(authorities = "LANDLORD")
    public void addApartmentTest() throws Exception {
        AddApartmentRequest request = buildAddApartmentRequest();
        MultipartFile file = request.getMediaFile();
        mockMvc.perform(multipart("/api/v1/apartment")
                        .file(file.getName(), file.getBytes())
                        .part(new MockPart("propertyId", "500".getBytes()))
                        .part(new MockPart("number", "25".getBytes()))
                        .part(new MockPart("type", request.getType().toString().getBytes()))
                        .part(new MockPart("rentType", request.getRentType().toString().getBytes()))
                        .part(new MockPart("price", request.getPrice().toString().getBytes()))
                        .contentType(MULTIPART_FORM_DATA))
                .andExpect(status().isCreated())
                .andDo(print());
    }





}