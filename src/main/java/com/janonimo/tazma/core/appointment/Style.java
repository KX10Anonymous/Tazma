package com.janonimo.tazma.core.appointment;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
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
    @JsonProperty("id")
    private Integer Id;
    
    @JsonProperty("styleName")
    private String styleName;
    
    @OneToMany(fetch=FetchType.LAZY)
    @JsonProperty("resources")
    private List<Resource> resources;
}
