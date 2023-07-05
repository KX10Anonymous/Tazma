package com.janonimo.tazma.core.appointment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.janonimo.tazma.core.reporting.Review;
import com.janonimo.tazma.user.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
    private Integer Id;
    
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    @JsonProperty("client")
    private User client;
    
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "stylist_id")
    @JsonProperty("stylist")
    private User stylist;
     
    @JsonProperty("clientOffer")
    private Double clientOffer;
    
    @JsonProperty("counterOffer")
    private Double counterOffer;
    
    @OneToOne(cascade= CascadeType.ALL)
    @JsonProperty("location")
    private Location location;
    
    @JsonProperty("agreedAmount")
    private Double agreedAmount;
    
    @JoinColumn(name="style_id")
    @OneToOne(fetch=FetchType.EAGER)
    @JsonProperty("style")
    private Style style;

    @JsonProperty("appointmentType")
    private AppointmentType appointmentType;

    @OneToOne
    private Review review;
    @Enumerated(EnumType.STRING)
    @JsonProperty("status")
    private EStatus status;
    @JsonProperty("time")
    private LocalDateTime appointmentTime;
}
