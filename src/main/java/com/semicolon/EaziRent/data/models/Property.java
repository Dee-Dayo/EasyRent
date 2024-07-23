package com.semicolon.EaziRent.data.models;

import com.semicolon.EaziRent.data.constants.Type;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import static jakarta.persistence.EnumType.STRING;

@Setter
@Getter
@Entity
@Table(name = "properties")
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Landlord landlord;

    @OneToOne
    private Address address;

    private int noOfApartments;

    @Enumerated(value = STRING)
    private Type type;
}
