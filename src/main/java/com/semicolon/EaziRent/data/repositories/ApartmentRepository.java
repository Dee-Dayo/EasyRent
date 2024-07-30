package com.semicolon.EaziRent.data.repositories;

import com.semicolon.EaziRent.data.models.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApartmentRepository extends JpaRepository<Apartment, Long> {
}
