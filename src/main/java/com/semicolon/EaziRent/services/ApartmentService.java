package com.semicolon.EaziRent.services;

import com.semicolon.EaziRent.data.constants.State;
import com.semicolon.EaziRent.data.constants.SubType;
import com.semicolon.EaziRent.data.models.Apartment;
import com.semicolon.EaziRent.dtos.requests.AddApartmentRequest;
import com.semicolon.EaziRent.dtos.requests.GetApartmentRequest;
import com.semicolon.EaziRent.dtos.requests.ReviewApartmentRequest;
import com.semicolon.EaziRent.dtos.responses.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ApartmentService {
    EaziRentAPIResponse<AddApartmentResponse> addApartment(AddApartmentRequest request);

    Apartment getApartmentBy(Long id);

    void updateAndSave(Apartment apartment);

    ListApartmentResponse findAllFor(Long propertyId);

    ApartmentResponse findApartmentBy(Long id);

    ListApartmentResponse findApartmentsBy(GetApartmentRequest request);

    EaziRentAPIResponse<UploadMediaResponse> uploadMedia(List<MultipartFile> mediaFiles,
                                                         Long id, String email);

    ListApartmentResponse findApartmentsByStateAndType(State state, SubType type);

    ReviewApartmentResponse reviewApartment(ReviewApartmentRequest request);
    ReviewListResponse getApartmentReviews(Long l);
}