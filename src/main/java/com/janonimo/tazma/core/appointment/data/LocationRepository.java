package com.janonimo.tazma.core.appointment.data;

import com.janonimo.tazma.core.appointment.Location;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author JANONIMO
 */
public interface LocationRepository extends JpaRepository<Location, Integer> {
   
}
