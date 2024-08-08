package com.semicolon.EaziRent.services;

import com.semicolon.EaziRent.data.models.Apartment;
import com.semicolon.EaziRent.dtos.requests.AddApartmentRequest;
import com.semicolon.EaziRent.dtos.responses.AddApartmentResponse;
import com.semicolon.EaziRent.dtos.responses.EaziRentAPIResponse;
import com.semicolon.EaziRent.dtos.responses.ViewApartmentResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.io.IOException;
import java.util.List;

import static com.semicolon.EaziRent.utils.TestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Sql(scripts = "/db/data.sql")
public class ApartmentServiceTest {

    @Autowired
    private ApartmentService apartmentService;

    @Test
    public void addApartmentTest() throws IOException {
        AddApartmentRequest request = buildAddApartmentRequest();
        EaziRentAPIResponse<AddApartmentResponse> response = apartmentService.addApartment(request);
        assertThat(response).isNotNull();
        assertThat(response.getData().getIsAvailable()).isTrue();
        assertThat(response.getData().getMediaUrls()).isNotEmpty();
    }

    @Test
    public void findAllApartment(){
        ViewApartmentResponse apartments = apartmentService.findAll();
        assertThat(apartments.getApartments().size()).isEqualTo(5);
    }

}