package com.semicolon.EaziRent.services;

import com.semicolon.EaziRent.data.models.BioData;
import com.semicolon.EaziRent.data.models.Renter;
import com.semicolon.EaziRent.data.repositories.RenterRepository;
import com.semicolon.EaziRent.dtos.requests.RegisterRequest;
import com.semicolon.EaziRent.dtos.responses.RegisterResponse;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class EaziRenterService implements RenterService{
    private final RenterRepository renterRepository;
    private final BioDataService bioDataService;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public RegisterResponse register(RegisterRequest request) {
        RegisterResponse response = bioDataService.register(request);
        BioData bioData = bioDataService.findByEmail(request.getEmail());
        Renter renter = new Renter();
        renter.setBioData(bioData);
        renterRepository.save(renter);
        return response;
    }
}
