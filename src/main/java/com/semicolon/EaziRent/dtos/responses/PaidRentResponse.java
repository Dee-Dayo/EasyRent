package com.semicolon.EaziRent.dtos.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class PaidRentResponse {
    @JsonFormat(pattern = "dd-MMMM-yyyy 'at' hh:mm a")
    private LocalDateTime responseTime;
    private Long rentId;
    private Long apartmentId;
    private Long renterId;
    private String rentType;
    private BigDecimal price;
    private String paymentOption;
    private String paystackResponse;
}
