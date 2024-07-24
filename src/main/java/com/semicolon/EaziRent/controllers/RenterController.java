package com.semicolon.EaziRent.controllers;

import com.semicolon.EaziRent.dtos.requests.RegisterRequest;
import com.semicolon.EaziRent.dtos.responses.RegisterResponse;
import com.semicolon.EaziRent.exceptions.EasyRentBaseException;
import com.semicolon.EaziRent.services.RenterService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/renter")
@AllArgsConstructor
public class RenterController {
    private final RenterService renterService;

    @PostMapping
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try{
            RegisterResponse response = renterService.register(request);
            return ResponseEntity.ok(response);
        } catch (EasyRentBaseException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }
}
