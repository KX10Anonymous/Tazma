package com.janonimo.tazma.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.janonimo.tazma.user.StylistStatus;

import java.io.IOException;

/**
 *
 * @author JANONIMO
 */
public class StatusDeserializer extends JsonDeserializer<StylistStatus>{

    @Override
    public StylistStatus deserialize(JsonParser p, DeserializationContext ctxt) throws IOException{
        String value = p.getText();
        return StylistStatus.fromString(value);
    }
}
