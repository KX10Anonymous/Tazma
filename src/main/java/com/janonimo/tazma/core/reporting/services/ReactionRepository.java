package com.janonimo.tazma.core.reporting.services;

import com.janonimo.tazma.core.reporting.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReactionRepository extends JpaRepository<Reaction, Long> {
    @Query(value = """
      select r from Reaction r
      where r.post.Id = :id
      """)
    List<Reaction> findByPost(Long id);
}
