package com.janonimo.tazma.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.janonimo.tazma.core.appointment.Appointment;
import com.janonimo.tazma.core.appointment.AppointmentType;
import com.janonimo.tazma.core.reporting.Post;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author JANONIMO
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="_user")
public class User implements UserDetails{
    
    @Id
    @GeneratedValue
    @JsonProperty("id")
    private Long Id;
    
    @JsonProperty("firstname")
    private String firstname;

    @Column(unique=true)
    @JsonProperty("phone")
    private String phone;
    
    @JsonProperty("lastname")
    private String lastname;

    @Column(unique=true)
    @JsonProperty("email")
    private String email;
    
    @JsonProperty("password")
    private String password;

    @JsonProperty("address")
    @OneToOne(fetch=FetchType.EAGER)
    @JsonBackReference
    private Address address;

    @JsonBackReference
    @OneToMany
    private List<Post> posts;

    @Nullable
    @JsonProperty("status")
    @Enumerated(EnumType.STRING)
    private StylistStatus status;

    @Nullable
    @JsonProperty("appointmentType")
    @Enumerated(EnumType.STRING)
    private AppointmentType appointmentType;

    @Enumerated(EnumType.STRING)
    @JsonProperty("gender")
    @Nullable
    private Gender gender;

    @JsonProperty("roles")
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.EAGER)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles;

    public void addRole(Role role){
        if (roles == null) {
            roles = new ArrayList<>();
        }
        roles.add(role);
    }

    @JsonBackReference
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Appointment> appointments;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        assert roles != null;
        if(roles.size() > 0){
            for(Role r : roles){
                if(r.getPriority() == RolePriority.MAIN){
                    return List.of(new SimpleGrantedAuthority(r.getRoleName().name()));
                }
            }
        }
        return null;
    }


    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getPassword() {
        return password;
    }
}
