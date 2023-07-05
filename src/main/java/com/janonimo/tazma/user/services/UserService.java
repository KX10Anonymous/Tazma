package com.janonimo.tazma.user.services;

import com.janonimo.tazma.token.TokenRepository;
import com.janonimo.tazma.user.Address;
import com.janonimo.tazma.user.Role;
import com.janonimo.tazma.user.StylistStatus;
import com.janonimo.tazma.user.User;

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

    public Address save(String jwt, Address address){
        if(address != null){
            User user = tokenRepository.findByToken(jwt).get().user;
            address.setUser(user);
            return addressRepository.save(address);
        }
        return null;
    }
    public User save(User user){
        return repository.save(user);
    }
    public List<User> findByAddress(String jwt){
        User user = tokenRepository.findByToken(jwt).get().user;
        Address address = addressRepository.getUserAddress(user.getId()).get();
        ArrayList<User> stylists = (ArrayList<User>) repository.findByAddress(address.getArea(), address.getProvince());
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
