package com.semicolon.EaziRent.services.impls;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.semicolon.EaziRent.data.models.*;
import com.semicolon.EaziRent.data.repositories.AddressRepository;
import com.semicolon.EaziRent.data.repositories.AgentDetailsRepository;
import com.semicolon.EaziRent.data.repositories.PropertyRepository;
import com.semicolon.EaziRent.dtos.requests.AddPropertyRequest;
import com.semicolon.EaziRent.dtos.responses.*;
import com.semicolon.EaziRent.exceptions.ResourceNotFoundException;
import com.semicolon.EaziRent.services.*;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.semicolon.EaziRent.utils.ServicesUtils.getMediaUrl;
import static java.time.LocalDateTime.now;

@Service
@AllArgsConstructor
public class EaziPropertyService implements PropertyService {
    private final Cloudinary cloudinary;
    private final ModelMapper modelMapper;
    private final AddressRepository addressRepository;
    private final AgentDetailsRepository agentDetailsRepository;
    private final PropertyRepository propertyRepository;
    private LandlordService landlordService;
    private ApartmentService apartmentService;


    @Autowired
    @Lazy
    public void setLandlordService(LandlordService landlordService, ApartmentService apartmentService) {
        this.landlordService = landlordService;
        this.apartmentService = apartmentService;
    }

    @Override
    @Transactional
    public EaziRentAPIResponse<AddPropertyResponse> addProperty(AddPropertyRequest request, String email) {
        Landlord landlord = landlordService.findLandlordBy(email);
        Uploader uploader = cloudinary.uploader();
        String mediaUrl = getMediaUrl(request.getMediaFile(), uploader);
        Address address = saveAddress(request);
        AgentDetails agentDetails = saveAgentDetails(request);
        Property property = saveProperty(request, landlord, mediaUrl, address, agentDetails);
        AddPropertyResponse response = createResponse(property, landlord);
        return new EaziRentAPIResponse<>(true, response);
    }

    private AgentDetails saveAgentDetails(AddPropertyRequest request) {
        AgentDetails details = new AgentDetails();
        details.setName(request.getAgentName());
        details.setPhoneNumber(request.getAgentPhoneNumber());
        agentDetailsRepository.save(details);
        return details;
    }

    @Override
    public Property getPropertyBy(Long id) {
        return propertyRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("No property found with id: " + id));
    }

    @Override
    public ViewPropertyResponse findAll() {
        List<Property> properties = propertyRepository.findAll();
        List<PropertyResponse> propertyresponseList = properties.stream()
            .map(PropertyResponse::new)
            .collect(Collectors.toList());
    ViewPropertyResponse response = new ViewPropertyResponse();
    response.setProperties(propertyresponseList);
    return response;
    }

    @Override
    public void addReview(Property property, Review review) {
        property.getReviews().add(review);
        propertyRepository.save(property);
    }

    @Override
    public PropertyResponse findBy(Long id) {
        Property property = getPropertyBy(id);
        ListApartmentResponse apartmentResponse = apartmentService.findAllFor(id);
        PropertyResponse propertyResponse = new PropertyResponse(property);
        propertyResponse.setApartments(apartmentResponse.getApartments());
        return propertyResponse;
    }



    private Address saveAddress(AddPropertyRequest request) {
        Address address = modelMapper.map(request.getAddressRequest(), Address.class);
        address.setLga(request.getAddressRequest().getLga());
        return addressRepository.save(address);
    }

    private Property saveProperty(AddPropertyRequest request, Landlord landlord, String mediaUrl, Address address, AgentDetails agentDetails) {
        Property property = modelMapper.map(request, Property.class);
        property.setAddress(address);
        property.setLandlord(landlord);
        property.setAgentDetails(agentDetails);
        property.setMediaUrl(mediaUrl);
        return propertyRepository.save(property);
    }

    private AddPropertyResponse createResponse(Property property, Landlord landlord) {
        AddPropertyResponse response = modelMapper.map(property, AddPropertyResponse.class);
        response.setResponseTime(now());
        response.setMessage("Successfully added property");
        response.setLandlordId(landlord.getId());
        return response;
    }


}
