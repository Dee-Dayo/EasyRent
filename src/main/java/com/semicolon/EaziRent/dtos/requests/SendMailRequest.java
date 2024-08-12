package com.semicolon.EaziRent.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SendMailRequest {
    private String recipientEmail;
    private String recipientName;
}
