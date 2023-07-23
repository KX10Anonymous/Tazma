package com.janonimo.tazma.core.appointment;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    private Long Id;
    
    @JsonProperty("locationName")
    private String locationName;
    
    @JsonProperty("longitude")
    private double longitude;
    
    @JsonProperty("latitude")
    private double latitude;

    @OneToMany(mappedBy = "location",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonProperty("appointment")
    @JsonBackReference
    private List<Appointment> appointments;
}
