package com.semicolon.EaziRent.data.repositories;

import com.semicolon.EaziRent.data.models.Renter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RenterRepository extends JpaRepository<Renter, Long> {
    @Query("SELECT r FROM Renter r WHERE r.bioData.id=:bioDataId")
    Optional<Renter> findRenterBy(Long bioDataId);
}
