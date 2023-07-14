package com.janonimo.tazma.core.services;

import com.janonimo.tazma.core.appointment.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.janonimo.tazma.core.maps.GeocodingService;
import com.janonimo.tazma.core.rest.response.AppointmentResponse;
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
    private final ResourceService resourceService;
    private final StyleService styleService;
    public Appointment find(Long id){
        return appointmentRepo.getReferenceById(id);
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
                temp.setLocationName(clientAddress.getProvince() +" " + clientAddress.getArea() + " " + clientAddress.getStreetName());
                Location loc = locationService.create(temp);
                appointment.setLocation(loc);
            }else{
                Location temp = new Location();
                Double lat = geocodingService.getLatitudeFromResponse(geocodingService.geocodeAddress(appointed.getAddress()));
                Double lon = geocodingService.getLongitudeFromResponse(geocodingService.geocodeAddress(appointed.getAddress()));
                temp.setLatitude(lat);
                temp.setLongitude(lon);
                temp.setLocationName(clientAddress.getProvince() +" " + clientAddress.getArea() + " " + clientAddress.getStreetName());
                Location loc = locationService.create(temp);
                appointment.setLocation(loc);
            }
        }else{
            appointmentRepo.save(appointment);
        }
        return appointmentRepo.save(appointment);
    }
    

    public Appointment create(Appointment appointment){
        return appointmentRepo.save(appointment);
    }
    public Appointment edit(String jwt, Appointment appointment){
        return create(jwt,appointment);
    }

    public ArrayList<AppointmentResponse> stylistAppointments(Long userId){
        ArrayList<Appointment> appointments =  appointmentRepo.findAllAppointmentsByStylist(userId);
        return getAppointments(appointments);
    }

    public ArrayList<AppointmentResponse> clientAppointments(Long userId){
        ArrayList<Appointment> appointments = appointmentRepo.findAllAppointmentsByClient(userId);

        return getAppointments(appointments);
    }

    public void delete(Long id){
        appointmentRepo.deleteById(id);
    }

    private ArrayList<AppointmentResponse> getAppointments(ArrayList<Appointment> appointments){
        ArrayList<AppointmentResponse> response = new ArrayList<>();
        for(Appointment appointment : appointments){
            Logger logger = Logger.getLogger(AppointmentService.class.getName());
            logger.info("Test");
            Style style = styleService.read(appointment.getStyle().getId());
            Resource resource = resourceService.resourcesByStyle(style.getId()).get(0);
            AppointmentResponse r = AppointmentResponse.builder()
                    .id(appointment.getId())
                    .client(appointment.getClient().getFirstname() + " " +
                            appointment.getClient().getLastname())
                    .time(appointment.getAppointmentTime().toString())
                    .agreed(appointment.getAgreedAmount())
                    .counter(appointment.getCounterOffer())
                    .url(resource.getPath())
                    .stylist(appointment.getStylist().getFirstname() + " " +
                            appointment.getStylist().getLastname())
                    .offer(appointment.getClientOffer())
                    .type(appointment.getAppointmentType())
                    .location("")
                    .status(appointment.getStatus()).build();
            response.add(r);
        }
        return response;
    }
//    public List<Appointment> readByName(String name){
//        ArrayList<Appointment> list= new ArrayList<>();
//        for(Location loc : locationService.findByName(name)){
//            list.add(loc.getAppointments());
//        }
//        return list;
//    }

//    public List<Appointment> readAllByName(String name){
//        ArrayList<Appointment> list= new ArrayList<>();
//        for(Location loc : locationService.findAllByName(name)){
//            list.add(loc.getAppointment());
//        }
//        return list;
//    }


}

