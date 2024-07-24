package com.semicolon.EaziRent.security.services;

import com.semicolon.EaziRent.data.models.BioData;
import com.semicolon.EaziRent.data.repositories.BioDataRepository;
import com.semicolon.EaziRent.security.data.models.SecureUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final BioDataRepository userRepository;

    public CustomUserDetailsService(BioDataRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        BioData user = userRepository.findByEmail(username)
                .orElseThrow(()-> new UsernameNotFoundException("Invalid username or password"));
        return new SecureUser(user);
    }
}
