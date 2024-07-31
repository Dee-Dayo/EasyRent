package com.semicolon.EaziRent.dtos.responses;

import com.semicolon.EaziRent.data.constants.SubType;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddApartmentResponse {
    private Long propertyId;
    private Long apartmentId;
    private boolean isAvailable;
    private SubType subType;
}
