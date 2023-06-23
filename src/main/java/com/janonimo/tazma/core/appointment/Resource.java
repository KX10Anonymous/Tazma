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
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="resources")
public class Resource {
    @Id
    @GeneratedValue
    private Integer Id;
    
    private String url;
    
    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="style_id")
    private Style style;
}
