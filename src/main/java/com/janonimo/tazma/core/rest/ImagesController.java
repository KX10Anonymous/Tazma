package com.janonimo.tazma.core.rest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/images")
public class ImagesController {

    @GetMapping("/read/{imageUrl:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable String imageUrl) throws MalformedURLException {
        Path imagePath = Paths.get("C:/Users/JANONIMO/PROJECTS/Tazma/src/main/resources/StyleResources/"+ imageUrl);
        Resource resource = new UrlResource(imagePath.toUri());

        if (resource.exists() && resource.isReadable()) {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG) // Adjust content type based on your image format
                    .body(resource);
        }
        return ResponseEntity.notFound().build();
    }
}