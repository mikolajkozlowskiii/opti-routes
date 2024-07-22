package com.wroclawroutes.routes.service.implementation;

import com.wroclawroutes.routes.dto.TagDTO;
import com.wroclawroutes.routes.entity.Route;
import com.wroclawroutes.routes.entity.Tag;
import com.wroclawroutes.routes.repository.TagRepository;
import com.wroclawroutes.routes.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    @Override
    public void validateTags(Route route) {
        final List<Tag> tags = route.getTags()
                .stream()
                .map(s->tagRepository.findByName(s.getName()).orElseThrow(IllegalArgumentException::new))
                .toList();

    }

    @Override
    public Set<Tag> getEntityTags(Set<TagDTO> tagDTOS) {
        return tagDTOS
                .stream()
                .map(s->tagRepository.findByName(s.getName()).orElseThrow(IllegalArgumentException::new))
                .collect(Collectors.toSet());
    }
}
