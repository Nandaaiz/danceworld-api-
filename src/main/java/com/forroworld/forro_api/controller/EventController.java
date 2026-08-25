package com.forroworld.forro_api.controller;

import com.forroworld.forro_api.dto.EventRequest;
import com.forroworld.forro_api.dto.EventResponse;
import com.forroworld.forro_api.service.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    @GetMapping("/by-city")
    public ResponseEntity<List<EventResponse>> listByCity(@RequestParam String city) {
        return ResponseEntity.ok(eventService.listByCity(city));
    }

    @GetMapping("/by-country")
    public ResponseEntity<List<EventResponse>> listByCountry(@RequestParam String country) {
        return ResponseEntity.ok(eventService.listByCountry(country));
    }

    @GetMapping("/by-type")
    public ResponseEntity<List<EventResponse>> listByType(@RequestParam String eventType) {
        return ResponseEntity.ok(eventService.listByType(eventType));
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<EventResponse> getEventById(@PathVariable UUID eventId) {
        return ResponseEntity.ok(eventService.getEventById(eventId));
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> deleteEvent(
            Authentication authentication,
            @PathVariable UUID eventId) {
        String email = authentication.getName();
        eventService.deleteEvent(email, eventId);
        return ResponseEntity.noContent().build();
    }
}

