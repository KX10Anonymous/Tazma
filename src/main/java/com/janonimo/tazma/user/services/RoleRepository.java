package com.janonimo.tazma.user.services;

import com.janonimo.tazma.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    @Override
    Optional<Role> findById(Long aLong);

    @Query(value="""
                   select r from  Role
                   r where r.roleName = :name
           """)
    Optional<Role> findByName(String name);

    @Query(value="""
                   select r from Role
                   r where r.roleName = :name and r.priority =:priority
           """)
    Optional<Role> findByPriority(String name, String priority);



    @Query(value="""
                   select r from Role
                   r where r.roleName = 'ADMIN'
           """)
    Optional<Role> findAdmin();

}
