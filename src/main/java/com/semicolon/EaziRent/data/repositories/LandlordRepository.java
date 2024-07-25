package com.semicolon.EaziRent.data.repositories;

import com.semicolon.EaziRent.data.models.Landlord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LandlordRepository extends JpaRepository<Landlord, Long> {
    @Query("SELECT l FROM Landlord l WHERE l.bioData.id=:bioDataId")
    Optional<Landlord> findLandlordBy(Long bioDataId);
}
