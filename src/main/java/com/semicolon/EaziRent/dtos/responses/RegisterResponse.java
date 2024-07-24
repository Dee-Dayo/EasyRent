package com.semicolon.EaziRent.dtos.responses;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class RegisterResponse {
    private Long id;
    private String message;
    private LocalDateTime dateCreated;

}
