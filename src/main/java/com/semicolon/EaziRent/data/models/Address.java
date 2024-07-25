package com.semicolon.EaziRent.data.models;

import com.semicolon.EaziRent.data.constants.State;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.EnumType.STRING;

@Setter
@Getter
@Entity
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String number;
    private String street;
    private String area;

    @Enumerated(STRING)
    private State state;

   private String lga;
}
