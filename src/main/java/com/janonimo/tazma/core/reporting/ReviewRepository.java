package com.janonimo.tazma.core.reporting;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("select r from Review r join r.appointment a " +
            "join a.stylist s " +
            "where s.Id = :id")
    List<Review> findAllOnStylist(Long id);

    @Query("select r from Review r join r.appointment a " +
            "join a.client s " +
            "where s.Id = :id")
    List<Review> findAllByClient(Long id);
}
