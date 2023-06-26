package com.janonimo.tazma.core.appointment.data;

import com.janonimo.tazma.core.appointment.Resource;
import com.janonimo.tazma.core.appointment.Style;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 *
 * @author JANONIMO
 */
@Service
@RequiredArgsConstructor
public class StyleService {
    private final StyleRepository styleRepository;
    private final ResourceService srcService;
    public Style create(Style style){
        Style temp = styleRepository.saveAndFlush(style);
        List<Resource> resources = style.getResources();
        if(!resources.isEmpty()){
            for(Resource r : resources){
                r.setStyle(temp);
                srcService.create(r);
            }
        }
        return temp;
    }
    
    public Style edit(Style style){
        return create(style);
    }
    
    public Style read(Integer id){
        Style style = styleRepository.findById(id).get();
        List<Resource> resources = srcService.resourcesByStyle(id);
        style.setResources(resources);
        return style;
    }
    
    public List<Style> all(){
        return styleRepository.findAll();
    }
    
    public boolean delete(Style style){
        styleRepository.delete(style);
        return true;
    }
}
