package com.semicolon.EaziRent.data.repositories;

import com.semicolon.EaziRent.data.models.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ApartmentRepository extends JpaRepository<Apartment, Long> {
    @Query("SELECT a from Apartment a WHERE a.property.id=:id")
    List<Apartment> findAllApartmentsFor(Long id);
}
