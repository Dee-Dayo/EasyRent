package com.semicolon.EaziRent.security.data.models;

import com.semicolon.EaziRent.data.models.BioData;
import com.semicolon.EaziRent.data.models.Review;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class SecureUser implements UserDetails {
    private final BioData user;
    @Getter
    private final String firstName;
    @Getter
    private final String lastName;
    @Getter
    private final String mediaUrl;
    @Getter
    private final int rating;

    public SecureUser(BioData user) {
        this.user = user;
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.mediaUrl = user.getMediaUrl();
        this.rating = calculateRating(user.getReviews());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .toList();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    private int calculateRating(List<Review> reviews) {
        if (reviews == null || reviews.isEmpty()) return 0;
        return reviews.stream().mapToInt(Review::getRating).sum() / reviews.size();
    }

    @Override
    public String toString() {
        return "[" +
                "Username=" + user.getEmail() +
                ", Password=" + "[PROTECTED]" +
                ", Enabled=" + isEnabled() +
                ", AccountNonExpired=" + isAccountNonExpired() +
                ", CredentialsNonExpired=" + isCredentialsNonExpired() +
                ", AccountNonLocked=" + isAccountNonLocked() +
                ", Granted Authorities=" + getAuthorities() +
                ']';
    }
}
