package com.janonimo.tazma.core.rest;

import com.janonimo.tazma.core.reporting.Review;
import com.janonimo.tazma.core.reporting.services.ReviewService;
import com.janonimo.tazma.token.Token;
import com.janonimo.tazma.token.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/reviews")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8080"})
public class ReviewsController {
    private final TokenRepository tokenRepository;
    private final ReviewService reviewService;

    @GetMapping("/reviews/{jwt}")
    public ResponseEntity<?> reviews(@PathVariable String jwt){
        if(validateUser(jwt)){
            return new ResponseEntity<>(reviewService.reviews(jwt), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/create/{jwt}")
    public ResponseEntity<Review> create(@PathVariable String jwt, @RequestBody Review review){
        if(validateUser(jwt)){
            return new ResponseEntity<>(reviewService.create(jwt, review), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("/edit/{jwt}")
    public ResponseEntity<Review> edit(@PathVariable String jwt, @RequestBody Review review){
        if(validateUser(jwt)){
            return new ResponseEntity<>(reviewService.edit(jwt, review), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("/delete/{jwt}")
    public ResponseEntity<Review> delete(@PathVariable String jwt, @RequestBody Review review){
        if(validateUser(jwt)){
            reviewService.delete(jwt, review);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    private boolean validateUser(String jwt){
        Token token = tokenRepository.findByToken(jwt).get();
        if(token.isRevoked() || token.isExpired())
            return false;
        return true;
    }
}
