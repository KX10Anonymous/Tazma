package com.janonimo.tazma.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.janonimo.tazma.core.appointment.AppointmentType;

import java.io.IOException;

public class AppointmentTypeDeserializer extends JsonDeserializer<AppointmentType> {
    @Override
    public AppointmentType deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String value = p.getText();
        return AppointmentType.fromString(value);
    }

}