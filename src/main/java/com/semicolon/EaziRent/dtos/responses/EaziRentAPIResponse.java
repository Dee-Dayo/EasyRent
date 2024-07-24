package com.semicolon.EaziRent.dtos.responses;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EaziRentAPIResponse<T> {
    private boolean status;
    private T data;
}
