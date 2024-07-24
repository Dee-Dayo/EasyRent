package com.semicolon.EaziRent.security.data.repositories;

import com.semicolon.EaziRent.security.data.models.BlacklistedToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlacklistedTokenRepository extends JpaRepository<BlacklistedToken, Long> {
    boolean existsByToken(String token);
}
