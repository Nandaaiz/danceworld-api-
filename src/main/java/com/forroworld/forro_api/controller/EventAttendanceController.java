package com.forroworld.forro_api.controller;

import com.forroworld.forro_api.service.EventAttendanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/events")
public class EventAttendanceController {

    private final EventAttendanceService attendanceService;

    public EventAttendanceController(EventAttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping("/{eventId}/attendance")
    public ResponseEntity<Map<String, Object>> markAttendance(
            Authentication authentication,
            @PathVariable UUID eventId,
            @RequestParam String status) {
        String email = authentication.getName();
        return ResponseEntity.ok(attendanceService.markAttendance(email, eventId, status));
    }

    @GetMapping("/{eventId}/attendees")
    public ResponseEntity<List<Map<String, Object>>> getAttendees(
            @PathVariable UUID eventId) {
        return ResponseEntity.ok(attendanceService.getEventAttendees(eventId));
    }

    @GetMapping("/my-attendances")
    public ResponseEntity<List<Map<String, Object>>> getMyAttendances(
            Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(attendanceService.getMyAttendances(email));
    }
}