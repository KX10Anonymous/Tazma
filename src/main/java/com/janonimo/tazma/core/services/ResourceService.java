package com.janonimo.tazma.core.services;

import com.janonimo.tazma.core.appointment.Resource;
import com.janonimo.tazma.core.appointment.Style;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author JANONIMO
 */
@Service
@RequiredArgsConstructor
public class ResourceService {

    private final ResourceRepository resourceRepository;
    private final String PATH = "C:/Users/JANONIMO/Documents/PROJECTS/Tazma/tazma-web/tazma/public/src/";

    /**
     * @param resource
     * @return
     */
    public Resource create(Resource resource) {
        return resourceRepository.save(resource);
    }

    /**
     * @param resource
     * @return
     */
    public Resource edit(Resource resource) {
        return resourceRepository.save(resource);
    }

    /**
     *
     * @param id
     * @return
     */
    public Resource read(Long id) {
        return resourceRepository.findById(id).get();
    }

    /**
     * @param resource
     */
    public void delete(Resource resource) {
        resourceRepository.delete(resource);
    }

    /**
     *
     * @param id
     * @return
     */
    public List<Resource> resourcesByStyle(Long id) {
        return resourceRepository.findAllResourcesByStyle(id);
    }

    /**
     *
     * @return
     */
    public List<Resource> resources() {
        return resourceRepository.findAll();
    }

    public Resource findByStyle(Long id){
        return resourceRepository.findResourceByStyle(id);
    }
    public Resource save(MultipartFile file, Style style) {
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

    /**
     * Download Image from database and storage
     *
     * @param id
     * @return
     */
    public byte[] download(Long id) {
        byte[] image = null;
        Optional<Resource> file = resourceRepository.findById(id);
        try {
            String path = file.get().getPath();
            image = Files.readAllBytes(new File(path).toPath());
        } catch (IOException ex) {
        }

        if (image != null) {
            return image;
        } else {
            return null;
        }
    }
}
