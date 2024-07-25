package com.semicolon.EaziRent.dtos.responses;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AddressResponse {
    private String number;
    private String street;
    private String area;
    private String state;
    private String lga;
}
