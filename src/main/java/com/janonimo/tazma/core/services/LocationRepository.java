package com.janonimo.tazma.core.services;

import com.janonimo.tazma.core.appointment.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface LocationRepository extends JpaRepository<Location, Long> {

}
