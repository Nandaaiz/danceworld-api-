package com.forroworld.forro_api.repository;

import com.forroworld.forro_api.model.Event;
import com.forroworld.forro_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID> {
    List<Event> findByCreatedBy(User user);

    @Query("SELECT e FROM Event e WHERE LOWER(e.city) = LOWER(:city)")
    List<Event> findByCity(@Param("city") String city);

    @Query("SELECT e FROM Event e WHERE LOWER(e.country) = LOWER(:country)")
    List<Event> findByCountry(@Param("country") String country);

    @Query("SELECT e FROM Event e WHERE LOWER(e.eventType) = LOWER(:eventType)")
    List<Event> findByEventType(@Param("eventType") String eventType);
}