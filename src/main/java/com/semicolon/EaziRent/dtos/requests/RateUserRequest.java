package com.semicolon.EaziRent.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RateUserRequest {
    private Long landlordId;
    private Long renterId;
    private int rate;
    private String comment;
}
