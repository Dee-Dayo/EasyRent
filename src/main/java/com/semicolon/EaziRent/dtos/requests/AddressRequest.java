package com.semicolon.EaziRent.dtos.requests;

import com.semicolon.EaziRent.data.constants.State;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressRequest {
    private String number;
    private String street;
    private String area;
    private State state;
    private String lga;
}
