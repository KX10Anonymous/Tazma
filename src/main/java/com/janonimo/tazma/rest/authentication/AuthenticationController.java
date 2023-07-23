package com.janonimo.tazma.rest.authentication;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8080"})
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        AuthenticationResponse response = authenticationService.register(request);
        if (response.getAccessToken().isEmpty()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } else {
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody LoginRequest request) {
        AuthenticationResponse registration = authenticationService.login(request);
        if (registration.getAccessToken().isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else {
            return ResponseEntity.ok(registration);
        }
    }

    @PostMapping("/refresh-token/{refreshToken}")
    public void refreshToken(@PathVariable String refreshToken) {
        authenticationService.refreshToken(refreshToken);
    }

    @GetMapping("/logout/{jwt}")
    public void logout(@PathVariable String jwt){
        authenticationService.revokeToken(jwt);
    }


}
