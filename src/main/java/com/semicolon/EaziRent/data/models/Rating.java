package com.semicolon.EaziRent.data.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "ratings")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int rating;
    @ManyToOne
    @JoinColumn(name = "renter_id")
    private Renter renter;
    @ManyToOne
    @JoinColumn(name = "landlord_id")
    private Landlord landlord;
    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;
}
