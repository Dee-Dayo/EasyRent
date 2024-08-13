package com.semicolon.EaziRent.services.impls;

import com.cloudinary.Cloudinary;
import com.semicolon.EaziRent.data.models.Apartment;
import com.semicolon.EaziRent.data.models.BioData;
import com.semicolon.EaziRent.data.models.Landlord;
import com.semicolon.EaziRent.data.models.Property;
import com.semicolon.EaziRent.data.repositories.ApartmentRepository;
import com.semicolon.EaziRent.dtos.requests.AddApartmentRequest;
import com.semicolon.EaziRent.dtos.requests.GetApartmentRequest;
import com.semicolon.EaziRent.dtos.responses.*;
import com.semicolon.EaziRent.exceptions.InvalidDataException;
import com.semicolon.EaziRent.exceptions.ResourceNotFoundException;
import com.semicolon.EaziRent.services.ApartmentService;
import com.semicolon.EaziRent.services.PropertyService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static com.semicolon.EaziRent.utils.ServicesUtils.getMediaUrl;
import static java.time.LocalDateTime.now;

@Service
@Slf4j
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
    public EaziRentAPIResponse<AddApartmentResponse> addApartment(AddApartmentRequest request) {
        Property property = propertyService.getPropertyBy(request.getPropertyId());
        Apartment apartment = createApartmentFromRequest(request, property);
        String mediaUrl = getMediaUrl(request.getMediaFile(), cloudinary.uploader());
        apartment.getMediaUrls().add(mediaUrl);
        apartment.setIsAvailable(apartment.getNumber() > 0);
        apartment = apartmentRepository.save(apartment);
        AddApartmentResponse response = buildAddApartmentResponse(apartment, property);
        return new EaziRentAPIResponse<>(true, response);
    }

    @Override
    public Apartment getApartmentBy(Long id) {
        return apartmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No apartment found with id " + id));
    }

    @Override
    public List<Apartment> findPropertyApartments(Long id) {
        return apartmentRepository.findAllApartmentsFor(id);
    }

    @Override
    public void updateAndSave(Apartment apartment, int number) {
        apartment.setNumber(apartment.getNumber() - number);
        apartment.setIsAvailable(apartment.getNumber() > 0);
        apartmentRepository.save(apartment);
    }

    @Override
    public ListApartmentResponse findAllFor(Long propertyId) {
        List<Apartment> apartments = apartmentRepository.findAllApartmentsFor(propertyId);
        return getListApartmentResponse(apartments);
    }

    private static @NotNull ListApartmentResponse getListApartmentResponse(List<Apartment> apartments) {
        List<ApartmentResponse> apartmentResponses = apartments
                .stream().map(ApartmentResponse::new)
                .collect(Collectors.toList());
        ListApartmentResponse listApartmentResponse = new ListApartmentResponse();
        listApartmentResponse.setApartments(apartmentResponses);
        return listApartmentResponse;
    }

    @Override
    public ApartmentResponse findApartmentBy(Long id) {
        Apartment apartment = getApartmentBy(id);
        return new ApartmentResponse(apartment);
    }

    @Override
    public ListApartmentResponse findApartmentsBy(GetApartmentRequest request) {
        List<Apartment> apartments = apartmentRepository.findByStateAndSubtypeAndRentType
                (request.getState(), request.getType(), request.getRentType());
        return getListApartmentResponse(apartments);
    }

    @Override
    public EaziRentAPIResponse<UploadMediaResponse> uploadMedia(List<MultipartFile> mediaFiles,
                                                                Long id, String email) {
        log.info("Trying to upload media files for apartment with id: {}", id);
        Apartment apartment = getApartmentBy(id);
        validatePropertyOwner(apartment.getProperty(), email);
        UploadMediaResponse response = uploadMediaAndGetResponse(mediaFiles, apartment);
        log.info("Successfully uploaded media files for apartment with id: {}", id);
        return new EaziRentAPIResponse<>(true, response);
    }

    private UploadMediaResponse uploadMediaAndGetResponse(List<MultipartFile> mediaFiles, Apartment apartment) {
        for (MultipartFile mediaFile : mediaFiles) {
            String mediaUrl = getMediaUrl(mediaFile, cloudinary.uploader());
            apartment.getMediaUrls().add(mediaUrl);
        }
        apartment = apartmentRepository.save(apartment);
        UploadMediaResponse response = modelMapper.map(apartment, UploadMediaResponse.class);
        response.setPropertyId(apartment.getProperty().getId());
        return response;
    }

    private void validatePropertyOwner(Property property, String email) {
        Landlord landlord = property.getLandlord();
        BioData bioData = landlord.getBioData();
        String landlordEmail = bioData.getEmail();
        if (!landlordEmail.equals(email))
            throw new InvalidDataException("You are not authorised to perform this action");
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
