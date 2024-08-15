package com.semicolon.EaziRent.services;

import com.semicolon.EaziRent.dtos.requests.SendMailRequest;

public interface MailService {

    void sendMail(SendMailRequest request);
}
