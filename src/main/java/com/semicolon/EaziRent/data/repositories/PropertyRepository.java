package com.semicolon.EaziRent.data.repositories;

import com.semicolon.EaziRent.data.models.Property;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyRepository extends JpaRepository<Property, Long> {
}
