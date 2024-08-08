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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.semicolon.EaziRent.utils.TestUtils.TEST_IMAGE_LOCATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = "/db/data.sql")
class PropertyControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ModelMapper modelMapper;

    @Test
    public void addPropertyTest() throws Exception {
        String token = getToken();
        try(InputStream inputStream = Files.newInputStream(Path.of(TEST_IMAGE_LOCATION))) {
            MultipartFile file = new MockMultipartFile("mediaFile", inputStream);
            mockMvc.perform(multipart("/api/v1/property/add")
                            .file(file.getName(), file.getBytes())
                            .part(new MockPart("noOfApartments", "25".getBytes()))
                            .part(new MockPart("type", "CONDO".getBytes()))
                            .part(new MockPart("addressRequest.number", "40".getBytes()))
                            .part(new MockPart("addressRequest.street", "street".getBytes()))
                            .part(new MockPart("addressRequest.area", "area".getBytes()))
                            .part(new MockPart("addressRequest.state", "FCT".getBytes()))
                            .part(new MockPart("addressRequest.lga", "lga".getBytes()))
                    .contentType(MULTIPART_FORM_DATA)
                    .header("Authorization", "Bearer " + token))
                    .andExpect(status().isCreated())
                    .andDo(print());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void findAllPropertiesTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/property/all")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }


    private String getToken() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setEmail("jamespalmer@gmail.com");
        request.setPassword("password");
        ObjectMapper objectMapper = new ObjectMapper();
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