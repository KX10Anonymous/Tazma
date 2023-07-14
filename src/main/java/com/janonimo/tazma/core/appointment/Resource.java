package com.janonimo.tazma.core.appointment;

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
    private Style style;
}
