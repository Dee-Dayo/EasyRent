package com.semicolon.EaziRent.data.models;

import com.semicolon.EaziRent.data.constants.RentType;
import com.semicolon.EaziRent.data.constants.SubType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static jakarta.persistence.EnumType.STRING;

@Setter
@Getter
@Entity
@Table(name = "apartments")
public class Apartment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(STRING)
    private SubType type;
    @Enumerated(STRING)
    private RentType rentType;
    private BigDecimal price;
    private Integer number;
    private Boolean isAvailable;


    @ElementCollection
    private Set<String> mediaUrls;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Review> reviews;

    @ManyToOne
    private Property property;
}
