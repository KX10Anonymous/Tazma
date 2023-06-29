package com.janonimo.tazma.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.janonimo.tazma.core.appointment.Appointment;
import jakarta.persistence.*;

import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
    private Integer Id;
    
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

    @Nullable
    @Enumerated(EnumType.STRING)
    @JsonProperty("role")
    private Role role;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @OneToMany(fetch = FetchType.LAZY)
    private Collection<Appointment>appointments;
    
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
