package com.semicolon.EaziRent.data.repositories;

import com.semicolon.EaziRent.data.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
