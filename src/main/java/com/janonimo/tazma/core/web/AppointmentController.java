package com.janonimo.tazma.core.web;

import com.janonimo.tazma.core.appointment.Appointment;
import com.janonimo.tazma.core.services.AppointmentService;
import com.janonimo.tazma.token.Token;
import com.janonimo.tazma.token.TokenRepository;
import com.janonimo.tazma.user.Role;
import com.janonimo.tazma.user.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author JANONIMO
 */
@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
@PreAuthorize("hasRole({'CLIENT', 'STYLIST'})")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8080"})
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final TokenRepository tokenRepository;
    

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
     * @param appointment
     * @return
     */
    @PostMapping("/edit")
    public ResponseEntity<Appointment> edit(@RequestBody Appointment appointment) {
        return new ResponseEntity<>(appointmentService.edit(appointment), HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity<?> delete(Integer id) {
        appointmentService.delete(id);
        return ResponseEntity.ok("Appointement Deleted");
    }

    @GetMapping("/read")
    public ResponseEntity<Appointment> read(Integer id) {
        return new ResponseEntity<>(appointmentService.find(id), HttpStatus.OK);
    }

    /**
     *
     * @param name
     * @return
     */
    @GetMapping("/searcha")
    public ResponseEntity<List<Appointment>> searchActive(@RequestBody String name){
        
        return new ResponseEntity<>(appointmentService.readByName(name), HttpStatus.OK);
    }
    
     /**
     *
     * @param name
     * @return
     */
    @GetMapping("/searchall")
    public ResponseEntity<List<Appointment>> searchAll(@RequestBody String name){
        return new ResponseEntity<>(appointmentService.readAllByName(name), HttpStatus.OK);
    }
}
