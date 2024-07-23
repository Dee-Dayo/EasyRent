package com.semicolon.EaziRent.data.repositories;

import com.semicolon.EaziRent.data.models.Landlord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LandlordRepository extends JpaRepository<Landlord, Long> {
}
