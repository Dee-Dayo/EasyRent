package com.semicolon.EaziRent.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddAgentRequest {
    private String name;
    private String phoneNumber;
}
