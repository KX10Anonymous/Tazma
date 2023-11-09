package com.janonimo.tazma.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
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
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="addresses")
public class Address {
    @Id
    @GeneratedValue
    @JsonProperty("id")
    private Long id;

    @JsonProperty("area")
    private String area;

    @JsonProperty("suburb")
    private String suburb;

    @JsonProperty("street")
    private String streetName;

    @JsonProperty("house")
    private String houseNumber;

    @JsonProperty("province")
    private String province;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonManagedReference
    @JsonProperty("user")
    private User user;

    @Override
    public String toString() {
        return area + '\''
                + streetName +
                '\'' + houseNumber + '\''
                + province + '\''
               ;
    }
}
