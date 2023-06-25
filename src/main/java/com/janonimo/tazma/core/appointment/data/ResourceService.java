package com.janonimo.tazma.core.appointment.data;

import com.janonimo.tazma.core.appointment.Resource;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 *
 * @author JANONIMO
 */
@Service
@RequiredArgsConstructor
public class ResourceService {

    private final ResourceRepository resourceRepository;

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
}
