package com.janonimo.tazma.core.services;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.janonimo.tazma.core.appointment.AppointmentType;
import com.janonimo.tazma.core.appointment.EStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentRequest {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("style")
    private Long style;

    @JsonProperty("time")
    private LocalDateTime time;

    @JsonProperty("type")
    private AppointmentType type;

    @Enumerated(EnumType.STRING)
    @JsonProperty("status")
    private EStatus status;

    @JsonProperty("offer")
    private Double offer;

}
