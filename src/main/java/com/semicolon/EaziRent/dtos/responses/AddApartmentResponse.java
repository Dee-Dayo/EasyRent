package com.semicolon.EaziRent.dtos.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
public class AddApartmentResponse {
    @JsonFormat(pattern = "dd-MMMM-yyyy 'at' hh:mm a")
    private LocalDateTime responseTime;
    @JsonProperty("apartmentId")
    private Long id;
    private Long propertyId;
    private String type;
    private String rentType;
    private BigDecimal price;
    private Integer number;
    private Boolean isAvailable;
    private Set<String> mediaUrls;
}
