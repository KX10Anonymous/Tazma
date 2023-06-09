package com.janonimo.tazma.core.services;

import com.janonimo.tazma.core.appointment.Location;
import java.util.Set;
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
    public Location read(Long id){
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
    
    /**
     * Find active appointments of the stylist around a specific area.
     * @param locationName
     * @return 
     */
//    public Set<Location> findByName(String locationName){
//        return locationRepository.findByName(locationName);
//    }
//
//     /**
//     * Find all appointments of the stylist around a specific area.
//     * @param locationName
//     * @return
//     */
//    public Set<Location> findAllByName(String locationName){
//        return locationRepository.findAllByName(locationName);
//    }
}
