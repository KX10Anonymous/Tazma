package com.janonimo.tazma.rest.auth;

import com.janonimo.tazma.user.Gender;
import com.janonimo.tazma.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private Role role;
    private String phone;

    private Gender gender;
}
