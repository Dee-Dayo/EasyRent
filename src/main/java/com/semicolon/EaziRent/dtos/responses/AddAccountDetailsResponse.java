package com.semicolon.EaziRent.dtos.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AddAccountDetailsResponse {
    @JsonFormat(pattern = "dd-MMMM-yyyy 'at' hh:ss a")
    private LocalDateTime responseTime;
    @JsonProperty("landlord_id")
    private Long id;
    private String accountNumber;
    private String accountName;
    private String bankName;
}
