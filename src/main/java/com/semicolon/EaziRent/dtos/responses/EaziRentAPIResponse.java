package com.semicolon.EaziRent.dtos.responses;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EaziRentAPIResponse<T> {
    private int code;
    private boolean status;
    private T data;
}
