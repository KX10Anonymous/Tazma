package com.janonimo.tazma.core.reporting.services;

import com.janonimo.tazma.core.reporting.Post;
import jakarta.persistence.OneToMany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query(value = """
      select p from Post p 
      where p.stylist.Id = :id 
      """)
    public List<Post> findAllByStylist(Long id);

    @Query(value = "SELECT p FROM Post p ORDER BY p.created DESC")
    public List<Post> findLatest();

    @Query(value = "SELECT p FROM Post p ORDER BY SIZE(p.reactions) DESC, p.created DESC")
    public List<Post> findByReactionsCount();
}
