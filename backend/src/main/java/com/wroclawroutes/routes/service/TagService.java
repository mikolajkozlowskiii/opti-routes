package com.wroclawroutes.routes.service;

import com.wroclawroutes.routes.dto.TagDTO;
import com.wroclawroutes.routes.entity.Route;
import com.wroclawroutes.routes.entity.Tag;

import java.util.Set;

public interface TagService {
    void validateTags(Route route);
    Set<Tag> getEntityTags(Set<TagDTO> tagDTOS);
}
