package com.semicolon.EaziRent.data.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "renters")
@Getter
@Setter
public class Renter {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
}
