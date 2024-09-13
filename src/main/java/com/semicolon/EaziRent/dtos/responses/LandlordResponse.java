package com.semicolon.EaziRent.dtos.responses;

import com.semicolon.EaziRent.data.models.Landlord;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LandlordResponse {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String profilePictureUrl;

    public LandlordResponse(Landlord landlord) {
        this.id = landlord.getId();
        this.email = landlord.getBioData().getEmail();
        firstName = landlord.getBioData().getFirstName();
        lastName = landlord.getBioData().getLastName();
        profilePictureUrl = landlord.getBioData().getMediaUrl();
    }
}
