package com.janonimo.tazma.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Data
@Table(name="profiles")
public class Profile {
    @Id
    @GeneratedValue
    private Long id;

    private LocalDateTime created;

    private String profilePicture;
    @JoinColumn(name="user_id")
    @OneToOne(fetch=FetchType.EAGER)
    @JsonProperty("user")
    private User user;

}
