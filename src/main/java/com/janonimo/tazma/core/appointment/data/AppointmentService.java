package com.janonimo.tazma.core.appointment.data;

import com.janonimo.tazma.core.appointment.Appointment;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 *
 * @author JANONIMO
 */
@Service
@RequiredArgsConstructor
public class AppointmentService {
    private final AppointmentRepository appointmentRepo;
    
    /**
     * Get appointment by id
     * @param id
     * @return 
     */
    public Appointment find(Integer id){
        return appointmentRepo.findById(id).get();
    }
    
    /**
     * Create new appointment
     * @param appointment
     * @return 
     */
    public Appointment create(Appointment appointment){
        return appointmentRepo.save(appointment);
    }
    
    /**
     * Edit existing appointment
     * @param appointment
     * @return 
     */
    public Appointment edit(Appointment appointment){
        return create(appointment);
    }
    
    /**
     * Returns all available appointments for that user
     * @param userId
     * @return 
     */
    public List<Appointment> stylistAppointments(Integer userId){
        return appointmentRepo.findAllAppointmentsByStylist(userId);
    }
    
     /**
     * Returns all available appointments for that user
     * @param userId
     * @return 
     */
    public List<Appointment> clientAppointments(Integer userId){
        return appointmentRepo.findAllAppointmentsByStylist(userId);
    }
    /**
     * Delete existing appointment 
     * @param id
     */
    public void delete(Integer id){
        appointmentRepo.deleteById(id);
    }
    
}

