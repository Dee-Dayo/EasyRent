package com.semicolon.EaziRent.data.repositories;

import com.semicolon.EaziRent.data.models.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RateRepository extends JpaRepository<Rating,Long> {
}
