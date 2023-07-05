package com.janonimo.tazma.core.rest;

import com.janonimo.tazma.core.reporting.Post;
import com.janonimo.tazma.core.reporting.PostService;
import com.janonimo.tazma.token.Token;
import com.janonimo.tazma.token.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8080"})
public class PostsController {
    private final PostService postService;
    private final TokenRepository tokenRepository;

    @PostMapping("/upload/{jwt}")
    public ResponseEntity<Post> create(@PathVariable String jwt, @RequestBody MultipartFile file){
        if(validateUser(jwt))
            return new ResponseEntity<>(postService.save(file, jwt), HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("/delete/{jwt}")
    public ResponseEntity<Post> delete(@PathVariable String jwt,@RequestBody Long id){
        if(validateUser(jwt)){
            Post post = postService.read(id);
            postService.delete(jwt, post);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<List<Post>> posts(@PathVariable Long id){
        return new ResponseEntity<>(postService.posts(id),HttpStatus.OK);
    }

    private boolean validateUser(String jwt){
        Token token = tokenRepository.findByToken(jwt).get();
        if(token.isRevoked() || token.isExpired())
            return false;
        return true;
    }

}
