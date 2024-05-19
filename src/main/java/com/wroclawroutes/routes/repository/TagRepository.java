package com.wroclawroutes.routes.repository;

import com.wroclawroutes.routes.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    @Query("SELECT COUNT(r) FROM Route r JOIN r.tags t WHERE t.name = :tagName")
    Long countRoutesByTagName(@Param("tagName") String tagName);
    Boolean existsByName(String name);
    Optional<Tag> findByName(String name);
}
