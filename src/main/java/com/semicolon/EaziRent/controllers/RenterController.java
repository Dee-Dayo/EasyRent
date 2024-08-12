package com.semicolon.EaziRent.controllers;

import com.semicolon.EaziRent.data.models.Review;
import com.semicolon.EaziRent.dtos.requests.*;
import com.semicolon.EaziRent.dtos.responses.*;
import com.semicolon.EaziRent.exceptions.EasyRentBaseException;
import com.semicolon.EaziRent.services.RenterService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
       }
       catch (EasyRentBaseException exception) {
           return new ResponseEntity<>(new EaziRentAPIResponse<>(false, exception.getMessage()), BAD_REQUEST);
       }
    }
    @PostMapping("/reviewLandlord")
    public ResponseEntity<?> reviewLandlord(@RequestBody ReviewUserRequest request){
        try{
            RateUserResponse response = renterService.reviewLandlord(request);
            return new ResponseEntity<>(new EaziRentAPIResponse<>(true,response), OK);
        }
        catch (EasyRentBaseException exception){
            return new ResponseEntity<>(new EaziRentAPIResponse<>(false, exception.getMessage()), BAD_REQUEST);
        }
    }

    @GetMapping("/getLandlordReviews{landlordId}")
    public ResponseEntity<?> getLandlordReviews(@PathVariable("landlordId") Long landlordId){
        try{
            ReviewListResponse reviews = renterService.getLandlordReviews(landlordId);
            return new ResponseEntity<>(new EaziRentAPIResponse<>(true, reviews), OK);
        }
        catch (EasyRentBaseException exception){
            return new ResponseEntity<>(new EaziRentAPIResponse<>(false, exception.getMessage()), BAD_REQUEST);
        }
    }

    @GetMapping("/getPropertyReviews{propertyId}")
    public ResponseEntity<?> getPropertyReviews(@PathVariable("propertyId") Long propertyId){
        try{
            ReviewListResponse reviews = renterService.findPropertyReviews(propertyId);
            return new ResponseEntity<>(new EaziRentAPIResponse<>(true, reviews), OK);
        }
        catch (EasyRentBaseException exception){
            return new ResponseEntity<>(new EaziRentAPIResponse<>(false, exception.getMessage()), BAD_REQUEST);
        }
    }
    @GetMapping("/getApartmentReviews{apartmentId}")
    public ResponseEntity<?> getApartmentReviews(@PathVariable Long apartmentId){
        try{
            ReviewListResponse reviews = renterService.getApartmentReviews(apartmentId);
            return new ResponseEntity<>(new EaziRentAPIResponse<>(true, reviews), OK);
        }
        catch (EasyRentBaseException exception){
            return new ResponseEntity<>(new EaziRentAPIResponse<>(false, exception.getMessage()), BAD_REQUEST);
        }
    }

}
