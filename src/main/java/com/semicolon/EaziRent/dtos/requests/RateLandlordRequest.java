package com.semicolon.EaziRent.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RateLandlordRequest {
    private Long landlordId;
    private Long renterId;
    private int rate;
}
