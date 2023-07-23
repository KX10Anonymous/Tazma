package com.janonimo.tazma.core.appointment;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
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
    private Long Id;
    
    private String path;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="style_id")
    @JsonManagedReference
    private Style style;
}
