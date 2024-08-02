package com.semicolon.EaziRent.data.repositories;

import com.semicolon.EaziRent.data.models.Rent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentRepository extends JpaRepository<Rent, Long> {
}
