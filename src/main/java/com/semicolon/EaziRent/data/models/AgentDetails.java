package com.semicolon.EaziRent.data.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Setter
@Getter
@Entity
public class AgentDetails {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
//    @NotNull
    private String name;
//    @NotNull
    private String phoneNumber;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Property> properties;
}
