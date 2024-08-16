package com.semicolon.EaziRent.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReviewPropertyRequest {
    private Long propertyId;
    private int rating;
    private String comment;
    private String email;
}
