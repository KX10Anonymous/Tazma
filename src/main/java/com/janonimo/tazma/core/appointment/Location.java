package com.janonimo.tazma.core.appointment;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
    private Integer Id;
    
    private String locationName;
    
    private double longitude;
    
    private double latitude;
    
    @OneToOne
    private Appointment appointment;
}
