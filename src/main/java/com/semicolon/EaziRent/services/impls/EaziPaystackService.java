package com.semicolon.EaziRent.services.impls;

import com.semicolon.EaziRent.config.PaystackConfig;
import com.semicolon.EaziRent.data.models.Apartment;
import com.semicolon.EaziRent.data.models.Rent;
import com.semicolon.EaziRent.data.models.Renter;
import com.semicolon.EaziRent.dtos.responses.EaziRentAPIResponse;
import com.semicolon.EaziRent.dtos.responses.PaidRentResponse;
import com.semicolon.EaziRent.exceptions.EasyRentBaseException;
import com.semicolon.EaziRent.exceptions.UnsuccessfulTransactionException;
import com.semicolon.EaziRent.services.ApartmentService;
import com.semicolon.EaziRent.services.PaystackService;
import com.semicolon.EaziRent.services.RentService;
import com.semicolon.EaziRent.services.RenterService;
import lombok.AllArgsConstructor;
import okhttp3.*;
import org.cloudinary.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.math.BigDecimal;

import static com.semicolon.EaziRent.data.constants.PaymentOption.PAYSTACK;
import static java.time.LocalDateTime.now;

@Service
@AllArgsConstructor
public class EaziPaystackService implements PaystackService {
    private final PaystackConfig paystackConfig;
    private final ApartmentService apartmentService;
    private final RenterService renterService;
    private final RentService rentService;
    private final ModelMapper modelMapper;


    @Override
    public EaziRentAPIResponse<String> initializePayment(String email, Long apartmentId) throws IOException {
        Apartment apartment = apartmentService.getApartmentBy(apartmentId);
        validateStatusOf(apartment);
        OkHttpClient client = new OkHttpClient();
        JSONObject json = new JSONObject();
        json.put("email", email);
        String value = String.valueOf(apartment.getPrice().multiply(BigDecimal.valueOf(100)));
        json.put("amount", value);
        json.put("currency", "NGN");
        json.put("callback_url", "https://eazi-rent-eight.vercel.app/verify-payment/" + apartmentId);
        RequestBody body = RequestBody.create(json.toString(), MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url(paystackConfig.getUrl())
                .post(body)
                .addHeader("Authorization", "Bearer " + paystackConfig.getSecretKey())
                .addHeader("Content-Type", "application/json")
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new UnsuccessfulTransactionException("Payment unsuccessful: " + response);
            String paystackResponse = response.body().string();
            return new EaziRentAPIResponse<>(true, paystackResponse);
        }
    }


    @Override
    @Transactional
    public EaziRentAPIResponse<PaidRentResponse> verifyPayment(String reference, String email, Long apartmentId) throws IOException {
        Renter renter = renterService.getRenterBy(email);
        if(renter.getRent() != null)throw new EasyRentBaseException("In apartment "+renter.getRent().getApartment().getNumber());
        Apartment apartment = apartmentService.getApartmentBy(apartmentId);
        validateStatusOf(apartment);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(paystackConfig.getVerificationUrl() + reference)
                .get()
                .addHeader("Authorization", "Bearer " + paystackConfig.getSecretKey())
                .addHeader("Content-Type", "application/json")
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful())
                throw new UnsuccessfulTransactionException("Transaction verification failed: " + response);

            String paystackResponse = response.body().string();
            System.out.println("Paystack response: " + paystackResponse);
            validate(paystackResponse);
            Rent rent = getRent(apartment, renter);
            apartmentService.updateAndSave(apartment);
            PaidRentResponse paidRentResponse = buildPaidRentResponse(rent, paystackResponse, renter, apartment);
            return new EaziRentAPIResponse<>(true, paidRentResponse);
        }
    }

    @Override
    public String getBanks() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + paystackConfig.getSecretKey());
        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(
                "https://api.paystack.co/bank", HttpMethod.GET, entity, String.class);

        return response.getBody();
}

    private void validateStatusOf(Apartment apartment) {
        if (!apartment.getIsAvailable()) throw new IllegalStateException("Apartment is not available");
    }

    private static void validate(String paystackResponse) {
        JSONObject jsonResponse = new JSONObject(paystackResponse);
        String status = jsonResponse.getJSONObject("data").getString("status");
        System.out.println("status: " + status);
        if (!status.equals("success"))
            throw new UnsuccessfulTransactionException("Transaction verification failed: transaction " + status);
    }

    private PaidRentResponse buildPaidRentResponse(Rent rent, String paystackResponse, Renter renter, Apartment apartment) {
        PaidRentResponse paidRentResponse = modelMapper.map(rent, PaidRentResponse.class);
        paidRentResponse.setRentId(rent.getId());
        paidRentResponse.setPaystackResponse(paystackResponse);
        paidRentResponse.setResponseTime(now());
        paidRentResponse.setRenterId(renter.getId());
        paidRentResponse.setApartmentId(apartment.getId());
        return paidRentResponse;
    }

    private Rent getRent(Apartment apartment, Renter renter) {
        Rent rent = new Rent();
        rent.setApartment(apartment);
        rent.setRenter(renter);
        rent.setRentType(apartment.getRentType());
        rent.setPrice(apartment.getPrice());
        rent.setPaymentOption(PAYSTACK);
        rent = rentService.save(rent);
        renter.setRent(rent);
        return rent;
    }
}
