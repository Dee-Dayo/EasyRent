package com.semicolon.EaziRent.controllers;

import com.semicolon.EaziRent.services.LandlordService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/vi/landlord")
public class LandlordController {

    private final LandlordService landlordService;

//    @PostMapping
//    public
}
