package com.janonimo.tazma.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.janonimo.tazma.user.RoleName;
import com.janonimo.tazma.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@Table(name = "roles")
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
public class Role {
    @Id
    @GeneratedValue
    @JsonProperty("id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private RolePriority priority;

    @Enumerated(EnumType.STRING)
    private RoleName roleName;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;

    public void addUser(User user){
        if(users == null){
            users = new ArrayList<>();
        }
        users.add(user);
    }
}
