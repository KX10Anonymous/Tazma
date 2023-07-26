
package com.janonimo.tazma.rest.authentication;

import com.janonimo.tazma.token.Token;
import com.janonimo.tazma.token.TokenRepository;
import com.janonimo.tazma.user.*;
import com.janonimo.tazma.user.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author JANONIMO
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8080"})
public class UsersController {
    private final AuthenticationService authService;
    private final TokenRepository tokenRepository;
    private final UserService userService;
    @GetMapping("/user/{jwt}")
    public ResponseEntity<User> read(@PathVariable String jwt){
        return new ResponseEntity<>(authService.read(jwt), HttpStatus.OK);
    }

    @PostMapping("/address/{jwt}")
    public ResponseEntity<Address> address(@PathVariable String jwt,@RequestBody Address address){
        return getUserResponseEntity(jwt, address);
    }

    private ResponseEntity<Address> getUserResponseEntity(@PathVariable String jwt, @RequestBody Address address) {
        Token token = tokenRepository.findByToken(jwt).get();
        if(!token.isExpired()){
            return new ResponseEntity<>(userService.save(jwt, address), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("/updateaddress/{jwt}")
    public ResponseEntity<Address> updateAddress(@PathVariable String jwt,@RequestBody Address address){
        return getUserResponseEntity(jwt, address);
    }
    @PostMapping("/edit/{jwt}")
    public ResponseEntity<User> edit(@PathVariable String jwt,@RequestBody User user){
        Token token = tokenRepository.findByToken(jwt).get();
        if(!token.isExpired()){
            return new ResponseEntity<>(userService.save(user), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/type-edit/{jwt}")
    public ResponseEntity<User> editType(@PathVariable String jwt,@RequestBody User user){
        Token token = tokenRepository.findByToken(jwt).get();
        if(!token.isExpired()){
            User temp = token.getUser();
            for(Role r : user.getRoles()){
                if(r.getRoleName() != RoleName.STYLIST){
                    temp.setAppointmentType(user.getAppointmentType());
                    break;
                }
            }
            temp.setGender(user.getGender());
            return new ResponseEntity<>(userService.save(temp), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/available/{jwt}")
    public ResponseEntity<?> availableUsers(@PathVariable String jwt){
        return new ResponseEntity<>(userService.findByStatus(jwt), HttpStatus.OK);
    }

    @GetMapping("/email-check/{email}")
    public ResponseEntity<ContactValidationResponse> emailExists(@PathVariable String email){
        ContactValidationResponse response = ContactValidationResponse.builder()
                .exists(userService.emailExists(email)).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/phone-check/{phone}")
    public ResponseEntity<ContactValidationResponse> phoneExists(@PathVariable String phone){
        ContactValidationResponse response = ContactValidationResponse.builder()
                .exists(userService.phoneExists(phone)).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
     @GetMapping("/location/{jwt}")
    public ResponseEntity<?> usersByLocation(@PathVariable String jwt){
        return new ResponseEntity<>(userService.findByAddress(jwt), HttpStatus.OK);
    }

}
