package com.forroworld.forro_api.controller;

import com.forroworld.forro_api.dto.TravelScheduleRequest;
import com.forroworld.forro_api.dto.TravelScheduleResponse;
import com.forroworld.forro_api.service.TravelScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/travels")
public class TravelScheduleController {

    private final TravelScheduleService travelService;

    public TravelScheduleController(TravelScheduleService travelService) {
        this.travelService = travelService;
    }

    @PostMapping
    public ResponseEntity<TravelScheduleResponse> createTravel(
            Authentication authentication,
            @RequestBody TravelScheduleRequest request) {
        String email = authentication.getName();
        return ResponseEntity.ok(travelService.createTravel(email, request));
    }

    @GetMapping("/my-travels")
    public ResponseEntity<List<TravelScheduleResponse>> getMyTravels(
            Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(travelService.getMyTravels(email));
    }

    @GetMapping("/by-city")
    public ResponseEntity<List<TravelScheduleResponse>> findByCity(
            @RequestParam String city) {
        return ResponseEntity.ok(travelService.findByCity(city));
    }

    @GetMapping("/by-city-and-date")
    public ResponseEntity<List<TravelScheduleResponse>> findByCityAndDate(
            @RequestParam String city,
            @RequestParam String date) {
        LocalDateTime dateTime = LocalDateTime.parse(date);
        return ResponseEntity.ok(travelService.findByCityAndDate(city, dateTime));
    }

    @DeleteMapping("/{travelId}")
    public ResponseEntity<Void> deleteTravel(
            Authentication authentication,
            @PathVariable UUID travelId) {
        String email = authentication.getName();
        travelService.deleteTravel(email, travelId);
        return ResponseEntity.noContent().build();
    }
}