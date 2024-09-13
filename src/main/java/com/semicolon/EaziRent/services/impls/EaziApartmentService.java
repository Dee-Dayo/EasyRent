package com.semicolon.EaziRent.services.impls;

import com.cloudinary.Cloudinary;
import com.semicolon.EaziRent.data.constants.State;
import com.semicolon.EaziRent.data.constants.SubType;
import com.semicolon.EaziRent.data.models.*;
import com.semicolon.EaziRent.data.repositories.ApartmentRepository;
import com.semicolon.EaziRent.data.repositories.ReviewRepository;
import com.semicolon.EaziRent.dtos.requests.AddApartmentRequest;
import com.semicolon.EaziRent.dtos.requests.GetApartmentRequest;
import com.semicolon.EaziRent.dtos.requests.ReviewApartmentRequest;
import com.semicolon.EaziRent.dtos.responses.*;
import com.semicolon.EaziRent.exceptions.InvalidDataException;
import com.semicolon.EaziRent.exceptions.ResourceNotFoundException;
import com.semicolon.EaziRent.services.ApartmentService;
import com.semicolon.EaziRent.services.BioDataService;
import com.semicolon.EaziRent.services.PropertyService;
import com.semicolon.EaziRent.services.RenterService;
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
    private final ReviewRepository reviewRepository;
    private final Cloudinary cloudinary;
    private RenterService renterService;
    private BioDataService bioDataService;

    public EaziApartmentService(ApartmentRepository apartmentRepository,
                                ModelMapper modelMapper, ReviewRepository reviewRepository, Cloudinary cloudinary){
        this.apartmentRepository = apartmentRepository;
        this.modelMapper = modelMapper;
        this.reviewRepository = reviewRepository;
        this.cloudinary = cloudinary;
    }

    @Autowired
    @Lazy
    public void setPropertyService(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @Autowired
    @Lazy
    public void setRenterService(RenterService renterService) {
        this.renterService = renterService;
    }

    @Autowired
    @Lazy
    public void setBioDataService(BioDataService bioDataService) {
        this.bioDataService = bioDataService;
    }

    @Override
    public EaziRentAPIResponse<AddApartmentResponse> addApartment(AddApartmentRequest request) {
        Property property = propertyService.getPropertyBy(request.getPropertyId());
        setPropertyApartments(property);
        Apartment apartment = createApartmentFromRequest(request, property);
        String mediaUrl = getMediaUrl(request.getMediaFile(), cloudinary.uploader());
        apartment.getMediaUrls().add(mediaUrl);
        apartment.setIsAvailable(true);
        apartment = apartmentRepository.save(apartment);
        AddApartmentResponse response = buildAddApartmentResponse(apartment, property);
        return new EaziRentAPIResponse<>(true, response);
    }

    private void setPropertyApartments(Property property) {
        property.setNoOfApartments(property.getNoOfApartments() + 1);
        propertyService.save(property);
    }

    @Override
    public Apartment getApartmentBy(Long id) {
        return apartmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No apartment found with id " + id));
    }



    @Override
    public void updateAndSave(Apartment apartment) {
        apartment.setIsAvailable(false);
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
    public ReviewListResponse getApartmentReviews(Long apartmentId) {
        List<Review> reviews = reviewRepository.findApartmentReviews(apartmentId);
        return mapReviewResponses(reviews);
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

    @Override
    public ListApartmentResponse findApartmentsByStateAndType(State state, SubType type) {
        List<Apartment> apartments = apartmentRepository.findAllApartmentsBy(state, type);
        return getListApartmentResponse(apartments);
    }

    @Override
    public ReviewApartmentResponse reviewApartment(ReviewApartmentRequest request) {
        Apartment apartment = getApartmentBy(request.getPropertyId());
        Renter renter = renterService.findById(request.getRenterId());
        BioData reviewer = bioDataService.findBioDataBy(renter.getBioData().getId());
        Review review = map(request, apartment, reviewer);
        return map(renter, review);
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
        apartment.setNumber(request.getNumber());
        apartment.setMediaUrls(new HashSet<>());
        return apartment;
    }

    private AddApartmentResponse buildAddApartmentResponse(Apartment apartment, Property property) {
        AddApartmentResponse response = modelMapper.map(apartment, AddApartmentResponse.class);
        response.setResponseTime(now());
        response.setPropertyId(property.getId());
        return response;
    }
    private Review map(ReviewApartmentRequest request, Apartment apartment, BioData reviewer){
        Review review = modelMapper.map(request, Review.class);
        review.setApartment(apartment);
        review.setReviewer(reviewer);
        reviewRepository.save(review);
        return review;
    }
    private @NotNull ReviewApartmentResponse map(Renter renter, Review review) {
        ReviewApartmentResponse response = modelMapper.map(renter, ReviewApartmentResponse.class);
        response.setApartmentId(review.getApartment().getId());
        response.setRenterId(review.getReviewer().getId());
        return response;
    }
    private static @NotNull ReviewListResponse mapReviewResponses(List<Review> reviews) {
        List<ReviewResponse> reviewResponses
                = reviews.stream().map(ReviewResponse::new)
                .collect(Collectors.toList());
        ReviewListResponse response = new ReviewListResponse();
        response.setReviews(reviewResponses);
        return response;
    }

}
