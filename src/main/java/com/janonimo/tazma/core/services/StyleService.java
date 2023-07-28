package com.janonimo.tazma.core.services;

import com.janonimo.tazma.core.appointment.Resource;
import com.janonimo.tazma.core.appointment.Style;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        return styleRepository.saveAndFlush(style);
    }

    public Style edit(Style style){
        return create(style);
    }

    public Style read(Long id){
        return styleRepository.getReferenceById(id);
    }

    public ArrayList<StyleResponse> all(){
        ArrayList<StyleResponse> response = new ArrayList<>();
        List<Style> styles = styleRepository.findAll();
        if(styles != null){
            for(Style style : styles){
                StyleResponse res = new StyleResponse();
                res.setId(style.getId());
                res.setTitle(style.getStyleName());
                res.setUrl(srcService.findByStyle(style.getId()).getPath());
                response.add(res);
            }
        }

        return response;
    }

    public boolean delete(Style style){
        styleRepository.delete(style);
        return !styleRepository.existsById(style.getId());
    }


    public Resource uploadResource(Long id, MultipartFile file){
        Style style =  Objects.requireNonNull(styleRepository.findById(id)).orElse(null);
        return srcService.save(file, style);
    }
}
