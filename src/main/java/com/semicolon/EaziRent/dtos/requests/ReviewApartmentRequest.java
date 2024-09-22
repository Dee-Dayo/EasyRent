package com.semicolon.EaziRent.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReviewApartmentRequest {
    private Long apartmentId;
    private Long renterId;
    private int rating;
    private String comment;
}
