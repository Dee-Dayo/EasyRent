package com.semicolon.EaziRent.controllers;

import com.semicolon.EaziRent.services.PaystackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1/paystack")
public class PaystackController {

    private final PaystackService paystackService;

    @Autowired
    public PaystackController(PaystackService paystackService) {
        this.paystackService = paystackService;
    }

    @PostMapping("/pay")
    public ResponseEntity<?> pay(Principal principal, @RequestParam Long apartmentId) throws IOException {
        return ResponseEntity.ok(paystackService.initializePayment(principal.getName(), apartmentId));
    }

    @PostMapping("/verify/{reference}")
    public ResponseEntity<?> verify(@PathVariable String reference,
                                    Principal principal, @RequestParam Long apartmentId) throws IOException {
        return ResponseEntity.status(CREATED)
                .body(paystackService.verifyPayment(reference, principal.getName(), apartmentId));
    }

    @GetMapping("/banks")
    public ResponseEntity<String> getBanks() {
        return ResponseEntity.ok(paystackService.getBanks());
    }
}
