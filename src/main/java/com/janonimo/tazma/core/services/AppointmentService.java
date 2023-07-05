package com.janonimo.tazma.core.services;

import com.janonimo.tazma.core.appointment.Appointment;
import com.janonimo.tazma.core.appointment.AppointmentType;
import com.janonimo.tazma.core.appointment.Location;
import java.util.ArrayList;
import java.util.List;

import com.janonimo.tazma.core.maps.GeocodingService;
import  com.janonimo.tazma.user.Address;
import com.janonimo.tazma.user.User;
import com.janonimo.tazma.user.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AppointmentService {
    private final AppointmentRepository appointmentRepo;
    private final LocationService locationService;
    private final UserService userService;
    private final GeocodingService geocodingService;
    public Appointment find(Integer id){
        return appointmentRepo.findById(id).get();
    }
    

    public Appointment create(String jwt, Appointment appointment){
        Address clientAddress = appointment.getClient().getAddress();

        if(appointment.getId() == null){
            Double clientLongitude = geocodingService.getLongitudeFromResponse(geocodingService.geocodeAddress(clientAddress));
            Double clientLatitude = geocodingService.getLatitudeFromResponse(geocodingService.geocodeAddress(clientAddress));

            ArrayList<User> stylists = (ArrayList<User>) userService.findByAddress(jwt);
            User appointed = stylists.get(0);
            Double shortestDistance = geocodingService.calculateDistance(
              clientLatitude, clientLongitude,
              geocodingService.getLatitudeFromResponse(geocodingService.geocodeAddress(stylists.get(0).getAddress())),
                    geocodingService.getLongitudeFromResponse(geocodingService.geocodeAddress(stylists.get(0).getAddress()))
            );
            for(User stylist : stylists) {
                Double styLongitude = geocodingService.getLongitudeFromResponse(
                        geocodingService.geocodeAddress(stylist.getAddress()));
                Double styLatitude = geocodingService.getLatitudeFromResponse(
                        geocodingService.geocodeAddress(stylist.getAddress()));
                if(geocodingService.calculateDistance(clientLatitude, clientLongitude, styLatitude, styLongitude) <= shortestDistance){
                    if(appointment.getAppointmentType() == appointment.getAppointmentType()){
                        appointed = stylist;
                        shortestDistance = geocodingService.calculateDistance(clientLatitude, clientLongitude, styLatitude, styLongitude);
                    }
                }
            }
            if(appointment.getAppointmentType() == AppointmentType.CLIENT_VISIT){
                Location temp = new Location();
                Double lat = geocodingService.getLatitudeFromResponse(geocodingService.geocodeAddress(appointment.getClient().getAddress()));
                Double lon = geocodingService.getLongitudeFromResponse(geocodingService.geocodeAddress(appointment.getClient().getAddress()));
                temp.setLatitude(lat);
                temp.setLongitude(lon);
                temp.setLocationName(clientAddress.getProvince() +" " + clientAddress.getTown() + " " + clientAddress.getStreetName());
                Location loc = locationService.create(temp);
                appointment.setLocation(loc);
            }else{
                Location temp = new Location();
                Double lat = geocodingService.getLatitudeFromResponse(geocodingService.geocodeAddress(appointed.getAddress()));
                Double lon = geocodingService.getLongitudeFromResponse(geocodingService.geocodeAddress(appointed.getAddress()));
                temp.setLatitude(lat);
                temp.setLongitude(lon);
                temp.setLocationName(clientAddress.getProvince() +" " + clientAddress.getTown() + " " + clientAddress.getStreetName());
                Location loc = locationService.create(temp);
                appointment.setLocation(loc);
            }
        }else{
            appointmentRepo.save(appointment);
        }
        return appointmentRepo.save(appointment);
    }
    

    public Appointment edit(String jwt, Appointment appointment){
        return create(jwt,appointment);
    }

    public List<Appointment> stylistAppointments(Long userId){
        return appointmentRepo.findAllAppointmentsByStylist(userId);
    }

    public List<Appointment> clientAppointments(Long userId){
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

