package com.janonimo.tazma.rest.authentication;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.janonimo.tazma.deserializer.RoleDeserializer;
import com.janonimo.tazma.rest.config.JwtService;
import com.janonimo.tazma.rest.dto.AuthenticationResponse;
import com.janonimo.tazma.rest.dto.LoginRequest;
import com.janonimo.tazma.rest.dto.RegisterRequest;
import com.janonimo.tazma.token.Token;
import com.janonimo.tazma.token.TokenRepository;
import com.janonimo.tazma.token.TokenType;
import com.janonimo.tazma.user.Role;
import com.janonimo.tazma.user.RoleName;
import com.janonimo.tazma.user.RolePriority;
import com.janonimo.tazma.user.User;
import com.janonimo.tazma.user.services.RoleRepository;
import com.janonimo.tazma.user.services.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService tokenService;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;

    @JsonDeserialize(using = RoleDeserializer.class)
    private RoleName roleName;

    public AuthenticationResponse register(RegisterRequest request) {
        try {
            roleName = RoleName.fromString(request.getRoleName().toString());
            Role r1;
            Role r2 = null;
            if(roleName == RoleName.STYLIST){
                r1 = roleRepository.findByPriority("STYLIST", "MAIN").orElse(null);
                r2 = roleRepository.findByPriority("CLIENT", "SECONDARY").orElse(null);
            }else{
                r1 = roleRepository.findByPriority("CLIENT", "MAIN").orElse(null);
            }
            User user = User.builder()
                    .firstname(request.getFirstname())
                    .lastname(request.getLastname())
                    .email(request.getEmail())
                    .phone(request.getPhone())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .build();
            user.addRole(r1);

            if(roleName == RoleName.STYLIST){
                user.setAppointmentType(request.getType());
                if(r2 != null){
                    user.addRole(r2);
                }
            }
            user = repository.save(user);
            if(r1 != null){
                r1.addUser(user);
            }
            roleRepository.saveAndFlush(r1);
            if(r2 != null){
                r2.addUser(user);
                roleRepository.saveAndFlush(r2);
            }
            String newJwt = tokenService.generateToken(user);
            String refreshJwt = tokenService.generateRefreshToken(user);
            saveUserToken(user, newJwt);

            String main = "";
            if(user != null){
                for(Role r : user.getRoles()){
                    if(r.getPriority() == RolePriority.MAIN){
                        main = r.getRoleName().name();
                        break;
                    }
                }
            }

            return AuthenticationResponse.builder()
                    .accessToken(newJwt)
                    .refreshToken(refreshJwt)
                    .role(main)
                    .build();
        } catch (BadCredentialsException ex) {
            return new AuthenticationResponse("", "", "");
        }

    }
    
    public User read(String jwt){
        try{
            if(tokenRepository.findByToken(jwt).isPresent()){
                Token token = Objects.requireNonNull(tokenRepository.findByToken(jwt)).orElse(null);
                if(token != null){
                    if(Objects.requireNonNull(repository.findById(token.getUser().getId())).isPresent()){
                        User user = Objects.requireNonNull(repository.findById(token.user.getId())).orElse(null);
                        if(user != null){
                            user.setPassword("");
                        }
                        return user;
                    }else{
                        return null;
                    }
                }else{return null;}
            }else {
                return null;
            }
        }catch(NullPointerException e){
            return null;
        }

    }

    public AuthenticationResponse login(LoginRequest request) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()));
            User user = repository.findByEmail(request.getEmail()).orElse(null);
            if(user != null){
                revokeTokens(user);
                String newToken = tokenService.generateToken(user);
                String refreshToken = tokenService.generateRefreshToken(user);

                saveUserToken(user, newToken);

                String main = "";
                for(Role r : user.getRoles()){
                    if(r.getPriority() == RolePriority.MAIN){
                        main = r.getRoleName().name();
                        break;
                    }
                }
                return AuthenticationResponse.builder()
                        .accessToken(newToken)
                        .refreshToken(refreshToken)
                        .role(main)
                        .build();
            }else {return null;}


        } catch (BadCredentialsException ex) {
            return new AuthenticationResponse("", "","");
        }
    }

    private void saveUserToken(User user, String jwt) {
        var token = Token.builder()
                .user(user)
                .token(jwt)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    public void revokeToken(String jwt) {
        Token token  = tokenRepository.findByToken(jwt).orElse(null);
        if(token != null){
            token.setExpired(true);
            token.setRevoked(true);
            tokenRepository.save(token);
        }
    }

    private void revokeTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (!validUserTokens.isEmpty()) {
            validUserTokens.forEach(token -> {
                token.setExpired(true);
                token.setRevoked(true);
            });
            tokenRepository.saveAll(validUserTokens);
        }
    }

    public void refreshToken(String refreshToken)  {
        String userEmail = tokenService.extractUsername(refreshToken);
        if (userEmail != null) {
            User user = repository.findByEmail(userEmail).orElse(null);
            if(user != null){
                if (tokenService.isTokenValid(refreshToken, user)) {
                    revokeTokens(user);
                    String accessToken = tokenService.generateToken(user);
                    saveUserToken(user, accessToken);
                }
            }
        }
    }
}
