package com.janonimo.tazma.core.appointment.controllers;


import com.janonimo.tazma.core.appointment.Style;
import com.janonimo.tazma.core.appointment.data.StyleService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author JANONIMO
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/style")
public class StyleController {
    
    private final StyleService styleService;
    
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<Style> create(@RequestBody Style style){
        return new ResponseEntity<>(style, HttpStatus.OK);
    }
    
    @GetMapping("/styles")
    public ResponseEntity<List<Style>> styles(){
        return new ResponseEntity<>(styleService.all(), HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/edit")
    public ResponseEntity<Style> edit(@RequestBody Style style){
        return new ResponseEntity<>(styleService.edit(style), HttpStatus.OK);
    }
    
    @PostMapping("/read")
    public ResponseEntity<Style> read(@RequestBody int id){
        return new ResponseEntity<>(styleService.read(id), HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/delete")
    public ResponseEntity<?> delete(Style style){
        return new ResponseEntity<>(styleService.delete(style), HttpStatus.OK);
    }
}
