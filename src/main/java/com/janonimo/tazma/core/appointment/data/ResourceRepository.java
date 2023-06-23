package com.janonimo.tazma.core.appointment.data;

import com.janonimo.tazma.core.appointment.Resource;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author JANONIMO
 */
public interface ResourceRepository extends JpaRepository<Resource, Integer> {
     @Query(value = """
      select r from Resource r inner join Style t\s
      on r.style.id = t.id\s
      where t.id = :id
      """)
    List<Resource> findAllResourcesByStyle(Integer id);
}
