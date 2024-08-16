package com.semicolon.EaziRent.dtos.responses;

import com.semicolon.EaziRent.data.models.Address;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class AddressResponse {
    private String number;
    private String street;
    private String area;
    private String state;
    private String lga;

    public AddressResponse(Address address) {
        this.number = address.getNumber();
        this.street = address.getStreet();
        this.area = address.getArea();
        this.state = String.valueOf(address.getState());
        this.lga = address.getLga();
    }

}
