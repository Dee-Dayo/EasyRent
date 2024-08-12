package com.semicolon.EaziRent.dtos.responses;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ReviewListResponse {
    private List<ReviewResponse> reviews;
}
