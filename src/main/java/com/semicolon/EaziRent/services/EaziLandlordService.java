package com.semicolon.EaziRent.services;

import com.semicolon.EaziRent.data.models.BioData;
import com.semicolon.EaziRent.data.models.Landlord;
import com.semicolon.EaziRent.data.models.Renter;
import com.semicolon.EaziRent.data.repositories.LandlordRepository;
import com.semicolon.EaziRent.dtos.requests.RegisterRequest;
import com.semicolon.EaziRent.dtos.responses.RegisterResponse;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.semicolon.EaziRent.data.constants.Role.LANDLORD;

@Service
@AllArgsConstructor
public class EaziLandlordService implements LandlordService{
    private final LandlordRepository landlordRepository;
    private final ModelMapper modelMapper;
    private final BioDataService bioDataService;

    @Override
    @Transactional
    public RegisterResponse register(RegisterRequest request) {
        BioData data = bioDataService.register(request);
        data.setRole(LANDLORD);
        Landlord landlord = modelMapper.map(request, Landlord.class);
        landlord.setBioData(data);
        landlord = landlordRepository.save(landlord);
        RegisterResponse response = modelMapper.map(landlord, RegisterResponse.class);
        response.setMessage("Renter successfully registered");
        return response;
    }
}
