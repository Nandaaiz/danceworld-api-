package com.forroworld.forro_api.repository;

import com.forroworld.forro_api.model.Event;
import com.forroworld.forro_api.model.EventAttendance;
import com.forroworld.forro_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EventAttendanceRepository extends JpaRepository<EventAttendance, UUID> {
    Optional<EventAttendance> findByUserAndEvent(User user, Event event);
    List<EventAttendance> findByEvent(Event event);
    List<EventAttendance> findByUser(User user);
}