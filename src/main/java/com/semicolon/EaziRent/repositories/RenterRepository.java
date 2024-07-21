package com.semicolon.EaziRent.repositories;

import com.semicolon.EaziRent.models.Renter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RenterRepository extends JpaRepository<Renter, Long> {
}
