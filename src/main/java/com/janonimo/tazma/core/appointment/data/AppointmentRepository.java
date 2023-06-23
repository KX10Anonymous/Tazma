package com.janonimo.tazma.core.appointment.data;

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
      on a.user.id = u.id\s
      where u.id = :id
      """)
    List<Appointment> findAllAppointmentsByUser(Integer id);
}
