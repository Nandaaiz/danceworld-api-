package com.forroworld.forro_api.service;

import com.forroworld.forro_api.dto.EventRequest;
import com.forroworld.forro_api.dto.EventResponse;
import com.forroworld.forro_api.model.Event;
import com.forroworld.forro_api.model.User;
import com.forroworld.forro_api.repository.EventRepository;
import com.forroworld.forro_api.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
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
                .orElseThrow(() -> new RuntimeException("User not found"));

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

    public List<EventResponse> listAllEvents() {
        return eventRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<EventResponse> listMyEvents(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return eventRepository.findByCreatedBy(user)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
    public List<EventResponse> listByCity(String city) {
        return eventRepository.findByCity(city)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<EventResponse> listByCountry(String country) {
        return eventRepository.findByCountry(country)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<EventResponse> listByType(String eventType) {
        return eventRepository.findByEventType(eventType)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
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