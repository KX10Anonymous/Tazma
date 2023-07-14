package com.janonimo.tazma.core.services;

import com.janonimo.tazma.core.appointment.Appointment;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author JANONIMO
 */
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
     @Query(value = """
      select a from Appointment a
      where a.stylist.Id = :id
      """)
     ArrayList<Appointment> findAllAppointmentsByStylist(Long id);
    
    @Query(value = """
      select a from Appointment a
      where a.client.Id = :id 
      """)
    ArrayList<Appointment> findAllAppointmentsByClient(Long id);
}
