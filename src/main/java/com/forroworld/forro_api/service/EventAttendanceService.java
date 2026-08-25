package com.forroworld.forro_api.service;

import com.forroworld.forro_api.exception.ResourceNotFoundException;
import com.forroworld.forro_api.model.Event;
import com.forroworld.forro_api.model.EventAttendance;
import com.forroworld.forro_api.model.User;
import com.forroworld.forro_api.repository.EventAttendanceRepository;
import com.forroworld.forro_api.repository.EventRepository;
import com.forroworld.forro_api.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EventAttendanceService {

    private final EventAttendanceRepository attendanceRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public EventAttendanceService(EventAttendanceRepository attendanceRepository,
                                  EventRepository eventRepository,
                                  UserRepository userRepository) {
        this.attendanceRepository = attendanceRepository;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    public Map<String, Object> markAttendance(String email, UUID eventId, String status) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));

        EventAttendance attendance = attendanceRepository
                .findByUserAndEvent(user, event)
                .orElse(new EventAttendance());

        // Se o evento já passou e o status é GOING, permite mudar para WENT
        String finalStatus = status;
        if (event.getEventDate() != null &&
                event.getEventDate().isBefore(LocalDateTime.now()) &&
                "WENT".equals(status)) {
            finalStatus = "WENT";
        }

        attendance.setUser(user);
        attendance.setEvent(event);
        attendance.setStatus(finalStatus);
        attendanceRepository.save(attendance);

        return Map.of(
                "eventId", eventId,
                "status", finalStatus,
                "eventName", event.getName()
        );
    }

    public List<Map<String, Object>> getEventAttendees(UUID eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));

        boolean eventPassed = event.getEventDate() != null &&
                event.getEventDate().isBefore(LocalDateTime.now());

        return attendanceRepository.findByEvent(event)
                .stream()
                .map(a -> {
                    String displayStatus = a.getStatus();
                    if (eventPassed && "GOING".equals(a.getStatus())) {
                        displayStatus = "WENT";
                    }
                    return Map.<String, Object>of(
                            "email", a.getUser().getEmail(),
                            "status", displayStatus
                    );
                })
                .collect(Collectors.toList());
    }

    public List<Map<String, Object>> getMyAttendances(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return attendanceRepository.findByUser(user)
                .stream()
                .map(a -> {
                    boolean eventPassed = a.getEvent().getEventDate() != null &&
                            a.getEvent().getEventDate().isBefore(LocalDateTime.now());
                    String displayStatus = a.getStatus();
                    if (eventPassed && "GOING".equals(a.getStatus())) {
                        displayStatus = "WENT";
                    }
                    return Map.<String, Object>of(
                            "eventId", a.getEvent().getId(),
                            "eventName", a.getEvent().getName(),
                            "status", displayStatus
                    );
                })
                .collect(Collectors.toList());
    }
}