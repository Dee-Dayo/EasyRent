package com.semicolon.EaziRent.data.repositories;

import com.semicolon.EaziRent.data.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long> {
    @Query("SELECT r FROM Review r where r.reviewee.id=:bioDataId")
    List<Review> findRenterReviews(Long bioDataId);
    @Query("SELECT r FROM Review r where r.property.id=:propertyId")
    List<Review> findPropertyReviews(Long propertyId);
    @Query("SELECT r FROM Review r where r.reviewee.id=:bioDataId")
    List<Review> findLandlordReviews(Long bioDataId);
    @Query("SELECT r FROM Review r WHERE r.apartment.apartmentId=:apartmentId")
    List<Review> findApartmentReviews(Long apartmentId);
}
