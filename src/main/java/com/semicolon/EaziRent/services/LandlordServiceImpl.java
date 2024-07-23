package com.semicolon.EaziRent.services;

import com.semicolon.EaziRent.data.models.Landlord;
import com.semicolon.EaziRent.data.repositories.LandlordRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LandlordServiceImpl implements LandlordService{

    private final ModelMapper modelMapper;
    private final LandlordRepository landlordRepository;

    @Override
    public void register(Landlord landlord) {
//        Landlord landlord =
    }
}
