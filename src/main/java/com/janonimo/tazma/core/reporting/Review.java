package com.janonimo.tazma.core.reporting;

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
    private Long Id;
    private LocalDateTime created;
    private Rating rating;

    @OneToOne
    private Appointment appointment;
}
