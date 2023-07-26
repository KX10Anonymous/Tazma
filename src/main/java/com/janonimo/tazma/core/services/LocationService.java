package com.janonimo.tazma.core.services;

import com.janonimo.tazma.core.appointment.Location;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 *
 * @author JANONIMO
 */
@Service
@RequiredArgsConstructor
public class LocationService {
    
    private final LocationRepository locationRepository;
    

    public Location read(Long id){
        return Objects.requireNonNull(locationRepository.findById(id)).get();
    }

    public Location create(Location location){
        return locationRepository.saveAndFlush(location);
    }
    
    public Location edit(Location location){
        return create(location);
    }

    public void delete(Location location){
        locationRepository.delete(location);
    }

}
