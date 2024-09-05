package com.semicolon.EaziRent.dtos.responses;

import com.semicolon.EaziRent.data.models.Apartment;
import com.semicolon.EaziRent.data.models.Review;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Setter
@Getter
public class ApartmentResponse {
    private Long id;
    private String subType;
    private String rentType;
    private BigDecimal price;
    private Integer number;
    private Boolean isAvailable;
    private Set<String> mediaUrls;
    private int ratings;
    private AddressResponse addressResponse;
    private PropertyResponse propertyResponse;
    private LandlordResponse landlordResponse;

    public ApartmentResponse(Apartment apartment) {
        id = apartment.getId();
        this.subType = String.valueOf(apartment.getType());
        this.rentType = String.valueOf(apartment.getRentType());
        this.price = apartment.getPrice();
        this.number = apartment.getNumber();
        this.isAvailable = apartment.getIsAvailable();
        this.mediaUrls = apartment.getMediaUrls();
        this.addressResponse = new AddressResponse(apartment.getProperty().getAddress());
        this.ratings = calculateRatings(apartment.getReviews());
        this.propertyResponse = new PropertyResponse(apartment.getProperty());
        this.landlordResponse = new LandlordResponse(apartment.getProperty().getLandlord());
    }

    private int calculateRatings(List<Review> reviews) {
        if(!reviews.isEmpty()) {
            int sum = reviews.stream().mapToInt(Review::getRating).sum();
            return sum / reviews.size();
        }
        return 0;
    }
}
