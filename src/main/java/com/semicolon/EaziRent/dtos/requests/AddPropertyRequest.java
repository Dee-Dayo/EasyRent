package com.semicolon.EaziRent.dtos.requests;

import com.semicolon.EaziRent.data.constants.Type;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class AddPropertyRequest {
    private AddressRequest addressRequest;
    private int noOfApartments;
    private Type type;
    private String agentName;
    private String agentPhoneNumber;
    private MultipartFile mediaFile;
}
