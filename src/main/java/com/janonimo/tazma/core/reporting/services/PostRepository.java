package com.janonimo.tazma.core.reporting.services;

import com.janonimo.tazma.core.reporting.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query(value = """
            select p from Post p
            where p.stylist.Id = :id
            """)
    List<Post> findAllByStylist(Long id);

    @Query(value = "SELECT p FROM Post p ORDER BY p.created DESC")
    List<Post> findLatest();

    @Query(value = "SELECT p FROM Post p ORDER BY SIZE(p.reactions) DESC, p.created DESC")
    List<Post> findByReactionsCount();
}
