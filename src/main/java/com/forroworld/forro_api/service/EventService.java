package com.forroworld.forro_api.service;

import com.forroworld.forro_api.dto.EventRequest;
import com.forroworld.forro_api.dto.EventResponse;
import com.forroworld.forro_api.dto.PageResponse;
import com.forroworld.forro_api.exception.ResourceNotFoundException;
import com.forroworld.forro_api.exception.UnauthorizedException;
import com.forroworld.forro_api.model.Event;
import com.forroworld.forro_api.model.User;
import com.forroworld.forro_api.repository.EventRepository;
import com.forroworld.forro_api.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public EventService(EventRepository eventRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    public EventResponse createEvent(String email, EventRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Event event = new Event();
        event.setName(request.getName());
        event.setDescription(request.getDescription());
        event.setEventType(request.getEventType());
        event.setCity(request.getCity());
        event.setCountry(request.getCountry());
        event.setEventDate(request.getEventDate());
        event.setIsFree(request.getIsFree());
        event.setPrice(request.getPrice());
        event.setCurrency(request.getCurrency());
        event.setTicketUrl(request.getTicketUrl());
        event.setCreatedBy(user);

        eventRepository.save(event);
        return toResponse(event);
    }

    public PageResponse<EventResponse> listAllEvents(Pageable pageable) {
        Page<EventResponse> events = eventRepository.findAll(pageable).map(this::toResponse);
        return PageResponse.from(events);
    }

    public List<EventResponse> listMyEvents(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return eventRepository.findByCreatedBy(user)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public PageResponse<EventResponse> listByCity(String city, Pageable pageable) {
        Page<EventResponse> events = eventRepository.findByCity(city, pageable).map(this::toResponse);
        return PageResponse.from(events);
    }

    public PageResponse<EventResponse> listByCountry(String country, Pageable pageable) {
        Page<EventResponse> events = eventRepository.findByCountry(country, pageable).map(this::toResponse);
        return PageResponse.from(events);
    }

    public PageResponse<EventResponse> listByType(String eventType, Pageable pageable) {
        Page<EventResponse> events = eventRepository.findByEventType(eventType, pageable).map(this::toResponse);
        return PageResponse.from(events);
    }

    public EventResponse getEventById(UUID id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));
        return toResponse(event);
    }

    public void deleteEvent(String email, UUID id) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));
        if (!event.getCreatedBy().getEmail().equals(email)) {
            throw new UnauthorizedException("You can only delete your own events");
        }
        eventRepository.delete(event);
    }

    private EventResponse toResponse(Event event) {
        return new EventResponse(
                event.getId(),
                event.getName(),
                event.getDescription(),
                event.getEventType(),
                event.getCity(),
                event.getCountry(),
                event.getEventDate(),
                event.getIsFree(),
                event.getPrice(),
                event.getCurrency(),
                event.getTicketUrl(),
                event.getCreatedBy().getEmail()
        );
    }
}