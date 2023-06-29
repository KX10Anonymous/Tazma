package com.janonimo.tazma.core.services;

import com.janonimo.tazma.core.appointment.Appointment;
import com.janonimo.tazma.core.appointment.Location;
import java.util.ArrayList;
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
    private final LocationService locationService;
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
        Location loc = appointment.getLocation();
        if(loc.getId() == null){
            loc = locationService.create(loc);
            appointment.setLocation(loc);
        }else{
            locationService.edit(appointment.getLocation());
        }
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
    /**
     * 
     * @param name
     * @return 
     */
    public List<Appointment> readByName(String name){
        ArrayList<Appointment> list= new ArrayList<>();
        for(Location loc : locationService.findByName(name)){
            list.add(loc.getAppointment());
        }
        return list;
    }
    
    /**
     * 
     * @param name
     * @return 
     */
    public List<Appointment> readAllByName(String name){
        ArrayList<Appointment> list= new ArrayList<>();
        for(Location loc : locationService.findAllByName(name)){
            list.add(loc.getAppointment());
        }
        return list;
    }
}

