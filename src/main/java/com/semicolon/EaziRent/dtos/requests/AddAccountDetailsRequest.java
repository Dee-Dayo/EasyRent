package com.semicolon.EaziRent.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddAccountDetailsRequest {
    private String accountNumber;
    private String accountName;
    private String bankName;
}
