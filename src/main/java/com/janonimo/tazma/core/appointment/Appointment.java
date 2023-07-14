package com.janonimo.tazma.core.appointment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.janonimo.tazma.core.reporting.Review;
import com.janonimo.tazma.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


/**
 *
 * @author JANONIMO
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name ="appointments")
public class Appointment {
    @Id
    @GeneratedValue
    @JsonProperty("id")
    private Long Id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "client_id")
    @JsonProperty("client")
    private User client;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "stylist_id")
    @JsonProperty("stylist")
    private User stylist;
     
    @JsonProperty("clientOffer")
    private Double clientOffer;
    
    @JsonProperty("counterOffer")
    private Double counterOffer;
    
    @ManyToOne(cascade= CascadeType.ALL)
    @JsonProperty("location")
    private Location location;
    
    @JsonProperty("agreedAmount")
    private Double agreedAmount;
    
    @JoinColumn(name="style_id")
    @ManyToOne(fetch=FetchType.EAGER)
    @JsonProperty("style")
    private Style style;

    @JsonProperty("appointmentType")
    private AppointmentType appointmentType;

    @OneToOne(mappedBy = "appointment")
    private Review review;
    @Enumerated(EnumType.STRING)
    @JsonProperty("status")
    private EStatus status;
    @JsonProperty("time")
    private LocalDateTime appointmentTime;
}
