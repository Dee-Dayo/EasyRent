package com.semicolon.EaziRent.services.impls;

import com.semicolon.EaziRent.config.MailConfig;
import com.semicolon.EaziRent.dtos.requests.BrevoMaiRequest;
import com.semicolon.EaziRent.dtos.requests.Recipient;
import com.semicolon.EaziRent.dtos.requests.SendMailRequest;
import com.semicolon.EaziRent.dtos.requests.Sender;
import com.semicolon.EaziRent.dtos.responses.BrevoMailResponse;
import com.semicolon.EaziRent.services.MailService;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
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
    public String sendMail(SendMailRequest sendMailRequest) {
        RestTemplate restTemplate = new RestTemplate();
        String url = mailConfig.getMailApiUrl();
        String registrationTemplate = loadHtmlTemplate();
        String processedTemplate = registrationTemplate.replace("[Recipient Name]", sendMailRequest.getRecipientName());
        BrevoMaiRequest request = new BrevoMaiRequest();
        request.setSubject("registration successful");
        request.setSender(new Sender());
        request.setRecipients(
                List.of(
                        new Recipient(sendMailRequest.getRecipientEmail(), sendMailRequest.getRecipientName())
                ));
        request.setContent(processedTemplate);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        headers.set("api-key", mailConfig.getMailApiKey());
        headers.set("accept", APPLICATION_JSON.toString());
        RequestEntity<?> httpRequest
                = new RequestEntity<>(request,headers, HttpMethod.POST, URI.create(url));
        ResponseEntity<BrevoMailResponse> response
                = restTemplate.postForEntity(url, httpRequest, BrevoMailResponse.class);
        if(response.getBody() != null
            && response.getStatusCode() == HttpStatusCode.valueOf(201))
            return " registration mail sent successfully";
        return null;
    }

    private String loadHtmlTemplate() {
        try {
            return new String(Files.readAllBytes(
                    Paths.get("C:\\Users\\DELL\\IdeaProjects\\EasyRent\\src\\main\\resources\\templates\\registration_template.html",
                            "registration_template.html")));
        }
        catch (IOException exception) {
            return "";
        }
    }
}
