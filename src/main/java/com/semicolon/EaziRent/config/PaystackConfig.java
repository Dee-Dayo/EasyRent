package com.semicolon.EaziRent.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class PaystackConfig {
    @Value("${paystack.api.secret}")
    private String secretKey;
    @Value("${paystack.api.url}")
    private String url;
    @Value("${paystack.api.url.verify}")
    private String verificationUrl;
}
