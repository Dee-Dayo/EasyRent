package com.semicolon.EaziRent.dtos.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
public class AddApartmentResponse {
    @JsonFormat(pattern = "dd-MMMM-yyyy 'at' hh:ss a")
    private LocalDateTime responseTime;
    private Long propertyId;
    private String type;
    private String rentType;
    private BigDecimal price;
    private Integer number;
    private Boolean isAvailable;
    private Set<String> mediaUrls;
}
