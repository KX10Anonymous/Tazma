package com.janonimo.tazma.rest.authentication;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.janonimo.tazma.rest.config.JwtService;
import com.janonimo.tazma.token.Token;
import com.janonimo.tazma.token.TokenRepository;
import com.janonimo.tazma.token.TokenType;
import com.janonimo.tazma.user.Role;
import com.janonimo.tazma.user.RoleName;
import com.janonimo.tazma.user.RolePriority;
import com.janonimo.tazma.user.User;
import com.janonimo.tazma.deserializer.RoleDeserializer;
import com.janonimo.tazma.user.services.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.security.authentication.BadCredentialsException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService tokenService;
    private final AuthenticationManager authenticationManager;

    @JsonDeserialize(using = RoleDeserializer.class)
    private RoleName roleName;

    public AuthenticationResponse register(RegisterRequest request) {
        try {
            ArrayList<Role> roles = new ArrayList<>();
            roleName = RoleName.fromString(request.getRoleName().toString());
            if(roleName == RoleName.STYLIST){
                Role r1 = Role.builder()
                        .priority(RolePriority.MAIN)
                        .roleName(RoleName.STYLIST)
                    .build();

                Role r2 = Role.builder()
                        .priority(RolePriority.SECONDARY)
                        .roleName(RoleName.CLIENT)
                        .build();
            }


            User user = User.builder()
                    .firstname(request.getFirstname())
                    .lastname(request.getLastname())
                    .email(request.getEmail())
                    .phone(request.getPhone())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .roles(roles)
                    .build();
            if(roleName == RoleName.STYLIST){
                user.setAppointmentType(request.getType());
            }
            User newUser = repository.save(user);
            String newJwt = tokenService.generateToken(user);
            String refreshJwt = tokenService.generateRefreshToken(user);
            saveUserToken(newUser, newJwt);

            String main = "";
            for(Role r : newUser.getRoles()){
                if(r.getPriority() == RolePriority.MAIN){
                    main = r.getRoleName().name();
                    break;
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
                Token token = Objects.requireNonNull(tokenRepository.findByToken(jwt)).get();
                if(Objects.requireNonNull(repository.findById(token.getUser().getId())).isPresent()){
                    User user = Objects.requireNonNull(repository.findById(token.user.getId())).get();
                    user.setPassword("");
                    return user;
                }else{
                    return null;
                }
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
            User user = repository.findByEmail(request.getEmail()).get();
            //Revoke Existing Tokens Before Saving New One
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
        Token token  = tokenRepository.findByToken(jwt).get();
        token.setExpired(true);
        token.setRevoked(true);

        tokenRepository.save(token);
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

    public AuthenticationResponse refreshToken(String refreshToken)  {
        AuthenticationResponse response = null;
        String userEmail = tokenService.extractUsername(refreshToken);
        if (userEmail != null) {
            User user = repository.findByEmail(userEmail).get();
            if (tokenService.isTokenValid(refreshToken, user)) {
                revokeTokens(user);
                String accessToken = tokenService.generateToken(user);
                saveUserToken(user, accessToken);
                response = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
            }
        }
        return response;
    }
}
