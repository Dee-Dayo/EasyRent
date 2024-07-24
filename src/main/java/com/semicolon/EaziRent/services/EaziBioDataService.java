package com.semicolon.EaziRent.services;

import com.semicolon.EaziRent.data.models.BioData;
import com.semicolon.EaziRent.data.repositories.BioDataRepository;
import com.semicolon.EaziRent.dtos.requests.RegisterRequest;
import com.semicolon.EaziRent.exceptions.EasyRentBaseException;
import com.semicolon.EaziRent.exceptions.EmailExistsException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EaziBioDataService implements BioDataService{
    private final BioDataRepository bioDataRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public BioData register(RegisterRequest request) {
        validateEmail(request);
        BioData bioData = modelMapper.map(request, BioData.class);
        bioData.setPassword(passwordEncoder.encode(bioData.getPassword()));
        return bioDataRepository.save(bioData);
    }

    private void validateEmail(RegisterRequest request) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        String emailRegex2 = "([a-z]\\.)?[a-z]+@(semicolon|enum|learnspace|native.semicolon).africa";
        if (!request.getEmail().matches(emailRegex) && !request.getEmail().matches(emailRegex2))
            throw new EasyRentBaseException("Invalid email format");
        validateExistingEmail(request);
    }

    private void validateExistingEmail(RegisterRequest request) {
        boolean emailExists = bioDataRepository.existsByEmail(request.getEmail());
        if (emailExists) throw new EmailExistsException(request.getEmail() + " already exists");
    }
}
