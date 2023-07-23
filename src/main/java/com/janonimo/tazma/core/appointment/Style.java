package com.janonimo.tazma.core.appointment;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.janonimo.tazma.user.Gender;
import jakarta.persistence.*;

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
    private Long Id;
    
    @JsonProperty("title")
    private String styleName;


    @JsonProperty("gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToMany(mappedBy = "style", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonBackReference
    @JsonProperty("resources")
    private List<Resource> resources;

    @JsonBackReference
    @OneToMany(mappedBy = "style",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Appointment> appointments;
}
