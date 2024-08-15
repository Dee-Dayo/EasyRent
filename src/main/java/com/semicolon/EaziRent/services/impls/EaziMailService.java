package com.semicolon.EaziRent.services.impls;

import com.semicolon.EaziRent.config.MailConfig;
import com.semicolon.EaziRent.dtos.requests.BrevoMailRequest;
import com.semicolon.EaziRent.dtos.requests.Recipient;
import com.semicolon.EaziRent.dtos.requests.SendMailRequest;
import com.semicolon.EaziRent.dtos.requests.Sender;
import com.semicolon.EaziRent.dtos.responses.BrevoMailResponse;
import com.semicolon.EaziRent.services.MailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
@AllArgsConstructor
public class EaziMailService implements MailService {
    private final MailConfig mailConfig;

    @Override
    public String sendMail(SendMailRequest sendMailRequest) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        String url = mailConfig.getMailApiUrl();
        BrevoMailRequest request = new BrevoMailRequest();
        request.setSubject("Registration successful");
        Resource resource = new ClassPathResource("templates/registration_template.html");
        String htmlTemplate = Files.readString(Paths.get(resource.getURI()));
        String personalizedHtml = htmlTemplate.replace("{{recipientName}}", sendMailRequest.getRecipientName());
        request.setSender(new Sender());
        request.setRecipients(
                List.of(
                        new Recipient(sendMailRequest.getRecipientEmail(), sendMailRequest.getRecipientName())
                ));
        request.setContent(personalizedHtml);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        headers.set("api-key", mailConfig.getMailApiKey());
        headers.set("accept", APPLICATION_JSON.toString());
        RequestEntity<?> httpRequest
                = new RequestEntity<>(request, headers, HttpMethod.POST, URI.create(url));
        ResponseEntity<BrevoMailResponse> response = restTemplate.postForEntity(url, httpRequest, BrevoMailResponse.class);
        if(response.getBody() != null
                && response.getStatusCode()== HttpStatusCode.valueOf(201))
            return "mail sent successfully";
        else throw new RuntimeException("email not sent");

    }

}
