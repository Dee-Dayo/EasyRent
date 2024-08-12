package com.semicolon.EaziRent.data.models;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class BioDataSerializer extends JsonSerializer<BioData> {
    @Override
    public void serialize(BioData bioData, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeObjectField("id", bioData.getId());
        jsonGenerator.writeStringField("firstName", bioData.getFirstName());
        jsonGenerator.writeStringField("lastName", bioData.getLastName());
        jsonGenerator.writeStringField("email", bioData.getEmail());
        jsonGenerator.writeEndObject();
    }
}
