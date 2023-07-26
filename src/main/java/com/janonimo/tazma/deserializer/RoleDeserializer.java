package com.janonimo.tazma.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.janonimo.tazma.user.RoleName;

import java.io.IOException;

public class RoleDeserializer extends JsonDeserializer<RoleName> {
    @Override
    public RoleName deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String value = jsonParser.getText();
        return RoleName.fromString(value);
    }
}
