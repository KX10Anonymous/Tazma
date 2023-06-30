package com.janonimo.tazma.core.services;

import com.janonimo.tazma.core.appointment.Resource;
import com.janonimo.tazma.core.appointment.Style;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    
    /**
     * 
     * @param id
     * @param file
     * @return 
     */
    public Resource uploadResource(Integer id, MultipartFile file){
        if(styleRepository.existsById(id)){
            Style style =  styleRepository.findById(id).get();
            return srcService.save(file, style);
        }else{
            return null;
        }
    }
}
