package com.janonimo.tazma.core.rest;

import com.janonimo.tazma.core.appointment.Appointment;
import com.janonimo.tazma.core.rest.response.AppointmentResponse;
import com.janonimo.tazma.core.services.AppointmentService;
import com.janonimo.tazma.token.Token;
import com.janonimo.tazma.token.TokenRepository;
import com.janonimo.tazma.user.Role;
import com.janonimo.tazma.user.User;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author JANONIMO
 */
@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8080"})
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final TokenRepository tokenRepository;
    

    @PostMapping("/create/{jwt}")
    public ResponseEntity<Appointment> create(@PathVariable String jwt, @RequestBody Appointment appointment) {
        if(validateUserRequest(jwt)){
            appointment.setClient(tokenRepository.findByToken(jwt).get().getUser());
            return new ResponseEntity<>(appointmentService.create(jwt, appointment), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/all/{jwt}")
    public ResponseEntity<List<AppointmentResponse>> appointments(@PathVariable String jwt) {
        Token temp = tokenRepository.findByToken(jwt).get();
        User user = temp.getUser();
        if (user.getRole() == Role.CLIENT) {
            return new ResponseEntity<>(appointmentService.clientAppointments(user.getId()), HttpStatus.OK);
        } else if(user.getRole() == Role.STYLIST){
            return new ResponseEntity<>(appointmentService.stylistAppointments(user.getId()), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    /**
     *
     */
    @PutMapping("/edit/{jwt}")
    public ResponseEntity<Appointment> edit(@PathVariable String jwt, @RequestBody Appointment appointment) {
        Appointment tempAppointment = appointmentService.find(appointment.getId());
        Token temp = tokenRepository.findByToken(jwt).get();
        if(!temp.isExpired() && !temp.isRevoked()){
            if(temp.getUser().getId() == tempAppointment.getStylist().getId() || temp.getUser().getId() == tempAppointment.getClient().getId()){
                return new ResponseEntity<>(appointmentService.edit(jwt, appointment), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("/delete/{jwt}")
    public ResponseEntity<?> delete(@PathVariable String jwt, @RequestBody Long id) {
        if(validateUserRequest(jwt, id)){
            appointmentService.delete(id);
            return ResponseEntity.ok("Appointment Deleted");
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/read/{jwt}")
    public ResponseEntity<Appointment> read(@PathVariable String jwt, @RequestBody Long id) {
        if(validateUserRequest(jwt, id) == true)
            return new ResponseEntity<>(appointmentService.find(id), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }


//    @GetMapping("/searcha/{name}")
//    public ResponseEntity<List<Appointment>> searchActive(@PathVariable String name){
//
//        return new ResponseEntity<>(appointmentService.readByName(name), HttpStatus.OK);
//    }
    

//    @GetMapping("/searchall/{name}")
//    public ResponseEntity<List<Appointment>> searchAll(@PathVariable String name){
//        return new ResponseEntity<>(appointmentService.readAllByName(name), HttpStatus.OK);
//    }

    private boolean validateUserRequest(String jwt, Long id){
        Appointment tempAppointment = appointmentService.find(id);
        Token temp = tokenRepository.findByToken(jwt).get();
        if(!temp.isExpired() && !temp.isRevoked()){
            if(temp.getUser().getId() == tempAppointment.getStylist().getId() || temp.getUser().getId() == tempAppointment.getClient().getId()){
                return true;
            }
        }
        return false;
    }
    private boolean validateUserRequest(String jwt){
        Token temp = tokenRepository.findByToken(jwt).get();
        if(!temp.isExpired() && !temp.isRevoked()){
            return true;
        }
        return false;
    }
}
