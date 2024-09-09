package com.semicolon.EaziRent.dtos.responses;

import com.semicolon.EaziRent.data.models.Renter;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RenterResponse {
    private String firstName;
    private String lastName;
    private String email;
    private String profileUrl;
    private ApartmentResponse apartment;
    private LandlordResponse landlordResponse;

    public RenterResponse(Renter renter){
        firstName = renter.getBioData().getFirstName();
        lastName = renter.getBioData().getLastName();
        email = renter.getBioData().getEmail();
        profileUrl = renter.getBioData().getMediaUrl();
        if(renter.getRent() != null) {
            apartment = new ApartmentResponse(renter.getRent().getApartment());
            landlordResponse = new LandlordResponse(renter.getRent().getApartment().getProperty().getLandlord());
        }
    }
}
