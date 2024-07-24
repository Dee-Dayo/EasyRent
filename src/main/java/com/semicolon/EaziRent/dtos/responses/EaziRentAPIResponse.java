package com.semicolon.EaziRent.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class EaziRentAPIResponse<T> {
    private boolean status;
    private T data;
}
