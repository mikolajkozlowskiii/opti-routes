package com.wroclawroutes.routes.repository;

import com.wroclawroutes.routes.entity.Route;
import com.wroclawroutes.routes.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {
    Optional<Route> findById(Long id);
    @Query("SELECT r from Route r LEFT JOIN FETCH r.steps steps LEFT JOIN FETCH steps.location WHERE r.id=:id")
    Optional<Route> findByIdFetchStepsWithLocationsEagerly(@Param("id") Long id);
    @Query("SELECT r from Route r LEFT JOIN FETCH r.steps steps LEFT JOIN FETCH steps.location")
    List<Route> findAllFetchStepsWithLocationsEagerly();

    @Query("SELECT r from Route r LEFT JOIN FETCH r.steps steps LEFT JOIN FETCH steps.location WHERE r.user.email=:email")
    List<Route> findAllFetchStepsWithLocationsEagerlyByEmail(@Param("email") String email);
    @Query("SELECT r from Route r " +
            " LEFT JOIN FETCH r.steps steps" +
            " LEFT JOIN FETCH steps.location" +
            " LEFT JOIN FETCH r.tags" +
            " LEFT JOIN FETCH r.user " +
            " WHERE r.id=:id")
    Optional<Route> findByIdFetchAllRelationships(@Param("id") Long id);
    List<Route> findAll();
    @Query("SELECT r from Route r LEFT JOIN FETCH r.ratings LEFT JOIN FETCH r.tags")
    List<Route> findAllFetchTagsAndRatingsEagerly();
    List<Route> findAllByTagsContains(Tag tag);
    @Query("SELECT r from Route r " +
            "LEFT JOIN FETCH r.ratings ra" +
            "LEFT JOIN FETCH r.tags t " +
            "WHERE t in (:tag)")
    List<Route> findAllByTagContainsFetchRatingsAndTagsEagerly(@Param("tag") Tag tag);
    @Query("SELECT r from Route r " +
            "LEFT JOIN FETCH r.ratings ra" +
            "LEFT JOIN FETCH r.tags t " +
            "WHERE t in (:tags)")
    List<Route> findAllByAnyTagsContainsFetchRatingsAndTagsEagerly(@Param("tags") Set<Tag> tag);
    @Query("SELECT r FROM Route r " +
            "LEFT JOIN r.tags t " +
            "GROUP BY r " +
            "HAVING COUNT(t) = :tagCount " +
            "AND COUNT(CASE WHEN t NOT IN (:tags) THEN 1 ELSE NULL END) = 0")
    List<Route> findAllByOnlyTags(@Param("tags") Set<Tag> tags, @Param("tagCount") int tagCount);
}









