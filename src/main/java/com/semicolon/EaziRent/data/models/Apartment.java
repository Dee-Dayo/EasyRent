package com.semicolon.EaziRent.data.models;

import com.semicolon.EaziRent.data.constants.SubType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

import static jakarta.persistence.EnumType.STRING;

@Setter
@Getter
@Entity
@Table(name = "apartments")
public class Apartment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal price;

    @Enumerated(value = STRING)
    private SubType type;

    private boolean isAvailable;

    @ManyToOne
    private Property property;
}
