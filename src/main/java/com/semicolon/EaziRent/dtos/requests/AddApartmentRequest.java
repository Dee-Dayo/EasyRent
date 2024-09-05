package com.semicolon.EaziRent.dtos.requests;

import com.semicolon.EaziRent.data.constants.RentType;
import com.semicolon.EaziRent.data.constants.SubType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Getter
@Setter
public class AddApartmentRequest {
    private Long propertyId;
    private BigDecimal price;
    private SubType type;
    private RentType rentType;
    private Integer number;
    private MultipartFile mediaFile;
}
