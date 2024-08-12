package com.semicolon.EaziRent.services;

import com.semicolon.EaziRent.dtos.requests.SendMailRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MailServiceTest {

    @Autowired
    private MailService mailService;

    @Test
    public void testSendMail() {
        SendMailRequest request = new SendMailRequest();
        request.setRecipientEmail("ameliageorge215@gmail.com");
        request.setRecipientName("Gagnon");
        var response = mailService.sendMail(request);
        assertThat(response).isNotNull();
    }
}