package com.janonimo.tazma.core.services;

import com.janonimo.tazma.core.appointment.Resource;
import com.janonimo.tazma.core.appointment.Style;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.openide.util.Exceptions;
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
    private final String PATH = "C:/Users/JANONIMO/Documents/PROJECTS/Tazma/src/main/resources/StyleResources/";

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
    public Resource read(Integer id) {
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
    public List<Resource> resourcesByStyle(Integer id) {
        return resourceRepository.findAllResourcesByStyle(id);
    }

    /**
     *
     * @return
     */
    public List<Resource> resources() {
        return resourceRepository.findAll();
    }

    public Resource save(MultipartFile file, Style style) {
        String path = PATH.concat(file.getName());
        Resource resource = new Resource();
        resource.setPath(path);
        resource.setStyle(style);
        resource = create(resource);                    //Save Resource In Database
        if (resource != null) {
            try {
                file.transferTo(new File(path));
            } catch (IOException ex) {
                return null;
            }
        }
        return resource;
    }

    /**
     * Download Image from database and storage
     *
     * @param id
     * @return
     */
    public byte[] download(Integer id) {
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
