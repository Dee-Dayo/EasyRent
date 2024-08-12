package com.semicolon.EaziRent.dtos.responses;

import com.semicolon.EaziRent.data.models.Review;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewResponse {
    private Long reviewId;
    private int rating;
    private String comment;
    private String reviewerName;
    private String reviewDate;

    public ReviewResponse(Review review) {
        this.reviewId = review.getId();
        this.rating = review.getRating();
        this.comment = review.getComment();
        this.reviewerName = review.getReviewer().getFirstName();
        this.reviewDate = String.valueOf(review.getReviewDate());
    }
}
