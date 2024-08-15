package com.semicolon.EaziRent.services;

import com.semicolon.EaziRent.dtos.requests.SendMailRequest;

public interface MailService {

    String sendMail(SendMailRequest request);
}
