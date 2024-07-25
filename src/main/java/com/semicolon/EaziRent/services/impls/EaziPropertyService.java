package com.semicolon.EaziRent.services.impls;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.cloudinary.utils.ObjectUtils;
import com.semicolon.EaziRent.data.models.Address;
import com.semicolon.EaziRent.data.models.Landlord;
import com.semicolon.EaziRent.data.models.Property;
import com.semicolon.EaziRent.data.repositories.AddressRepository;
import com.semicolon.EaziRent.data.repositories.PropertyRepository;
import com.semicolon.EaziRent.dtos.requests.AddPropertyRequest;
import com.semicolon.EaziRent.dtos.responses.AddPropertyResponse;
import com.semicolon.EaziRent.dtos.responses.EaziRentAPIResponse;
import com.semicolon.EaziRent.services.LandlordService;
import com.semicolon.EaziRent.services.PropertyService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

import static java.time.LocalDateTime.now;

@Service
@AllArgsConstructor
public class EaziPropertyService implements PropertyService {
    private final Cloudinary cloudinary;
    private final ModelMapper modelMapper;
    private final AddressRepository addressRepository;
    private final PropertyRepository propertyRepository;
    private LandlordService landlordService;

    @Autowired
    @Lazy
    public void setLandlordService(LandlordService landlordService) {
        this.landlordService = landlordService;
    }

    @Override
    @Transactional
    public EaziRentAPIResponse<AddPropertyResponse> addProperty(AddPropertyRequest request, String email) throws IOException {
        String url = getMediaUrl(request.getMediaFile());
        Landlord landlord = landlordService.findLandlordBy(email);
        Address address = modelMapper.map(request.getAddressRequest(), Address.class);
        address = addressRepository.save(address);
        Property property = modelMapper.map(request, Property.class);
        property.setAddress(address);
        property.setLandlord(landlord);
        property.setMediaUrl(url);
        property = propertyRepository.save(property);
        AddPropertyResponse response = modelMapper.map(property, AddPropertyResponse.class);
        response.setResponseTime(now());
        response.setMessage("Successfully added property");
        return new EaziRentAPIResponse<>(true, response);
    }

    private String getMediaUrl(MultipartFile mediaFile) throws IOException {
        Uploader uploader = cloudinary.uploader();
        Map<?, ?> map = ObjectUtils.asMap(
                "resource_type", "auto",
                "overwrite", true,
                "use_filename", true,
                "unique_filename", false
        );
        Map<?, ?> uploadResponse = uploader.upload(mediaFile.getBytes(), map);
        return uploadResponse.get("url").toString();
    }
}
