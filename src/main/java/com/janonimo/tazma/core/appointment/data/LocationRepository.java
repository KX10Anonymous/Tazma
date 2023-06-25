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
    @Query(value = """
      select l from Location l join Appointment a\s
      on l.id = a.location.id\s
      where l.name = :name and a.status\s = 'ACTIVE'
      join User u\s on a.stylist.id\s = u.id\s
      """)
    public List<Location> findByName(String name);
    
     @Query(value = """
      select l from Location l join Appointment a\s
      on l.id = a.location.id\s
      where l.name = :name
      join User u\s on a.stylist.id\s = u.id\s
      """)
    public List<Location> findAllByName(String name);
}
