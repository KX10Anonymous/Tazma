
package com.janonimo.tazma.user;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author JANONIMO
 */
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    
    @Query(value="""
                   select a from User a where a.address = :address
                   and a.role = 'STYLIST' and a.province = :province
           """)
    List<User> findByAddress(String address, String province);
}
