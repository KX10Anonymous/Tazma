package com.janonimo.tazma.core.services;

import com.janonimo.tazma.core.appointment.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 *
 * @author JANONIMO
 */
public interface ResourceRepository extends JpaRepository<Resource, Long> {
     @Query(value = """
      select r from Resource r inner join Style t
      on r.style.Id = t.Id
      where t.Id = :id
      """)
    List<Resource> findAllResourcesByStyle(Long id);

    @Query(value = """
      select r from Resource r
      where r.style.Id = :id
      """)
    Resource findResourceByStyle(Long id);
}
