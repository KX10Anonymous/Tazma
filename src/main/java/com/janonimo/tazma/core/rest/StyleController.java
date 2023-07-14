package com.janonimo.tazma.core.rest;


import com.janonimo.tazma.core.appointment.Style;
import com.janonimo.tazma.core.rest.response.StyleResponse;
import com.janonimo.tazma.core.services.StyleService;
import com.janonimo.tazma.token.Token;
import com.janonimo.tazma.token.TokenRepository;
import com.janonimo.tazma.user.Role;
import com.janonimo.tazma.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 *
 * @author JANONIMO
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/styles")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8080"})
public class StyleController {
    
    private final StyleService styleService;
    private final TokenRepository tokenRepository;

    @PostMapping("/create/{jwt}")
    public ResponseEntity<Style> create(@PathVariable String jwt, @RequestBody Style style){
        if(validateUserRequest(jwt)){
            return new ResponseEntity<>(styleService.create(style), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/upload/{id}")
    public ResponseEntity<?> upload(@PathVariable Long id,  @RequestParam("file") MultipartFile file){
        return new ResponseEntity<>(styleService.uploadResource(id, file), HttpStatus.OK);
    }
    @GetMapping("/styles")
    public ResponseEntity<List<StyleResponse>> styles(){
        return new ResponseEntity<>(styleService.all(), HttpStatus.OK);
    }
    

    @PutMapping("/edit/{jwt}")
    public ResponseEntity<Style> edit(@PathVariable String jwt, @RequestBody Style style){
        if(validateUserRequest(jwt)){
            return new ResponseEntity<>(styleService.edit(style), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/read")
    public ResponseEntity<Style> read(@RequestBody Long id){
        return new ResponseEntity<>(styleService.read(id), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{jwt}")
    public ResponseEntity<?> delete(@PathVariable String jwt, Style style){
        if(validateUserRequest(jwt)){
            return new ResponseEntity<>(styleService.delete(style), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    private boolean validateUserRequest(String jwt){
        Token temp = tokenRepository.findByToken(jwt).get();
        User user = tokenRepository.findByToken(jwt).get().user;
        if(user.getRole() != Role.ADMIN)
            return false;
        return !temp.isExpired();
    }


}
