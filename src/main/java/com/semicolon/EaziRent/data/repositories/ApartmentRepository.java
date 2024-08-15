package com.semicolon.EaziRent.data.repositories;

import com.semicolon.EaziRent.data.constants.RentType;
import com.semicolon.EaziRent.data.constants.State;
import com.semicolon.EaziRent.data.constants.SubType;
import com.semicolon.EaziRent.data.models.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ApartmentRepository extends JpaRepository<Apartment, Long> {
    @Query("SELECT a from Apartment a WHERE a.property.id=:id")
    List<Apartment> findAllApartmentsFor(Long id);
    @Query("SELECT a from Apartment a WHERE a.property.address.state=:state and a.type=:type and a.rentType=:rentType")
    List<Apartment> findByStateAndSubtypeAndRentType(State state, SubType type, RentType rentType);
    @Query("SELECT a FROM Apartment a WHERE a.property.address.state=:state and a.type=:type")
    List<Apartment> findAllApartmentsBy(State state, SubType type);
}
