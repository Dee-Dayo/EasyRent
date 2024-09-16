package com.semicolon.EaziRent.security.utils;

import java.util.List;

public class SecurityUtils {

    private SecurityUtils() {}

    public static final String JWT_PREFIX = "Bearer ";

    public static final List<String>
            PUBLIC_ENDPOINTS = List.of(
                "/api/v1/auth/login",
                "/api/v1/auth/logout",
                "/api/v1/landlord/register",
                "/api/v1/renter/register",
                "/api/v1/property/all",
                "/api/v1/apartment/all",
                "/api/v1/apartment/filter",
                "/api/v1/property/findBy{id}",
                "/api/v1/apartment/findBy{id}",
                "/api/v1/apartment/all{propertyId}",
                "/api/v1/paystack/banks",
                "/api/v1/renter/findByEmail",
                "/api/v1/renter/getPropertyReviews{propertyId}",
                "/api/v1/renter/getLandlordReviews{landlordId}",
                "/api/v1/renter/reviewLandlord",
                "/api/v1/apartment/review"
    );


    public static final String[] LANDLORD_AUTH_ENDPOINTS = {
                "/api/v1/property/add",
                "/api/v1/landlord/**",
                "/api/v1/apartment/add",
                "/api/v1/apartment/upload-media/{apartmentId}",
                "/api/v1/property/findByLandlord",
                "/api/v1/landlord/add-account-details"

    };

    public static final String[] RENTER_AUTH_ENDPOINTS = {
            "/api/v1/renter/**",
            "/api/v1/paystack/**",
            "/api/v1/paystack/verify/{reference}"
    };

}
