package com.semicolon.EaziRent.data.repositories;

import com.semicolon.EaziRent.data.models.BioData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BioDataRepository extends JpaRepository<BioData, Long> {
    boolean existsByEmail(String email);
}
