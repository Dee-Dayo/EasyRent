package com.semicolon.EaziRent.dtos.responses;

import com.semicolon.EaziRent.data.models.Apartment;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ViewApartmentResponse {
    private List<Apartment> apartments;
}
