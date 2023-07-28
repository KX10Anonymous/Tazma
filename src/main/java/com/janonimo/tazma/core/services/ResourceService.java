package com.janonimo.tazma.core.services;

import com.janonimo.tazma.core.appointment.Resource;
import com.janonimo.tazma.core.appointment.Style;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author JANONIMO
 */
@Service
@RequiredArgsConstructor
public class ResourceService {

    private final ResourceRepository resourceRepository;


    public Resource create(Resource resource) {
        return resourceRepository.save(resource);
    }

    public Resource edit(Resource resource) {
        return resourceRepository.save(resource);
    }

    public Resource read(Long id) {
        return Objects.requireNonNull(resourceRepository.findById(id)).orElse(null);
    }

    public void delete(Resource resource) {
        resourceRepository.delete(resource);
    }

    public List<Resource> resourcesByStyle(Long id) {
        return resourceRepository.findAllResourcesByStyle(id);
    }

    public Resource findByStyle(Long id){
        return resourceRepository.findResourceByStyle(id);
    }
    public Resource save(MultipartFile file, Style style) {
        final String PATH = "C:/Users/JANONIMO/Documents/PROJECTS/Tazma/tazma-web/tazma/public/src/";
        String path = PATH +  file.getOriginalFilename();
        try {
            file.transferTo(new File(path));
            String url = "/src/" + file.getOriginalFilename();
            Resource resource = new Resource();
            resource.setPath(url);
            resource.setStyle(style);
            return resourceRepository.save(resource);

        } catch (IOException ex) {
            return null;
        }

    }
}
