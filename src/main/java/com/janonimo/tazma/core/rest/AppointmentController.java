package com.janonimo.tazma.core.rest;

import com.janonimo.tazma.core.appointment.Appointment;
import com.janonimo.tazma.core.rest.response.AppointmentResponse;
import com.janonimo.tazma.core.services.AppointmentRequest;
import com.janonimo.tazma.core.services.AppointmentService;
import com.janonimo.tazma.core.services.StyleService;
import com.janonimo.tazma.token.Token;
import com.janonimo.tazma.token.TokenRepository;
import com.janonimo.tazma.user.Role;
import com.janonimo.tazma.user.RoleName;
import com.janonimo.tazma.user.RolePriority;
import com.janonimo.tazma.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

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
    private final StyleService styleService;
    

    @PostMapping("/create/{jwt}")
    public ResponseEntity<Appointment> create(@PathVariable String jwt, @RequestBody AppointmentRequest request) {
        if(validateUserRequest(jwt)){
            Token token = tokenRepository.findByToken(jwt).orElse(null);
            if(token != null){
                Appointment appointment = Appointment.builder()
                        .client(token.getUser())
                        .appointmentType(request.getType())
                        .style(styleService.read(request.getStyle()))
                        .clientOffer(0.0)
                        .counterOffer(0.0)
                        .agreedAmount(0.0)
                        .appointmentType(request.getType()).build();

                return new ResponseEntity<>(appointmentService.create(jwt, appointment), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/all/{jwt}")
    public ResponseEntity<List<AppointmentResponse>> appointments(@PathVariable String jwt) {
        return handleRole(jwt);
    }

    @PutMapping("/edit/{jwt}")
    public ResponseEntity<Appointment> edit(@PathVariable String jwt, @RequestBody AppointmentRequest request) {
        Appointment tempAppointment = appointmentService.find(request.getId());
        tempAppointment.setAppointmentType(request.getType());
        tempAppointment.setAgreedAmount(request.getOffer());
        tempAppointment.setAppointmentTime(request.getTime());
        Token temp = tokenRepository.findByToken(jwt).orElse(null);
        if(temp != null){
            if(!temp.isExpired() && !temp.isRevoked()){
                if(Objects.equals(temp.getUser().getId(), tempAppointment.getStylist().getId()) || Objects.equals(temp.getUser().getId(), tempAppointment.getClient().getId())){
                    return new ResponseEntity<>(appointmentService.edit(jwt, tempAppointment), HttpStatus.OK);
                }
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
        if(validateUserRequest(jwt, id))
            return new ResponseEntity<>(appointmentService.find(id), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    private boolean validateUserRequest(String jwt, Long id){
        Appointment tempAppointment = appointmentService.find(id);
        Token temp = tokenRepository.findByToken(jwt).orElse(null);
        if(!Objects.equals(temp,null)){
            if(!temp.isExpired() && !temp.isRevoked()){
                return Objects.equals(temp.getUser().getId(), tempAppointment.getStylist().getId()) || Objects.equals(temp.getUser().getId(), tempAppointment.getClient().getId());
            }
        }
        return false;
    }
    private boolean validateUserRequest(String jwt){
        Token temp = tokenRepository.findByToken(jwt).orElse(null);
        if(Objects.equals(temp, null))
            return false;
        return !temp.isExpired() && !temp.isRevoked();
    }

    private ResponseEntity<List<AppointmentResponse>> handleRole(String jwt){
        Token token = tokenRepository.findByToken(jwt).orElse(null);
        if(token != null){
            User user = token.getUser();
            for(Role r : user.getRoles()){
                if(r.getRoleName() == RoleName.CLIENT && r.getPriority() == RolePriority.MAIN){
                    return new ResponseEntity<>(appointmentService.clientAppointments(user.getId()), HttpStatus.OK);
                }
                else if(r.getRoleName() == RoleName.STYLIST && r.getPriority() == RolePriority.MAIN){
                    return new ResponseEntity<>(appointmentService.stylistAppointments(user.getId()), HttpStatus.OK);
                }

            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
