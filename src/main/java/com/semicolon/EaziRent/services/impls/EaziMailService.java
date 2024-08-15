package com.semicolon.EaziRent.services.impls;

import com.semicolon.EaziRent.dtos.requests.SendMailRequest;
import com.semicolon.EaziRent.services.MailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
@AllArgsConstructor
public class EaziMailService implements MailService {
    private final JavaMailSender mailSender;
    static String SENDER_EMAIL = "eazirent@gmail.com";

    @Override
    public void sendMail(SendMailRequest request) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);
            mimeMessageHelper.setFrom(SENDER_EMAIL);
            mimeMessageHelper.setTo(request.getRecipientEmail());
            mimeMessageHelper.setSubject("Registration confirmation!");
            Resource resource = new ClassPathResource("templates/registration_template.html");
            String htmlTemplate = Files.readString(Paths.get(resource.getURI()));
            String personalizedHtml = htmlTemplate.replace("{{recipientName}}", request.getRecipientName());
            mimeMessageHelper.setText(personalizedHtml, true);
            mailSender.send(mimeMessage);
        } catch (MessagingException | IOException e) {
            throw new RuntimeException(e);
        }
    }

}
