package com.semicolon.EaziRent.services;

import com.semicolon.EaziRent.data.constants.RentType;
import com.semicolon.EaziRent.dtos.requests.AddApartmentRequest;
import com.semicolon.EaziRent.dtos.requests.GetApartmentRequest;
import com.semicolon.EaziRent.dtos.responses.AddApartmentResponse;
import com.semicolon.EaziRent.dtos.responses.EaziRentAPIResponse;
import com.semicolon.EaziRent.dtos.responses.ListApartmentResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.io.IOException;

import static com.semicolon.EaziRent.data.constants.State.LAGOS;
import static com.semicolon.EaziRent.data.constants.SubType.ONE_ROOM;
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
    public void findAllForApartment(){
        ListApartmentResponse apartments = apartmentService.findAllFor(500L);
        assertThat(apartments.getApartments().size()).isEqualTo(5);
    }

    @Test
    public void filterApartmentTest(){
        GetApartmentRequest request = new GetApartmentRequest();
        request.setState(LAGOS);
        request.setType(ONE_ROOM);
        request.setRentType(RentType.MONTHLY);
        ListApartmentResponse response = apartmentService.findApartmentsBy(request);
        assertThat(response).isNotNull();
    }

}