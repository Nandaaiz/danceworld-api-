package com.forroworld.forro_api.repository;

import com.forroworld.forro_api.model.Event;
import com.forroworld.forro_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID> {
    List<Event> findByCreatedBy(User user);
    List<Event> findByCity(String city);
    List<Event> findByCountry(String country);
    List<Event> findByEventType(String eventType);
}