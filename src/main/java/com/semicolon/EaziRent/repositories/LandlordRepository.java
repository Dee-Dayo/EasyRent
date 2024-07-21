package com.semicolon.EaziRent.repositories;

import com.semicolon.EaziRent.models.Landlord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LandlordRepository extends JpaRepository<Landlord, Long> {
}
