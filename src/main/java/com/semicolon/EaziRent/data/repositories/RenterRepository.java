package com.semicolon.EaziRent.data.repositories;

import com.semicolon.EaziRent.data.models.Renter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RenterRepository extends JpaRepository<Renter, Long> {
}
