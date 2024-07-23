package com.semicolon.EaziRent.data.repositories;

import com.semicolon.EaziRent.data.models.BioData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BioDataRepository extends JpaRepository<BioData, Long> {
}
