package com.semicolon.EaziRent.services.impls;

import com.semicolon.EaziRent.data.models.AccountDetails;
import com.semicolon.EaziRent.data.models.BioData;
import com.semicolon.EaziRent.data.models.Landlord;
import com.semicolon.EaziRent.data.models.Review;
import com.semicolon.EaziRent.data.repositories.AccountDetailsRepository;
import com.semicolon.EaziRent.data.repositories.LandlordRepository;
import com.semicolon.EaziRent.dtos.requests.AddAccountDetailsRequest;
import com.semicolon.EaziRent.dtos.requests.RegisterRequest;
import com.semicolon.EaziRent.dtos.requests.UpdateRequest;
import com.semicolon.EaziRent.dtos.responses.AddAccountDetailsResponse;
import com.semicolon.EaziRent.dtos.responses.EaziRentAPIResponse;
import com.semicolon.EaziRent.dtos.responses.RegisterResponse;
import com.semicolon.EaziRent.dtos.responses.UpdateDataResponse;
import com.semicolon.EaziRent.exceptions.InvalidDataException;
import com.semicolon.EaziRent.exceptions.ResourceNotFoundException;
import com.semicolon.EaziRent.services.BioDataService;
import com.semicolon.EaziRent.services.LandlordService;
import com.semicolon.EaziRent.services.ReviewService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.semicolon.EaziRent.data.constants.Role.LANDLORD;
import static java.time.LocalDateTime.now;

@Service
@AllArgsConstructor
public class EaziLandlordService implements LandlordService {
    private final LandlordRepository landlordRepository;
    private final ModelMapper modelMapper;
    private final BioDataService bioDataService;
    private final AccountDetailsRepository accountDetailsRepository;
    private ReviewService reviewService;

    @Autowired
    public void setReviewService(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @Override
    @Transactional
    public RegisterResponse register(RegisterRequest request) {
        request.setRole(LANDLORD);
        BioData data = bioDataService.register(request);
        Landlord landlord = modelMapper.map(request, Landlord.class);
        landlord.setBioData(data);
        landlord = landlordRepository.save(landlord);
        RegisterResponse response = modelMapper.map(landlord, RegisterResponse.class);
        response.setMessage("Landlord successfully registered");
        return response;
    }

    @Override
    public Landlord findLandlordBy(String email) {
        BioData bioData = bioDataService.getBioDataBy(email);
        return landlordRepository.findLandlordBy(bioData.getId())
                .orElseThrow(()-> new ResourceNotFoundException("Landlord not found with " + email));
    }

    @Override
    public EaziRentAPIResponse<UpdateDataResponse> update(UpdateRequest request, String email) {
        Landlord landlord = findLandlordBy(email);
        BioData bioData = bioDataService.update(landlord.getBioData(), request);
        UpdateDataResponse response = modelMapper.map(bioData, UpdateDataResponse.class);
        response.setResponseTime(now());
        return new EaziRentAPIResponse<>(true, response);
    }

    @Override
    public EaziRentAPIResponse<AddAccountDetailsResponse> addAccountDetails(AddAccountDetailsRequest request, String email) {
        Landlord landlord = findLandlordBy(email);
        validate(request.getAccountNumber());
        AccountDetails newAccountDetails = modelMapper.map(request, AccountDetails.class);
        newAccountDetails.setLandlord(landlord);
        newAccountDetails = accountDetailsRepository.save(newAccountDetails);
        AddAccountDetailsResponse response = modelMapper.map(newAccountDetails, AddAccountDetailsResponse.class);
        response.setId(landlord.getId());
        response.setResponseTime(now());
        return new EaziRentAPIResponse<>(true, response);
    }

    @Override
    public Landlord findLandlordById(Long landlordId) {
        return landlordRepository.findLandlordBy(landlordId)
                .orElseThrow(()-> new ResourceNotFoundException("Landlord not found"));
    }

    @Override
    public List<Review> findLandlordReviews(Long landlordId) {
        return reviewService.findLandlordReviews(landlordId);
    }

    private void validate(String accountNumber) {
        String regex = "\\d{10}";
        if (!accountNumber.matches(regex)) throw new InvalidDataException("Invalid account number");
    }
}
