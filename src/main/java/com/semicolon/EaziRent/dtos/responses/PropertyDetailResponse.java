package com.semicolon.EaziRent.dtos.responses;

import com.semicolon.EaziRent.data.models.Apartment;
import com.semicolon.EaziRent.data.models.Property;
import com.semicolon.EaziRent.data.models.Review;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class PropertyDetailResponse {
    private Long id;
    private String lga;
    private String state;
    private String mediaUrl;
    private int noOfApartments;
    List<ApartmentResponse> apartments;
    private String type;
    private int averageRating;

    public PropertyDetailResponse(Property property) {
        this.id = property.getId();
        this.lga = property.getAddress().getLga();
        this.state = String.valueOf(property.getAddress().getState());
        this.mediaUrl = property.getMediaUrl();
        this.noOfApartments = property.getNoOfApartments();
        this.apartments = map(property.getApartments());
        this.type = property.getType().toString();
        this.averageRating = calculateAverageRating(property.getReviews());
    }

    private List<ApartmentResponse> map(List<Apartment> apartmentList) {
                return apartmentList.stream()
                    .map(ApartmentResponse::new)
                .toList();
    }

    private int calculateAverageRating(List<Review> reviews) {
        if (reviews == null || reviews.isEmpty()) {
            return 0;
        }
        int sum = 0;
        for (Review review : reviews) {
            sum += review.getRating();
        }
        return sum / reviews.size();
    }
}
