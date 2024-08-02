package com.semicolon.EaziRent.services.impls;

import com.semicolon.EaziRent.data.models.Rent;
import com.semicolon.EaziRent.data.repositories.RentRepository;
import com.semicolon.EaziRent.services.RentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EaziRentService implements RentService {
    private final RentRepository rentRepository;

    @Autowired
    public EaziRentService(RentRepository rentRepository) {
        this.rentRepository = rentRepository;
    }

    @Override
    public Rent save(Rent rent) {
        return rentRepository.save(rent);
    }
}
