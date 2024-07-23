package com.semicolon.EaziRent.data.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "renters")
public class Renter {
    @Id
    private Long id;
}
