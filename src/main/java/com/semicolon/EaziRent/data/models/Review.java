package com.semicolon.EaziRent.data.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "reviews")
@ToString
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int rating;

    @ManyToOne
    @JsonSerialize
    @JsonDeserialize
    private BioData reviewer;

    @ManyToOne
    @JsonSerialize
    @JsonDeserialize
    private BioData reviewee;

    @ManyToOne
    @JsonSerialize
    @JsonDeserialize
    private Property property;

    private String comment;
    @Setter(AccessLevel.NONE)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime reviewDate;

    @ManyToOne
    @JsonIgnore
    private Apartment apartment;

    @PrePersist
    private void setReviewDate() {
        reviewDate = LocalDateTime.now();
    }


}
