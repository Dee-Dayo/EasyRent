package com.semicolon.EaziRent.services.impls;

import com.semicolon.EaziRent.data.models.*;
import com.semicolon.EaziRent.data.repositories.AccountDetailsRepository;
import com.semicolon.EaziRent.data.repositories.LandlordRepository;
import com.semicolon.EaziRent.data.repositories.ReviewRepository;
import com.semicolon.EaziRent.dtos.requests.AddAccountDetailsRequest;
import com.semicolon.EaziRent.dtos.requests.RateUserRequest;
import com.semicolon.EaziRent.dtos.requests.RegisterRequest;
import com.semicolon.EaziRent.dtos.requests.UpdateRequest;
import com.semicolon.EaziRent.dtos.responses.*;
import com.semicolon.EaziRent.exceptions.InvalidDataException;
import com.semicolon.EaziRent.exceptions.ResourceNotFoundException;
import com.semicolon.EaziRent.services.*;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.semicolon.EaziRent.data.constants.Role.LANDLORD;
import static java.time.LocalDateTime.now;

@Service
public class EaziLandlordService implements LandlordService {
    private final LandlordRepository landlordRepository;
    private final ModelMapper modelMapper;
    private final BioDataService bioDataService;
    private final AccountDetailsRepository accountDetailsRepository;
    private final ReviewRepository reviewRepository;
    private RenterService renterService;


    public EaziLandlordService(LandlordRepository landlordRepository,
                               ModelMapper modelMapper,
                               BioDataService bioDataService,
                               AccountDetailsRepository accountDetailsRepository,
                               ReviewRepository reviewRepository, RenterService renterService) {
        this.landlordRepository = landlordRepository;
        this.modelMapper = modelMapper;
        this.bioDataService = bioDataService;
        this.accountDetailsRepository = accountDetailsRepository;
        this.reviewRepository = reviewRepository;
    }

    @Autowired
    @Lazy
    public void setRenterService(RenterService renterService) {
        this.renterService = renterService;
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
        return reviewRepository.findLandlordReviews(landlordId);
    }

    @Override
    public RateUserResponse reviewRenter(RateUserRequest request) {
        Landlord landlord = findLandlordById(request.getLandlordId());
        Renter renter = renterService.findById(request.getRenterId());
        BioData reviewer = bioDataService.findBioDataBy(landlord.getBioData().getId());
        BioData reviewee = bioDataService.findBioDataBy(renter.getBioData().getId());
        Review review = map(request, reviewer, reviewee);
        reviewRepository.save(review);
        return map(review, landlord, renter);
    }
    private @NotNull Review map(RateUserRequest request, BioData reviewer, BioData reviewee) {
        Review review = modelMapper.map(request, Review.class);
        review.setReviewer(reviewer);
        review.setReviewee(reviewee);
        return review;
    }

    private @NotNull RateUserResponse map(Review review, Landlord landlord, Renter renter) {
        RateUserResponse response = modelMapper.map(review, RateUserResponse.class);
        response.setLandLordId(landlord.getId());
        response.setRenterId(renter.getId());
        return response;
    }


    private void validate(String accountNumber) {
        String regex = "\\d{10}";
        if (!accountNumber.matches(regex)) throw new InvalidDataException("Invalid account number");
    }
}
