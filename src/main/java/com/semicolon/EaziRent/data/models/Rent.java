package com.semicolon.EaziRent.data.models;

import com.semicolon.EaziRent.data.constants.PaymentOption;
import com.semicolon.EaziRent.data.constants.RentType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

import static jakarta.persistence.EnumType.STRING;

@Entity
@Setter
@Getter
@Table(name = "rents")
public class Rent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Apartment apartment;
    @ManyToOne
    private Renter renter;
    @Enumerated(STRING)
    private RentType rentType;
    private BigDecimal price;
    @Enumerated(STRING)
    private PaymentOption paymentOption;
}
