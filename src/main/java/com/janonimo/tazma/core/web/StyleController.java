package com.janonimo.tazma.core.web;


import com.janonimo.tazma.core.appointment.Style;
import com.janonimo.tazma.core.services.StyleService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author JANONIMO
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/style")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8080"})
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
    @PutMapping("/edit")
    public ResponseEntity<Style> edit(@RequestBody Style style){
        return new ResponseEntity<>(styleService.edit(style), HttpStatus.OK);
    }
    
    @PostMapping("/read/{id}")
    public ResponseEntity<Style> read(@PathVariable int id){
        return new ResponseEntity<>(styleService.read(id), HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(Style style){
        return new ResponseEntity<>(styleService.delete(style), HttpStatus.OK);
    }
}
