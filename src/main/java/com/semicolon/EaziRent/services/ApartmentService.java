package com.semicolon.EaziRent.services;

import com.semicolon.EaziRent.data.models.Apartment;
import com.semicolon.EaziRent.dtos.requests.AddApartmentRequest;
import com.semicolon.EaziRent.dtos.responses.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ApartmentService {
    EaziRentAPIResponse<AddApartmentResponse> addApartment(AddApartmentRequest request) throws IOException;

    Apartment getApartmentBy(Long id);

    List<Apartment> findPropertyApartments(Long id);

    void updateAndSave(Apartment apartment, int number);

    ListApartmentResponse findAllFor(Long propertyId);

    ApartmentResponse findApartmentBy(Long id);

    EaziRentAPIResponse<UploadMediaResponse> uploadMedia(List<MultipartFile> mediaFiles, Long id, String name);
}
