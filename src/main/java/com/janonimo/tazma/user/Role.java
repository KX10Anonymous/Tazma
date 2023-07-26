package com.janonimo.tazma.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.janonimo.tazma.user.RoleName;
import com.janonimo.tazma.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Builder
@Table(name = "roles")
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
public class Role {
    @Id
    @GeneratedValue
    @JsonProperty("id")
    private Long id;

    private RolePriority priority;

    @Enumerated(EnumType.STRING)
    private RoleName roleName;

    @ManyToMany
    private List<User> users;

}
