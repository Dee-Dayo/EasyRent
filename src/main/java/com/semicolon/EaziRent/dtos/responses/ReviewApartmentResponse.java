package com.semicolon.EaziRent.dtos.responses;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReviewApartmentResponse {
    private Long apartmentId;
    private Long renterId;
    private int rating;
    private String comment;
}
