package com.semicolon.EaziRent.dtos.responses;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReviewPropertyResponse {
    private Long propertyId;
    private Long renterId;
    private int propertyRate;
}
