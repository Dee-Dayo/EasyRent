package com.semicolon.EaziRent.dtos.responses;

import com.semicolon.EaziRent.data.models.Property;
import com.semicolon.EaziRent.data.models.Review;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class PropertyResponse {
    private Long id;
    private String lga;
    private String state;
    private String mediaUrl;
    private int noOfApartments;
    private String agentName;
    private String agentPhoneNumber;
    private String landlordMediaUrl;
    private String landlordName;
    private int landlordRating;
    private String type;
    private int averageRating;
    private List<ApartmentResponse> apartments;


    public PropertyResponse(Property property) {
        this.id = property.getId();
        this.lga = property.getAddress().getLga();
        this.state = String.valueOf(property.getAddress().getState());
        this.mediaUrl = property.getMediaUrl();
        this.noOfApartments = property.getNoOfApartments();
        this.type = String.valueOf(property.getType());
        this.landlordRating = calculateAverageRating(property.getLandlord().getBioData().getReviews());
        if (property.getLandlord().getBioData().getMediaUrl() != null)
            this.landlordMediaUrl = property.getLandlord().getBioData().getMediaUrl();
        else this.landlordMediaUrl = "default";
        if (property.getLandlord().getBioData().getFirstName() != null)
            this.landlordName = property.getLandlord().getBioData().getFirstName();
        else this.landlordName = "default";




        if (property.getAgentDetails() != null) {
            this.agentName = property.getAgentDetails().getName();
            this.agentPhoneNumber = property.getAgentDetails().getPhoneNumber();
        } else {
            this.agentName = "No agent assigned";
            this.agentPhoneNumber = "N/A";
        }

        this.averageRating = calculateAverageRating(property.getReviews());
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
