package com.forroworld.forro_api.controller;

import com.forroworld.forro_api.dto.EventRequest;
import com.forroworld.forro_api.dto.EventResponse;
import com.forroworld.forro_api.service.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public ResponseEntity<EventResponse> createEvent(
            Authentication authentication,
            @RequestBody EventRequest request) {
        String email = authentication.getName();
        return ResponseEntity.ok(eventService.createEvent(email, request));
    }

    @GetMapping
    public ResponseEntity<List<EventResponse>> listAllEvents() {
        return ResponseEntity.ok(eventService.listAllEvents());
    }

    @GetMapping("/my-events")
    public ResponseEntity<List<EventResponse>> listMyEvents(
            Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(eventService.listMyEvents(email));
    }
}