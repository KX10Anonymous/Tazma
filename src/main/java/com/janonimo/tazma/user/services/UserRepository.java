
package com.janonimo.tazma.user.services;

import java.util.List;
import java.util.Optional;

import com.janonimo.tazma.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author JANONIMO
 */
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    
    @Query(value="""
                   select a from User a where a.address.area = :town
                   and a.role = 'STYLIST' and a.address.province = :province
           """)
    List<User> findByAddress(String town, String province);

    @Query(value="""
                   select a from User a where a.firstname = :firstname
                   and a.role = 'STYLIST' and a.lastname = :lastname and a.address.area =:town
           """)
    List<User>findByNames(String firstname, String lastname, String town);
}
