package com.semicolon.EaziRent.services;

import com.semicolon.EaziRent.data.models.BioData;
import com.semicolon.EaziRent.data.repositories.BioDataRepository;
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
public class EaziBioDataService implements BioDataService{
    private BioDataRepository bioDataRepository;
    private ModelMapper modelMapper;
    private PasswordEncoder passwordEncoder;

    @Override
    public RegisterResponse register(RegisterRequest request) {
        validateEmail(request);
        BioData bioData = modelMapper.map(request, BioData.class);
        bioData.setPassword(passwordEncoder.encode(bioData.getPassword()));
        bioDataRepository.save(bioData);
        RegisterResponse response = modelMapper.map(bioData, RegisterResponse.class);
        response.setMessage("Successfully registered");
        return response;
    }

    @Override
    public BioData findByEmail(String email) {
        return bioDataRepository.findByEmail(email);
    }

    private void validateEmail(RegisterRequest request) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        String emailRegex2 = "([a-z]\\.)?[a-z]+@(semicolon|enum|learnspace|native.semicolon).africa";
        if (!request.getEmail().matches(emailRegex) && !request.getEmail().matches(emailRegex2))
            throw new EazyRentBaseExceptions("Invalid email format");
        validateExistingEmail(request);
    }

    private void validateExistingEmail(RegisterRequest request) {
        BioData bioData = findByEmail(request.getEmail());
        if (bioData != null)
            throw new EazyRentBaseExceptions(request.getEmail()+"already exists");
    }
}
