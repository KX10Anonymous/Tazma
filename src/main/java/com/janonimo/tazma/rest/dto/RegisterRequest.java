package com.janonimo.tazma.rest.dto;

import com.janonimo.tazma.core.appointment.AppointmentType;
import com.janonimo.tazma.user.Gender;
import com.janonimo.tazma.user.RoleName;
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
    private RoleName roleName;
    private String phone;
    private Gender gender;
    private AppointmentType type;
}
