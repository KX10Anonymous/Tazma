package com.janonimo.tazma.core.appointment;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
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
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="resources")
public class Resource {
    @Id
    @GeneratedValue
    private Integer Id;
    
    private String path;
    
    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="style_id")
    private Style style;
}
