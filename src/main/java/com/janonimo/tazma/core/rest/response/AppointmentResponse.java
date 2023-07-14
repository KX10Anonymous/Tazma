package com.janonimo.tazma.core.rest.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.janonimo.tazma.core.appointment.AppointmentType;
import com.janonimo.tazma.core.appointment.EStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentResponse {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("url")
    private String url;

    @JsonProperty("time")
    private String time;

    @JsonProperty("agreed")
    private Double agreed;

    @JsonProperty("offer")
    private Double offer;

    @JsonProperty("counter")
    private Double counter;

    @JsonProperty("stylist")
    private String stylist;

    @JsonProperty  ("client")
    private String client;

    @JsonProperty("location")
    private String location;

    @JsonProperty("type")
    @Enumerated(EnumType.STRING)
    private AppointmentType type;

    @JsonProperty("status")
    @Enumerated(EnumType.STRING)
    private EStatus status;

}
