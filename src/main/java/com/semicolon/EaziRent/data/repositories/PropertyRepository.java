package com.semicolon.EaziRent.data.repositories;

import com.semicolon.EaziRent.data.models.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Long> {
    @Query("SELECT p from Property p WHERE p.landlord.bioData.email=:email")
    List<Property> findAllFor(String email);
}
