package com.semicolon.EaziRent.controllers;

import com.semicolon.EaziRent.dtos.requests.AddApartmentRequest;
import com.semicolon.EaziRent.dtos.requests.GetApartmentRequest;
import com.semicolon.EaziRent.dtos.responses.ApartmentResponse;
import com.semicolon.EaziRent.dtos.responses.EaziRentAPIResponse;
import com.semicolon.EaziRent.dtos.responses.ListApartmentResponse;
import com.semicolon.EaziRent.exceptions.EasyRentBaseException;
import com.semicolon.EaziRent.services.ApartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping("/api/v1/apartment")
@Slf4j
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
    public ResponseEntity<?> findApartmentByLocationAndSubType(@RequestBody GetApartmentRequest request){
        try{
            ListApartmentResponse response = apartmentService.findApartmentsBy(request);
            return new ResponseEntity<>(new EaziRentAPIResponse<>(true, response), OK);
        }
        catch (EasyRentBaseException exception){
            return new ResponseEntity<>(new EaziRentAPIResponse<>(false, exception.getMessage()), BAD_REQUEST);
        }
    }

    @PostMapping(path = "/upload-media", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadImages(@ModelAttribute List<MultipartFile> mediaFiles,
                                          @RequestParam Long id, Principal principal) throws IOException {
        return ResponseEntity.status(CREATED).body(apartmentService.uploadMedia(mediaFiles, id, principal.getName()));
    }
}
