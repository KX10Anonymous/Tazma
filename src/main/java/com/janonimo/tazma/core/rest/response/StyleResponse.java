package com.janonimo.tazma.core.rest.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StyleResponse {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("title")
    private String title;

    @JsonProperty("url")
    private String url;
}
