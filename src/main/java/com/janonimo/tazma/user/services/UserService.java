package com.janonimo.tazma.user.services;

import com.janonimo.tazma.token.TokenRepository;
import com.janonimo.tazma.user.Address;
import com.janonimo.tazma.user.RoleName;
import com.janonimo.tazma.user.StylistStatus;
import com.janonimo.tazma.user.User;

import java.util.ArrayList;
import java.util.List;

import com.janonimo.tazma.util.ProvinceTyposCorrector;
import com.janonimo.tazma.util.TownTyposCorrector;
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
            //Correct Area Name if there's a typo
            address.setArea(TownTyposCorrector.correctNeighborhoodTypo(address.getArea()));
            //Correct Province name if there's a typo
            address.setProvince(ProvinceTyposCorrector.correctProvinceTypo(address.getProvince()));
            return addressRepository.save(address);
        }
        return null;
    }

    public Address save(User user, Address address){
        if(address != null){
            address.setUser(user);
            address = addressRepository.save(address);
            user.setAddress(address);
            save(user);
            return address;
        }
        return null;
    }

    public List<User> stylists(){
        return repository.stylists();
    }

    public List<User> clients(){
        return repository.clients();
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
            if(u.getStatus() == StylistStatus.AVAILABLE && u.getRoleName() == RoleName.STYLIST){
                users.add(u);
            }
        }
        return users;
    }

    public User read(Long id){
        return repository.getReferenceById(id);
    }

    public boolean emailExists(String email){
        return repository.findByEmail(email).isEmpty();
    }

    public boolean phoneExists(String email){
        return repository.findByEmail(email).isEmpty();
    }

}
