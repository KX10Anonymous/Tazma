package com.janonimo.tazma.core.appointment.data;

import com.janonimo.tazma.core.appointment.Location;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 *
 * @author JANONIMO
 */
@Service
@RequiredArgsConstructor
public class LocationService {
    
    private final LocationRepository locationRepository;
    
    /**
     * 
     * @param id
     * @return 
     */
    public Location read(Integer id){
        return locationRepository.findById(id).get();
    }
    
    /**
     * Save and flush the location.
     * @param location
     * @return 
     */
    public Location create(Location location){
        return locationRepository.saveAndFlush(location);
    }
    
    public Location edit(Location location){
        return create(location);
    }
  
    /**
     * Delete location
     * @param location 
     */
    public void delete(Location location){
        locationRepository.delete(location);
    }
    
}
