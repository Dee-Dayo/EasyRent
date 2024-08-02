package com.semicolon.EaziRent.data.repositories;

import com.semicolon.EaziRent.data.models.AccountDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountDetailsRepository extends JpaRepository<AccountDetails, Long> {
}
