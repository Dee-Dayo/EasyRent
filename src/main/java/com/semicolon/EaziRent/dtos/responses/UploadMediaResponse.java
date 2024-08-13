package com.semicolon.EaziRent.dtos.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

import static java.time.LocalDateTime.now;

@Getter
@Setter
public class UploadMediaResponse {
    @JsonFormat(pattern = "dd-MMMM-yyyy 'at' hh:mm a")
    private LocalDateTime responseTime = now();
    @JsonProperty("apartmentId")
    private Long id;
    private Long propertyId;
    private Set<String> mediaUrls;
}
