package com.semicolon.EaziRent.dtos.requests;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
public class UploadImageRequest {
    private Long id;
    private MultipartFile image;
}
