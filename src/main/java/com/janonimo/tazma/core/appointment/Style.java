package com.janonimo.tazma.core.appointment;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Collection;
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
@Table(name="styles")
public class Style {
    @Id
    @GeneratedValue
    private Integer Id;
    
    private String styleName;
    
    @OneToMany(fetch=FetchType.LAZY)
    private Collection<Resource> resources;
}
