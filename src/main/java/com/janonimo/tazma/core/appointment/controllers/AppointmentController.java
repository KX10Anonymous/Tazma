/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/SpringFramework/Controller.java to edit this template
 */
package com.janonimo.tazma.core.appointment.controllers;

import com.janonimo.tazma.core.appointment.Appointment;
import com.janonimo.tazma.core.appointment.data.AppointmentService;
import com.janonimo.tazma.token.Token;
import com.janonimo.tazma.token.TokenRepository;
import com.janonimo.tazma.user.Role;
import com.janonimo.tazma.user.User;
import com.janonimo.tazma.user.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author JANONIMO
 */
@RestController
@RequestMapping("/api/appointment")
@RequiredArgsConstructor
@PreAuthorize("hasRole('CLIENT', 'STYLIST')")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;

    @PostMapping("/create")
    public ResponseEntity<Appointment> create(@RequestBody Appointment appointment) {
        return new ResponseEntity<>(appointmentService.create(appointment), HttpStatus.OK);
    }

    @GetMapping("/appointments")
    public ResponseEntity<List<Appointment>> appointments(@RequestBody Token token) {
        Token temp = tokenRepository.findByToken(token.getToken()).get();
        User user = temp.getUser();
        if (user.getRole() == Role.CLIENT) {
            return new ResponseEntity<>(appointmentService.clientAppointments(user.getId()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(appointmentService.stylistAppointments(user.getId()), HttpStatus.OK);
        }
    }

    /**
     *
     * @param id
     * @param appointment
     * @return
     */
    @PostMapping("/edit")
    public ResponseEntity<Appointment> edit(@RequestBody Integer id, Appointment appointment) {
        Appointment temp = appointmentService.find(id);
        temp.setStatus(appointment.getStatus());
        if (temp.getAgreedAmount() == 0.0) {
            temp.setAgreedAmount(appointment.getAgreedAmount());
        }
        if (temp.getCounterOffer() == 0.0) {
            temp.setCounterOffer(appointment.getCounterOffer());
        }

        temp.setAgreedAmount(appointment.getAgreedAmount());

        return new ResponseEntity<>(appointmentService.edit(temp), HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity<?> delete(Integer id) {
        appointmentService.delete(id);
        return ResponseEntity.ok("Appointement Deleted");
    }

    @GetMapping("read")
    public ResponseEntity<?> read(Integer id) {
        return new ResponseEntity<>(appointmentService.find(id), HttpStatus.OK);
    }

    
}
