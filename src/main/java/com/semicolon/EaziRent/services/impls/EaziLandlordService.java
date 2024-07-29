package com.semicolon.EaziRent.services.impls;

import com.semicolon.EaziRent.data.models.BioData;
import com.semicolon.EaziRent.data.models.Landlord;
import com.semicolon.EaziRent.data.repositories.LandlordRepository;
import com.semicolon.EaziRent.dtos.requests.RegisterRequest;
import com.semicolon.EaziRent.dtos.requests.UpdateRequest;
import com.semicolon.EaziRent.dtos.responses.EaziRentAPIResponse;
import com.semicolon.EaziRent.dtos.responses.RegisterResponse;
import com.semicolon.EaziRent.dtos.responses.UpdateDataResponse;
import com.semicolon.EaziRent.exceptions.ResourceNotFoundException;
import com.semicolon.EaziRent.services.BioDataService;
import com.semicolon.EaziRent.services.LandlordService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.semicolon.EaziRent.data.constants.Role.LANDLORD;
import static java.time.LocalDateTime.now;

@Service
@AllArgsConstructor
public class EaziLandlordService implements LandlordService {
    private final LandlordRepository landlordRepository;
    private final ModelMapper modelMapper;
    private final BioDataService bioDataService;

    @Override
    @Transactional
    public RegisterResponse register(RegisterRequest request) {
        request.setRole(LANDLORD);
        BioData data = bioDataService.register(request);
        Landlord landlord = modelMapper.map(request, Landlord.class);
        landlord.setBioData(data);
        landlord = landlordRepository.save(landlord);
        RegisterResponse response = modelMapper.map(landlord, RegisterResponse.class);
        response.setMessage("Landlord successfully registered");
        return response;
    }

    @Override
    public Landlord findLandlordBy(String email) {
        BioData bioData = bioDataService.getBioDataBy(email);
        return landlordRepository.findLandlordBy(bioData.getId())
                .orElseThrow(()-> new ResourceNotFoundException("Landlord not found with " + email));
    }

    @Override
    public EaziRentAPIResponse<UpdateDataResponse> update(UpdateRequest request, String email) {
        Landlord landlord = findLandlordBy(email);
        BioData bioData = bioDataService.update(landlord.getBioData(), request);
        UpdateDataResponse response = modelMapper.map(bioData, UpdateDataResponse.class);
        response.setResponseTime(now());
        return new EaziRentAPIResponse<>(true, response);
    }
}
