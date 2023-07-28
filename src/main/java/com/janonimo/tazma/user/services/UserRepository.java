package com.janonimo.tazma.user.services;

import com.janonimo.tazma.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * @author JANONIMO
 */
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = """
                    select a from User a where a.email = :email
            """)
    Optional<User> findByEmail(String email);

    @Query(value = """
                    select a from User a join Role
                    r on r.roleName = 'STYLIST' where a.address.area = :town
                     and a.address.province = :province
            """)
    List<User> findByAddress(String town, String province);

    @Query(value = """
                    select a from User a join Role
                    r on r.roleName = 'STYLIST' where a.address.area = :town
                     and a.address.province = :province and a.address.suburb = :suburb
            """)
    List<User> findByAddress(String town, String province, String suburb);

    @Query(value = """
                    select a from User a join Role
                    r on r.roleName = 'STYLIST' where
                     a.address.province = :province
            """)
    List<User> findByAddress(String province);
    @Query(value = """
                     select a from User a join Role
                    r on r.roleName = 'CLIENT'
            """)
    List<User> clients();

    @Query(value = """
                   select a from User a join Role
                   r on r.roleName = 'STYLIST'
            """)
    List<User> stylists();


}
