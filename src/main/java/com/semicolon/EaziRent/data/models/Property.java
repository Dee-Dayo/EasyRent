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

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Landlord landlord;

    @OneToOne
    private Address address;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional = true)
    private AgentDetails agentDetails;

    private String mediaUrl;
    private int noOfApartments;

    @Enumerated(value = STRING)
    private Type type;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Apartment> apartments;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Review> reviews;
}
