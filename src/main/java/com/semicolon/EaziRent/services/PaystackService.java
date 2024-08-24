package com.semicolon.EaziRent.services;

import com.semicolon.EaziRent.dtos.responses.EaziRentAPIResponse;
import com.semicolon.EaziRent.dtos.responses.PaidRentResponse;

import java.io.IOException;

public interface PaystackService {

    EaziRentAPIResponse<String> initializePayment(String email, Long apartmentId) throws IOException;

    EaziRentAPIResponse<PaidRentResponse> verifyPayment(String reference, String email, Long apartmentId) throws IOException;

    String getBanks();
}
