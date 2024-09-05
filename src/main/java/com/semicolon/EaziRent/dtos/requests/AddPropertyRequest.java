package com.semicolon.EaziRent.dtos.requests;

import com.semicolon.EaziRent.data.constants.Type;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class AddPropertyRequest {
    private AddressRequest addressRequest;
    private Type type;
    private MultipartFile mediaFile;
}
