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
    private final ResourceService resourceService;
    public Style create(Style style){
        Style temp = styleRepository.saveAndFlush(style);
        List<Resource> resources = style.getResources();
        for(Resource r : resources){
            r.setStyle(style);
        }
        return null;
    }
    
}
