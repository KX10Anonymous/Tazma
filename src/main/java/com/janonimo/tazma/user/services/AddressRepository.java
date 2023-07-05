package com.janonimo.tazma.user.services;

import com.janonimo.tazma.user.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {
    @Query(value = """
      select a from Address a inner join User u\s
      on a.user.Id = u.Id\s
      where a.user.Id = :id
      """)
    public Optional<Address> getUserAddress(Long id);
}
