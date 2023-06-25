package com.janonimo.tazma.core.appointment;

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
    private Integer Id;
    
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private User client;
    
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "stylist_id")
    private User stylist;
     
    private Double clientOffer;
    
    private Double counterOffer;
    
    @OneToOne(cascade= CascadeType.ALL)
    private Location location;
    
    private Double agreedAmount;
    
    @JoinColumn(name="style_id")
    @OneToOne(fetch=FetchType.EAGER)
    private Style style;
    
    @Enumerated(EnumType.STRING)
    private EStatus status;
}
