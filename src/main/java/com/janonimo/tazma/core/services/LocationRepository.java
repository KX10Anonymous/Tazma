package com.janonimo.tazma.core.services;

import com.janonimo.tazma.core.appointment.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface LocationRepository extends JpaRepository<Location, Long> {

//    @Query("select l from Location l join l.appointment a " +
//           "join a.stylist u " +
//           "where l.locationName = :locationName and a.status = 'ACTIVE'")
//    Set<Location> findByName(@Param("locationName") String locationName);
//
//    @Query("select l from Location l join l.appointment a " +
//           "join a.stylist u " +
//           "where l.locationName = :locationName")
//    Set<Location> findAllByName(@Param("locationName") String locationName);
}
