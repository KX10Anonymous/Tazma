package com.janonimo.tazma.rest.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
public class ContactValidationResponse {
    @JsonProperty("exists")
    private boolean exists;
}
