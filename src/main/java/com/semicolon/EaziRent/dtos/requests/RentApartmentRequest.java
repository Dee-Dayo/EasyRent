package com.semicolon.EaziRent.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RentApartmentRequest {
    private String renterEmail;
    private Long apartmentId;
}
