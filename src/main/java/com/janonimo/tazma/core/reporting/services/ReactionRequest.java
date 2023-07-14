package com.janonimo.tazma.core.reporting.services;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.janonimo.tazma.core.reporting.React;
import lombok.Data;

@Data
public class ReactionRequest {
    @JsonProperty("react")
    private React react;
    @JsonProperty("postid")
    private Long postId;
    @JsonProperty("jwt")
    private String jwt;
}
