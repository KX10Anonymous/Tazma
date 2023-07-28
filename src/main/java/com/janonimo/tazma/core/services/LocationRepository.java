package com.janonimo.tazma.core.services;

import com.janonimo.tazma.core.appointment.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {

}
