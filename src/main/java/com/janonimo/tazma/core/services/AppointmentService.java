package com.janonimo.tazma.core.services;

import com.janonimo.tazma.core.appointment.Appointment;
import com.janonimo.tazma.core.appointment.AppointmentType;
import com.janonimo.tazma.core.appointment.Location;
import com.janonimo.tazma.core.maps.GeocodingService;
import com.janonimo.tazma.core.rest.response.AppointmentResponse;
import com.janonimo.tazma.user.Address;
import com.janonimo.tazma.user.User;
import com.janonimo.tazma.user.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("unused")
@Service
@RequiredArgsConstructor
public class AppointmentService {
    private final AppointmentRepository appointmentRepo;
    private final LocationService locationService;
    private final UserService userService;
    private final GeocodingService geocodingService;

    public Appointment find(Long id) {
        return appointmentRepo.getReferenceById(id);
    }


    public Appointment create(String jwt, Appointment appointment) {
        Address clientAddress = appointment.getClient().getAddress();

        if (appointment.getId() == null) {
            double clientLongitude = geocodingService.getLongitudeFromResponse(geocodingService.geocodeAddress(clientAddress));
            double clientLatitude = geocodingService.getLatitudeFromResponse(geocodingService.geocodeAddress(clientAddress));

            List<User> stylists = userService.findByAddress(jwt,1);
            if(stylists == null || stylists.isEmpty()){
                stylists = userService.findByAddress(jwt, 2);
            }else{
                stylists = userService.findByAddress(jwt, 3);
            }
            if (stylists == null || stylists.size() < 1) {
                return null;
            }
            User appointed = stylists.get(0);

            double shortestDistance = geocodingService.calculateDistance(clientLatitude, clientLongitude, geocodingService.getLatitudeFromResponse(geocodingService.geocodeAddress(stylists.get(0).getAddress())), geocodingService.getLongitudeFromResponse(geocodingService.geocodeAddress(stylists.get(0).getAddress())));
            for (User stylist : stylists) {
                double styLongitude = geocodingService.getLongitudeFromResponse(geocodingService.geocodeAddress(stylist.getAddress()));
                double styLatitude = geocodingService.getLatitudeFromResponse(geocodingService.geocodeAddress(stylist.getAddress()));
                if (geocodingService.calculateDistance(clientLatitude, clientLongitude, styLatitude, styLongitude) <= shortestDistance) {
                    if (appointment.getAppointmentType() == appointment.getAppointmentType()) {
                        appointed = stylist;
                        shortestDistance = geocodingService.calculateDistance(clientLatitude, clientLongitude, styLatitude, styLongitude);
                    }
                }
            }
            if (appointment.getAppointmentType() == AppointmentType.CLIENT_VISIT) {
                Location temp = new Location();
                double lat = geocodingService.getLatitudeFromResponse(geocodingService.geocodeAddress(appointment.getClient().getAddress()));
                double lon = geocodingService.getLongitudeFromResponse(geocodingService.geocodeAddress(appointment.getClient().getAddress()));
                temp.setLatitude(lat);
                temp.setLongitude(lon);
                temp.setLocationName(clientAddress.getProvince() + " " + clientAddress.getArea() + " " + clientAddress.getStreetName());
                Location loc = locationService.create(temp);
                appointment.setLocation(loc);
            } else {
                Location temp = new Location();
                double lat = geocodingService.getLatitudeFromResponse(geocodingService.geocodeAddress(appointed.getAddress()));
                double lon = geocodingService.getLongitudeFromResponse(geocodingService.geocodeAddress(appointed.getAddress()));
                temp.setLatitude(lat);
                temp.setLongitude(lon);
                temp.setLocationName(clientAddress.getProvince() + " " + clientAddress.getArea() + " " + clientAddress.getStreetName());
                Location loc = locationService.create(temp);
                appointment.setLocation(loc);
            }
        } else {
            appointmentRepo.save(appointment);
        }
        return appointmentRepo.save(appointment);
    }


    public void create(Appointment appointment) {
        appointmentRepo.save(appointment);
    }

    public Appointment edit(String jwt, Appointment appointment) {
        return create(jwt, appointment);
    }

    public ArrayList<AppointmentResponse> stylistAppointments(Long userId) {
        ArrayList<Appointment> appointments = appointmentRepo.findAllAppointmentsByStylist(userId);
        return getAppointments(appointments);
    }

    public ArrayList<AppointmentResponse> clientAppointments(Long userId) {
        ArrayList<Appointment> appointments = appointmentRepo.findAllAppointmentsByClient(userId);

        return getAppointments(appointments);
    }

    public void delete(Long id) {
        appointmentRepo.deleteById(id);
    }

    private ArrayList<AppointmentResponse> getAppointments(ArrayList<Appointment> appointments) {
        ArrayList<AppointmentResponse> response = new ArrayList<>();
        for (Appointment appointment : appointments) {
            if (appointment.getId() == null || appointment.getClient() == null || appointment.getAppointmentTime() == null || appointment.getStylist() == null || appointment.getAppointmentType() == null || appointment.getStyle() == null) {
                continue;
            }
            AppointmentResponse r = AppointmentResponse.builder().id(appointment.getId()).client(appointment.getClient().getFirstname() + " " + appointment.getClient().getLastname()).time(appointment.getAppointmentTime().toString()).agreed(appointment.getAgreedAmount()).counter(appointment.getCounterOffer()).url("").stylist(appointment.getStylist().getFirstname() + " " + appointment.getStylist().getLastname()).offer(appointment.getClientOffer()).type(appointment.getAppointmentType()).location("").status(appointment.getStatus()).build();
            response.add(r);

        }
        return response;
    }

}

