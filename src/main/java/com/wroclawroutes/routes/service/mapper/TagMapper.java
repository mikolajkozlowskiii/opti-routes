package com.wroclawroutes.routes.service.mapper;

import com.wroclawroutes.routes.dto.TagDTO;
import com.wroclawroutes.routes.entity.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TagMapper {
    public TagDTO map(Tag tag){
        return TagDTO
                .builder()
                .name(tag.getName())
                .build();
    }
}
