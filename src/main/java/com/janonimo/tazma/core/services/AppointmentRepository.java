package com.janonimo.tazma.core.services;

import com.janonimo.tazma.core.appointment.Appointment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author JANONIMO
 */
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
     @Query(value = """
      select a from Appointment a inner join User u\s
      on a.stylist.id = u.id\s
      where u.id = :id and a.status = 'ACTIVE'
      """)
    List<Appointment> findAllAppointmentsByStylist(Integer id);
    
    @Query(value = """
      select a from Appointment a inner join User u\s
      on a.client.id = u.id\s
      where u.id = :id and a.status = 'ACTIVE'
      """)
    List<Appointment> findAllAppointmentsByClient(Integer id);
}
