package com.janonimo.tazma.user.services;

import com.janonimo.tazma.rest.auth.AuthenticationService;
import com.janonimo.tazma.token.TokenRepository;
import com.janonimo.tazma.user.Address;
import com.janonimo.tazma.user.Role;
import com.janonimo.tazma.user.StylistStatus;
import com.janonimo.tazma.user.User;
import com.janonimo.tazma.user.services.UserRepository;
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
public class UserService {
    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final AddressRepository addressRepository;

    public List<User> findByAddress(String jwt){
        User user = tokenRepository.findByToken(jwt).get().user;
        Address address = addressRepository.getUserAddress(user.getId()).get();
        ArrayList<User> stylists = (ArrayList<User>) repository.findByAddress(address.getTown(), address.getProvince());
        for(User stylist : stylists){
            stylist.setAddress(addressRepository.getUserAddress(stylist.getId()).get());
        }
        return stylists;
    }

    public List<User> findByStatus(String jwt){
        ArrayList<User> users = new ArrayList<>();
        for(User u : findByAddress(jwt)){
            if(u.getStatus() == StylistStatus.AVAILABLE && u.getRole() == Role.STYLIST){
                users.add(u);
            }
        }
        return users;
    }

}
