package com.janonimo.tazma.core.rest;

import com.github.javafaker.Faker;
import com.janonimo.tazma.core.appointment.Appointment;
import com.janonimo.tazma.core.appointment.AppointmentType;
import com.janonimo.tazma.core.appointment.Style;
import com.janonimo.tazma.core.services.AppointmentService;
import com.janonimo.tazma.core.services.StyleService;
import com.janonimo.tazma.user.*;
import com.janonimo.tazma.user.services.UserService;
import com.janonimo.tazma.util.TownTyposCorrector;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api/demo")
@RequiredArgsConstructor
public class DemoController {
    private final UserService userService;
    private final StyleService styleService;
    private final AppointmentService appointmentService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/appointments")
    public ResponseEntity<?> createAppointments(){
        List<User> stylists = userService.stylists();
        List<User> clients = userService.clients();
        for(User stylist : stylists){
            Random random1 = new Random(LocalDateTime.MAX.getNano());
            Random random3 = new Random(random1.nextInt(1,399));
            Random random2 = new Random(random3.nextInt(1,399));
            for(int r = 0; r < 6; r++){
                Random random4 = new Random(100 - random2.nextInt(r,97));
                int index = random4.nextInt(1,99);
                User client = clients.get(index);
                Appointment appointment = new Appointment();
                Random rand = new Random();
                int hour = rand.nextInt(1,24);
                int day = rand.nextInt(1, 30);
                int min = rand.nextInt(1, 59);
                appointment.setAppointmentTime(LocalDateTime.of(2023, 7, day, hour,min));
                appointment.setAppointmentType(stylist.getAppointmentType());
                rand = new Random();
                Long id = rand.nextLong(1,3+1);
                Style s = styleService.read(id);
                appointment.setStyle(s);
                appointment.setStylist(stylist);
                appointment.setClient(client);

                appointment.setCounterOffer(rand.nextDouble(200, 250));
                appointment.setClientOffer(rand.nextDouble(100, 150));
                appointment.setAgreedAmount(rand.nextDouble(200, 250));
                appointmentService.create(appointment);
            }
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/stylists")
    public  ResponseEntity<?> addStylists(){

        for(int r = 0; r < 50; r++){
            Faker faker = new Faker(new Random(100+r+1));
            User user = new User();
            String firstname =faker.name().firstName();
            String lastname = faker.name().lastName();
            user.setFirstname(firstname);
            user.setLastname(lastname);
            user.setPassword(passwordEncoder.encode("123456789"));
            user.setGender(Gender.MALE);
            Role role = Role.builder()
                    .roleName(RoleName.STYLIST).priority(RolePriority.MAIN).build();
            Role role2 = Role.builder()
                    .roleName(RoleName.CLIENT).priority(RolePriority.SECONDARY).build();
            ArrayList<Role> roles = new ArrayList<>();
            roles.add(role);
            roles.add(role2);

            user.setRoles(roles);
            user.setAppointmentType(AppointmentType.HOUSE_CALL);
            user.setStatus(StylistStatus.AVAILABLE);
            Random phonerandom = new Random();
            user.setEmail((firstname + lastname).toLowerCase().trim()+"@gmail.com");
            user.setPhone(String.valueOf(phonerandom.nextInt(1000000000,1988888881)));

            user = userService.save(user);

            Random random = new Random();
            int randomIndex = random.nextInt(TownTyposCorrector.GAUTENG_NEIGHBORHOODS.size());
            String area = TownTyposCorrector.GAUTENG_NEIGHBORHOODS.get(randomIndex);

            Address address = new Address();
            address.setProvince("Gauteng");
            address.setHouseNumber("");
            address.setArea("Johannesburg");
            address.setSuburb(area);
            address.setStreetName("");
            userService.save(user, address);

        }
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/clients")
    public  ResponseEntity<?> addClients(){

        for(int r = 0; r < 100; r++){
            Faker faker = new Faker(new Random(r));
            User user = new User();
            String firstname =faker.name().firstName();
            String lastname = faker.name().lastName();
            user.setFirstname(firstname);
            user.setLastname(lastname);
            user.setPassword(passwordEncoder.encode("123456789"));
            user.setGender(Gender.MALE);
            Role role = Role.builder()
                    .roleName(RoleName.CLIENT).priority(RolePriority.MAIN).build();
            ArrayList<Role> roles = new ArrayList<>();
            roles.add(role);
            user.setRoles(roles);
            Random phonerandom = new Random();
            user.setEmail((firstname + lastname).toLowerCase().trim()+"@gmail.com");
            user.setPhone(String.valueOf(phonerandom.nextInt(1000000000,1988888881)));

            user = userService.save(user);

            Random random = new Random();
            int randomIndex = random.nextInt(TownTyposCorrector.GAUTENG_NEIGHBORHOODS.size());
            String area = TownTyposCorrector.GAUTENG_NEIGHBORHOODS.get(randomIndex);

            Address address = new Address();
            address.setProvince("Gauteng");
            address.setHouseNumber("");
            address.setArea("Johannesburg");
            address.setSuburb(area);
            address.setStreetName("");
            userService.save(user, address);

        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/admin")
    public  ResponseEntity<?> admin(){
        User user = new User();

        user.setFirstname("Ronnie");
        user.setLastname("Mamidza");
        user.setPassword(passwordEncoder.encode("123456789"));
        user.setGender(Gender.MALE);
        Role role = Role.builder()
                .roleName(RoleName.ADMIN).priority(RolePriority.MAIN).build();
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(role);
        user.setRoles(roles);
        Random phonerandom = new Random();
        user.setEmail("mamidzaronnie@gmail.com");
        user.setPhone(String.valueOf(phonerandom.nextInt(1000000000, 1988888881)));

        user = userService.save(user);

        Random random = new Random();
        int randomIndex = random.nextInt(TownTyposCorrector.GAUTENG_NEIGHBORHOODS.size());
        String area = TownTyposCorrector.GAUTENG_NEIGHBORHOODS.get(randomIndex);

        Address address = new Address();
        address.setProvince("Gauteng");
        address.setHouseNumber("4757");
        address.setArea("Johannesburg");
        address.setSuburb(area);
        address.setStreetName("Zwane Street");
        userService.save(user, address);


        return new ResponseEntity<>(HttpStatus.OK);
    }
}
