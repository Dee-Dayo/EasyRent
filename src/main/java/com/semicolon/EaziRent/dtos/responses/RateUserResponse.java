package com.semicolon.EaziRent.dtos.responses;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RateUserResponse {
    private Long renterId;
    private Long landLordId;
    private int rating;
    private String comment;
}
