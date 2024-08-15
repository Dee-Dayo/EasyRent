package com.semicolon.EaziRent.services;

import com.semicolon.EaziRent.dtos.requests.SendMailRequest;

import java.io.IOException;

public interface MailService {

    String sendMail(SendMailRequest request) throws IOException;
}
