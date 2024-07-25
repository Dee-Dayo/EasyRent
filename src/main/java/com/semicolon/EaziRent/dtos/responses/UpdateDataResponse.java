package com.semicolon.EaziRent.dtos.responses;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class UpdateDataResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDateTime dateUpdated;
}

