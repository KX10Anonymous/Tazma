package com.janonimo.tazma.core.services;

import com.janonimo.tazma.core.appointment.Resource;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.multipart.MultipartFile;

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
    
    Resource save(MultipartFile file);
    
    
}
