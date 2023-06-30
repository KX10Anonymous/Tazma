package com.janonimo.tazma.core.services;

import com.janonimo.tazma.core.appointment.Appointment;
import com.janonimo.tazma.core.appointment.Location;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AppointmentService {
    private final AppointmentRepository appointmentRepo;
    private final LocationService locationService;

    public Appointment find(Integer id){
        return appointmentRepo.findById(id).get();
    }
    

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
    

    public Appointment edit(Appointment appointment){
        return create(appointment);
    }

    public List<Appointment> stylistAppointments(Integer userId){
        return appointmentRepo.findAllAppointmentsByStylist(userId);
    }

    public List<Appointment> clientAppointments(Integer userId){
        return appointmentRepo.findAllAppointmentsByStylist(userId);
    }

    public void delete(Integer id){
        appointmentRepo.deleteById(id);
    }

    public List<Appointment> readByName(String name){
        ArrayList<Appointment> list= new ArrayList<>();
        for(Location loc : locationService.findByName(name)){
            list.add(loc.getAppointment());
        }
        return list;
    }

    public List<Appointment> readAllByName(String name){
        ArrayList<Appointment> list= new ArrayList<>();
        for(Location loc : locationService.findAllByName(name)){
            list.add(loc.getAppointment());
        }
        return list;
    }
}

