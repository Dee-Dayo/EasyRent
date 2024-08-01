package com.semicolon.EaziRent.services;

import com.semicolon.EaziRent.dtos.responses.EaziRentAPIResponse;
import com.semicolon.EaziRent.dtos.responses.PaidRentResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Sql(scripts = "/db/data.sql")
public class PaystackServiceTest {

    @Autowired
    private PaystackService paystackService;

    @Test
    public void initializePaymentWithPaystackTest() throws IOException {
        String email = "orisakwenwokocha1@gmail.com";
        EaziRentAPIResponse<String> response = paystackService.initializePayment(email, 800L);
        assertThat(response).isNotNull();
        System.out.println(response.getData());
        assertThat(response.getData()).isNotNull();
    }

    @Test
    public void verifyPaymentWithPaystackTest() throws IOException {
        String email = "colesam@gmail.com";
        String reference = "cufwe78a2b";
        EaziRentAPIResponse<PaidRentResponse> response =
                paystackService.verifyPayment(reference, email, 800L);
        assertThat(response).isNotNull();
        System.out.println(response.getData());
        assertThat(response.getData().getPaystackResponse()).isNotNull();
    }
}