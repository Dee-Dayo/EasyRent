package com.semicolon.EaziRent.data.repositories;

import com.semicolon.EaziRent.data.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long> {
    @Query("SELECT r FROM Review r where r.reviewee.id=:renterId")
    List<Review> finAllReviewsFor(Long renterId);
    @Query("SELECT r FROM Review r where r.property.id=:propertyId")
    List<Review> findAllReviewsFor(Long propertyId);
}
