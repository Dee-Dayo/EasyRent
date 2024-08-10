package com.semicolon.EaziRent.services.impls;

import com.cloudinary.Cloudinary;
import com.semicolon.EaziRent.data.models.Apartment;
import com.semicolon.EaziRent.data.models.Property;
import com.semicolon.EaziRent.data.repositories.ApartmentRepository;
import com.semicolon.EaziRent.dtos.requests.AddApartmentRequest;
import com.semicolon.EaziRent.dtos.requests.RentApartmentRequest;
import com.semicolon.EaziRent.dtos.responses.AddApartmentResponse;
import com.semicolon.EaziRent.dtos.responses.EaziRentAPIResponse;
import com.semicolon.EaziRent.dtos.responses.RentApartmentResponse;
import com.semicolon.EaziRent.exceptions.ResourceNotFoundException;
import com.semicolon.EaziRent.services.ApartmentService;
import com.semicolon.EaziRent.services.PropertyService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

import static com.semicolon.EaziRent.utils.EaziUtils.getMediaUrl;
import static java.time.LocalDateTime.now;

@Service
public class EaziApartmentService implements ApartmentService {
    private PropertyService propertyService;
    private final ApartmentRepository apartmentRepository;
    private final ModelMapper modelMapper;
    private final Cloudinary cloudinary;

    public EaziApartmentService(ApartmentRepository apartmentRepository,
                                ModelMapper modelMapper, Cloudinary cloudinary){
        this.apartmentRepository = apartmentRepository;
        this.modelMapper = modelMapper;
        this.cloudinary = cloudinary;
    }
    @Autowired
    @Lazy
    public void setPropertyService(PropertyService propertyService) {
        this.propertyService = propertyService;
    }


    @Override
    public EaziRentAPIResponse<AddApartmentResponse> addApartment(AddApartmentRequest request) throws IOException {
        Property property = propertyService.getPropertyBy(request.getPropertyId());
        Apartment apartment = createApartmentFromRequest(request, property);
        String mediaUrl = getMediaUrl(request.getMediaFile(), cloudinary.uploader());
        apartment.getMediaUrls().add(mediaUrl);
        apartment.setIsAvailable(apartment.getNumber() != 0);
        apartment = apartmentRepository.save(apartment);
        AddApartmentResponse response = buildAddApartmentResponse(apartment, property);
        return new EaziRentAPIResponse<>(true, response);
    }

    @Override
    public Apartment getApartmentBy(Long id) {
        return apartmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No apartment found with id " + id));
    }


    private Apartment createApartmentFromRequest(AddApartmentRequest request, Property property) {
        Apartment apartment = modelMapper.map(request, Apartment.class);
        apartment.setProperty(property);
        apartment.setMediaUrls(new HashSet<>());
        return apartment;
    }

    private AddApartmentResponse buildAddApartmentResponse(Apartment apartment, Property property) {
        AddApartmentResponse response = modelMapper.map(apartment, AddApartmentResponse.class);
        response.setResponseTime(now());
        response.setPropertyId(property.getId());
        return response;
    }

}
