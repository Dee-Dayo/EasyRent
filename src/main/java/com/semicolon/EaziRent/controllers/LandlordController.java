package com.semicolon.EaziRent.controllers;

import com.semicolon.EaziRent.data.models.Renter;
import com.semicolon.EaziRent.dtos.requests.AddAccountDetailsRequest;
import com.semicolon.EaziRent.dtos.requests.RegisterRequest;
import com.semicolon.EaziRent.dtos.requests.ReviewUserRequest;
import com.semicolon.EaziRent.dtos.requests.UpdateRequest;
import com.semicolon.EaziRent.dtos.responses.EaziRentAPIResponse;
import com.semicolon.EaziRent.dtos.responses.RegisterResponse;
import com.semicolon.EaziRent.exceptions.EasyRentBaseException;
import com.semicolon.EaziRent.services.LandlordService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/landlord")
@CrossOrigin(origins = "*")
public class LandlordController {

    private final LandlordService landlordService;

    @PostMapping("/register")
    public ResponseEntity<?> registerLandlord(@RequestBody RegisterRequest request) {
        try {
            RegisterResponse response = landlordService.register(request);
            return new ResponseEntity<>(new EaziRentAPIResponse<>(true, response), CREATED);
        } catch (EasyRentBaseException error){
            return new ResponseEntity<>(new EaziRentAPIResponse<>(false, error.getMessage()), BAD_REQUEST);
        }
    }

    @PatchMapping("/update")
    public ResponseEntity<?> updateLandlordDetails(@RequestBody UpdateRequest request, Principal principal) {
        return ResponseEntity.ok(landlordService.update(request, principal.getName()));
    }

    @PostMapping("/review-renter")
    public ResponseEntity<?> reviewRenter(@RequestBody ReviewUserRequest request){
        return ResponseEntity.ok(landlordService.reviewRenter(request));
    }


    @PostMapping("/add-account-details")
    public ResponseEntity<?> addAccountDetails(@RequestBody AddAccountDetailsRequest request, Principal principal) {

        return ResponseEntity.status(CREATED).body(landlordService.addAccountDetails(request, principal.getName()));
    }
}
