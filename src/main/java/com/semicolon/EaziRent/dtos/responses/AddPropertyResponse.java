package com.semicolon.EaziRent.dtos.responses;

import com.semicolon.EaziRent.data.constants.Type;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class AddPropertyResponse {
    private LocalDateTime responseTime;
    private String message;
    private Long landlordId;
    private Type type;
    private int noOfApartments;
    private AddressResponse address;
    private String mediaUrl;
}
