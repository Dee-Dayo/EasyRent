package com.semicolon.EaziRent.controllers;

import com.semicolon.EaziRent.dtos.requests.AddApartmentRequest;
import com.semicolon.EaziRent.dtos.requests.GetApartmentRequest;
import com.semicolon.EaziRent.dtos.responses.ApartmentResponse;
import com.semicolon.EaziRent.dtos.responses.EaziRentAPIResponse;
import com.semicolon.EaziRent.dtos.responses.ListApartmentResponse;
import com.semicolon.EaziRent.exceptions.EasyRentBaseException;
import com.semicolon.EaziRent.services.ApartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping("/api/v1/apartment")
public class ApartmentController {
    private final ApartmentService apartmentService;

    @Autowired
    public ApartmentController(ApartmentService apartmentService) {
        this.apartmentService = apartmentService;
    }

    @PostMapping(path = "/add", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addApartment(@ModelAttribute AddApartmentRequest request) throws IOException {
        return ResponseEntity.status(CREATED).body(apartmentService.addApartment(request));
    }

    @GetMapping("/all{propertyId}")
    public ResponseEntity<?> allApartmentsFor(@PathVariable Long propertyId){
        ListApartmentResponse response = apartmentService.findAllFor(propertyId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("findBy{id}")
    public ResponseEntity<?> findApartmentById(@PathVariable Long id){
        try {
            ApartmentResponse response = apartmentService.findApartmentBy(id);
            return new ResponseEntity<>(new EaziRentAPIResponse<>(true,response),OK);
        }catch (EasyRentBaseException exception){
            return new ResponseEntity<>(new EaziRentAPIResponse<>(false,exception.getMessage()),BAD_REQUEST);
        }
    }
    @GetMapping("/filterApartment")
    public ResponseEntity<?> findApartmentByLocationAndSubType(GetApartmentRequest request){
        try{
            ListApartmentResponse response = apartmentService.findApartmentsBy(request);
        }
    }
}
