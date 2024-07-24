package com.semicolon.EaziRent.services;

import com.semicolon.EaziRent.data.models.BioData;
import com.semicolon.EaziRent.data.models.Renter;
import com.semicolon.EaziRent.data.repositories.RenterRepository;
import com.semicolon.EaziRent.dtos.requests.RegisterRequest;
import com.semicolon.EaziRent.dtos.responses.RegisterResponse;
import com.semicolon.EaziRent.exceptions.EazyRentBaseExceptions;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EaziRenterService implements RenterService{
    private final RenterRepository renterRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;


    @Override
    public RegisterResponse register(RegisterRequest request) {
        BioData data = modelMapper.map(request, BioData.class);
        data.setPassword(passwordEncoder.encode(request.getPassword()));
        Renter renter = modelMapper.map(request, Renter.class);
        renter.setBioData(data);
        renterRepository.save(renter);
        return modelMapper.map(renter, RegisterResponse.class);
    }
}
