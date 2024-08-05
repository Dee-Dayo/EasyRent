package com.semicolon.EaziRent.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RatePropertyRequest {
    private Long propertyId;
    private Long renterId;
    private int rate;
    private String comment;
}
