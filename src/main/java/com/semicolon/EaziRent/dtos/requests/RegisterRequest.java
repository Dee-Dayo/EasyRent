package com.semicolon.EaziRent.dtos.requests;

import com.semicolon.EaziRent.data.constants.Role;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String occupation;
    private Role role;
}
