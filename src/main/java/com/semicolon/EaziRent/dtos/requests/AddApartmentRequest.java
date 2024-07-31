package com.semicolon.EaziRent.dtos.requests;

import com.semicolon.EaziRent.data.constants.SubType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class AddApartmentRequest {
    private Long propertyId;
    private BigDecimal price;
    private SubType subType;
}
