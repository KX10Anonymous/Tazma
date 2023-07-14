package com.janonimo.tazma.core.services;

import com.janonimo.tazma.core.appointment.Resource;
import com.janonimo.tazma.core.appointment.Style;

import java.util.ArrayList;
import java.util.List;

import com.janonimo.tazma.core.rest.response.StyleResponse;
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

    public Style read(Long id){
        Style style = styleRepository.getReferenceById(id);
       // List<Resource> resources = srcService.resourcesByStyle(id);
      //  style.setResources(resources);
        return style;
    }

    public ArrayList<StyleResponse> all(){
        List<Style> styles = styleRepository.findAll();
        ArrayList<StyleResponse> response = new ArrayList<>();
        for(Style style : styles){
            Style temp = read(style.getId());
            StyleResponse res = new StyleResponse();
            res.setId(temp.getId());
            res.setTitle(temp.getStyleName());
            res.setUrl(temp.getResources().get(0).getPath());
            response.add(res);
        }
        return response;
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
    public Resource uploadResource(Long id, MultipartFile file){
        Style style =  styleRepository.findById(id).get();
        return srcService.save(file, style);

    }
}
