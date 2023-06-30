package com.janonimo.tazma.rest.auth;

import com.janonimo.tazma.user.Role;
import com.janonimo.tazma.user.StylistStatus;
import com.janonimo.tazma.user.User;
import com.janonimo.tazma.user.UserRepository;
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
    private final AuthenticationService authService;
    public List<User> findByAddress(String jwt){
        User user = authService.read(jwt);
        return repository.findByAddress(user.getAddress(), user.getProvince());
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
