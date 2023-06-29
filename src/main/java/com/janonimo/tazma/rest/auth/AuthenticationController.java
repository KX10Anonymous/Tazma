package com.janonimo.tazma.rest.auth;

import com.janonimo.tazma.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8080"})
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        AuthenticationResponse response = service.register(request);
        if(response.getAccessToken().isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }else{
            return ResponseEntity.ok(response);
        }
        
    }
    
    /**
     * Used To Send the User Information to the Application
     * @param jwt
     * @return 
     */
    public ResponseEntity<User> read(@RequestBody String jwt){
        return new ResponseEntity<>(service.read(jwt), HttpStatus.OK);
    }
    
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
         AuthenticationResponse response = service.authenticate(request);
        if(response.getAccessToken().isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }else{
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        service.refreshToken(request, response);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        String errorMessage = "Email already exists.";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

}
