package com.semicolon.EaziRent.dtos.responses;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
public class RatePropertyResponse {
    private Long propertyId;
    private Long renterId;
    private int propertyRate;
}
