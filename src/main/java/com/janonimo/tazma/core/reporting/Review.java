package com.janonimo.tazma.core.reporting;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.janonimo.tazma.core.appointment.Appointment;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@Table(name = "reviews")
@NoArgsConstructor
@AllArgsConstructor
public class Review {

    @Id
    @JsonProperty("id")
    private Long Id;

    @JsonProperty("created")
    private LocalDateTime created;

    @JsonProperty("rating")
    private Rating rating;

    @OneToOne
    @JoinColumn(name = "appointment_id")
    @JsonProperty("appointment")
    @JsonManagedReference
    private Appointment appointment;
}
