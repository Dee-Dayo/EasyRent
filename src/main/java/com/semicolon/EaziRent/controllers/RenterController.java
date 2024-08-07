package com.semicolon.EaziRent.controllers;

import com.semicolon.EaziRent.dtos.requests.RegisterRequest;
import com.semicolon.EaziRent.dtos.requests.ReviewPropertyRequest;
import com.semicolon.EaziRent.dtos.requests.UpdateRequest;
import com.semicolon.EaziRent.dtos.responses.EaziRentAPIResponse;
import com.semicolon.EaziRent.dtos.responses.ReviewPropertyResponse;
import com.semicolon.EaziRent.dtos.responses.RegisterResponse;
import com.semicolon.EaziRent.dtos.responses.UpdateDataResponse;
import com.semicolon.EaziRent.exceptions.EasyRentBaseException;
import com.semicolon.EaziRent.services.RenterService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/v1/renter")
@AllArgsConstructor
public class RenterController {
    private final RenterService renterService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        try{
            RegisterResponse response = renterService.register(request);
            return new ResponseEntity<>(new EaziRentAPIResponse<>(true, response), CREATED);
        } catch (EasyRentBaseException exception) {
            return new ResponseEntity<>(new EaziRentAPIResponse<>(false, exception.getMessage()), BAD_REQUEST);
        }
    }

    @PatchMapping("/update{renterId}")
    public ResponseEntity<?> update(@PathVariable("renterId") Long renterId, @RequestBody UpdateRequest request) {
        try{
            UpdateDataResponse response = renterService.update(renterId, request);
            return new ResponseEntity<>(new EaziRentAPIResponse<>(true, response), OK);
        }
        catch (EasyRentBaseException exception) {
            return new ResponseEntity<>(new EaziRentAPIResponse<>(false, exception.getMessage()), BAD_REQUEST);
        }
    }
    @PostMapping("/reviewProperty")
    public ResponseEntity<?> reviewProperty(@RequestBody ReviewPropertyRequest request) {
       try {
           ReviewPropertyResponse response = renterService.reviewProperty(request);
           return new ResponseEntity<>(new EaziRentAPIResponse<>(true, response), OK);
       }catch (EasyRentBaseException exception) {
           return new ResponseEntity<>(new EaziRentAPIResponse<>(false, exception.getMessage()), BAD_REQUEST);
       }
    }
}
