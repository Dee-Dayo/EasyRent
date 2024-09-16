package com.semicolon.EaziRent.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReviewApartmentRequest {
    private Long propertyId;
    private Long apartmentId;
    private String email;
    private int rating;
    private String comment;
}
