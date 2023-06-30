
package com.janonimo.tazma.rest.auth;

import com.janonimo.tazma.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author JANONIMO
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8080"})
public class UsersControllers {
    private final AuthenticationService authService;
    private final UserService userService;

    /**
     * Used To Send the User Information to the Application
     */
    @GetMapping("/user/{jwt}")
    public ResponseEntity<User> read(@PathVariable String jwt){
        return new ResponseEntity<>(authService.read(jwt), HttpStatus.OK);
    }
    
    @GetMapping("/available/{jwt}")
    public ResponseEntity<?> availableUsers(@PathVariable String jwt){
        return new ResponseEntity<>(userService.findByStatus(jwt), HttpStatus.OK);
    }
    
     @GetMapping("/location/{jwt}")
    public ResponseEntity<?> usersByLocation(@PathVariable String jwt){
        return new ResponseEntity<>(userService.findByAddress(jwt), HttpStatus.OK);
    }
    
}
