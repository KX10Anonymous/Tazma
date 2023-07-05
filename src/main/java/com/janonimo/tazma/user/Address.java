package com.janonimo.tazma.user;

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
@Table(name="address")
public class Address {
    @Id
    @GeneratedValue
    @JsonProperty("id")
    private Long id;

    @JsonProperty("town")
    private String town;

    @JsonProperty("street")
    private String streetName;
    @JsonProperty("house")
    private String houseNumber;
    @JsonProperty("province")
    private String province;
    @OneToOne(cascade = CascadeType.ALL)
    @JsonProperty("user")
    private User user;

    @Override
    public String toString() {
        return town + '\''
                + streetName +
                '\'' + houseNumber + '\''
                + province + '\''
               ;
    }
}
