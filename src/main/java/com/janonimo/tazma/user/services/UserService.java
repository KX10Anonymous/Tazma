package com.janonimo.tazma.user.services;

import com.janonimo.tazma.token.Token;
import com.janonimo.tazma.token.TokenRepository;
import com.janonimo.tazma.user.Address;
import com.janonimo.tazma.user.StylistStatus;
import com.janonimo.tazma.user.User;
import com.janonimo.tazma.util.ProvinceTyposCorrector;
import com.janonimo.tazma.util.TownTyposCorrector;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
            Token token = tokenRepository.findByToken(jwt).orElse(null);
            if(token != null){
                User user = token.getUser();
                address.setUser(user);
                //Correct Area Name if there's a typo
                address.setArea(TownTyposCorrector.correctNeighborhoodTypo(address.getArea()));
                //Correct Province name if there's a typo
                address.setProvince(ProvinceTyposCorrector.correctProvinceTypo(address.getProvince()));
                return addressRepository.save(address);
            }else{
                return null;
            }
        }
        return null;
    }

    public void save(User user, Address address){
        if(address != null){
            address.setUser(user);
            address = addressRepository.save(address);
            user.setAddress(address);
            save(user);
        }
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
    public List<User> findByAddress(String jwt, int condition){
         Token token = tokenRepository.findByToken(jwt).orElse(null);
        if(token != null){
            User user = token.getUser();
            if(user.getAddress() != null){
                Address address = addressRepository.getUserAddress(user.getId()).orElse(user.getAddress());
                List<User> stylists;
                if(condition == 1){
                    stylists = repository.findByAddress(address.getArea(), address.getProvince(), address.getSuburb());
                }else if(condition == 2){
                    stylists = repository.findByAddress(address.getArea(), address.getProvince());
                }else{
                    stylists = repository.findByAddress(address.getProvince());
                }
                for(User stylist : stylists){
                    stylist.setAddress(addressRepository.getUserAddress(stylist.getId()).orElse(stylist.getAddress()));
                }
                return stylists;
            }else{
                return null;
            }

        }
        return null;
    }

    public List<User> findByStatus(String jwt){
        ArrayList<User> users = new ArrayList<>();
        for(User u : findByAddress(jwt, 1)){
            if(u.getStatus() == StylistStatus.AVAILABLE){
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
