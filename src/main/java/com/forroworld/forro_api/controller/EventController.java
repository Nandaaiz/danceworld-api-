package com.forroworld.forro_api.controller;

import com.forroworld.forro_api.dto.EventRequest;
import com.forroworld.forro_api.dto.EventResponse;
import com.forroworld.forro_api.dto.PageResponse;
import com.forroworld.forro_api.service.EventService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
            @Valid @RequestBody EventRequest request) {
        String email = authentication.getName();
        return ResponseEntity.ok(eventService.createEvent(email, request));
    }

    @GetMapping
    public ResponseEntity<PageResponse<EventResponse>> listAllEvents(
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(eventService.listAllEvents(pageable));
    }

    @GetMapping("/my-events")
    public ResponseEntity<List<EventResponse>> listMyEvents(
            Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(eventService.listMyEvents(email));
    }

    @GetMapping("/by-city")
    public ResponseEntity<PageResponse<EventResponse>> listByCity(
            @RequestParam String city,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(eventService.listByCity(city, pageable));
    }

    @GetMapping("/by-country")
    public ResponseEntity<PageResponse<EventResponse>> listByCountry(
            @RequestParam String country,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(eventService.listByCountry(country, pageable));
    }

    @GetMapping("/by-type")
    public ResponseEntity<PageResponse<EventResponse>> listByType(
            @RequestParam String eventType,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(eventService.listByType(eventType, pageable));
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

