package com.semicolon.EaziRent.dtos.requests;

import com.semicolon.EaziRent.data.constants.RentType;
import com.semicolon.EaziRent.data.constants.State;
import com.semicolon.EaziRent.data.constants.SubType;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GetApartmentRequest {
    private State state;
    private SubType subType;
    private RentType rentType;
}
