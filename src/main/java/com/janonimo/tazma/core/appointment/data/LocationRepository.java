package com.janonimo.tazma.core.appointment.data;

import com.janonimo.tazma.core.appointment.Location;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author JANONIMO
 */
public interface LocationRepository extends JpaRepository<Location, Integer> {
    
}
