package com.janonimo.tazma.deserializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.janonimo.tazma.core.appointment.AppointmentType;
import com.janonimo.tazma.user.Gender;

import java.io.IOException;

public class AppointmentTypeDeserializer extends JsonDeserializer<AppointmentType> {
    @Override
    public AppointmentType deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        String value = p.getText();
        return AppointmentType.fromString(value);
    }

}