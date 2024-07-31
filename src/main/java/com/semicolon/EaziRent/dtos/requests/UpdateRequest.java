package com.semicolon.EaziRent.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateRequest {
    private String firstName;
    private String lastName;
    private String password;
    private String occupation;
}
