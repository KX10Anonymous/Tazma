package com.janonimo.tazma.core.appointment;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author JANONIMO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="locations")
public class Location {
    
    @Id
    @GeneratedValue
    @JsonProperty("id")
    private Integer Id;
    
    @JsonProperty("locationName")
    private String locationName;
    
    @JsonProperty("longitude")
    private double longitude;
    
    @JsonProperty("latitude")
    private double latitude;
    
    @OneToOne
    @JsonProperty("appointment")
    private Appointment appointment;
}
