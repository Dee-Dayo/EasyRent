package com.semicolon.EaziRent.dtos.responses;

import com.semicolon.EaziRent.data.models.Property;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ViewPropertyResponse {
    private List<Property> properties;
}
