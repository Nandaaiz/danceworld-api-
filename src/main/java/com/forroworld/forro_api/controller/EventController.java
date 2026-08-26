package com.forroworld.forro_api.controller;

import com.forroworld.forro_api.dto.EventRequest;
import com.forroworld.forro_api.dto.EventResponse;
import com.forroworld.forro_api.dto.PageResponse;
import com.forroworld.forro_api.exception.ErrorResponse;
import com.forroworld.forro_api.exception.ValidationErrorResponse;
import com.forroworld.forro_api.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Events", description = "Browsing, creating and managing forró events")
@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @Operation(
            summary = "Create an event",
            description = "Creates a new event owned by the authenticated user."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Event created successfully",
                    content = @Content(schema = @Schema(implementation = EventResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request body",
                    content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Missing or invalid authentication token",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Authenticated user no longer exists",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping
    public ResponseEntity<EventResponse> createEvent(
            Authentication authentication,
            @Parameter(description = "Details of the event to create", required = true)
            @Valid @RequestBody EventRequest request) {
        String email = authentication.getName();
        return ResponseEntity.ok(eventService.createEvent(email, request));
    }

    @Operation(
            summary = "List all events",
            description = "Returns a paginated list of every event, in no particular order. Public endpoint."
    )
    @ApiResponse(responseCode = "200", description = "Page of events returned successfully",
            content = @Content(schema = @Schema(implementation = PageResponse.class)))
    @GetMapping
    public ResponseEntity<PageResponse<EventResponse>> listAllEvents(
            @Parameter(description = "Pagination and sorting options (default page size 20)")
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(eventService.listAllEvents(pageable));
    }

    @Operation(
            summary = "List events created by the current user",
            description = "Returns every event created by the authenticated user."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Events returned successfully",
                    content = @Content(schema = @Schema(implementation = EventResponse.class))),
            @ApiResponse(responseCode = "401", description = "Missing or invalid authentication token",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Authenticated user no longer exists",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/my-events")
    public ResponseEntity<List<EventResponse>> listMyEvents(
            Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(eventService.listMyEvents(email));
    }

    @Operation(
            summary = "List events by city",
            description = "Returns a paginated list of events taking place in the given city (case-insensitive). Public endpoint."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page of events returned successfully",
                    content = @Content(schema = @Schema(implementation = PageResponse.class))),
            @ApiResponse(responseCode = "400", description = "Missing required 'city' parameter",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/by-city")
    public ResponseEntity<PageResponse<EventResponse>> listByCity(
            @Parameter(description = "City to filter events by", required = true, example = "Recife")
            @RequestParam String city,
            @Parameter(description = "Pagination and sorting options (default page size 20)")
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(eventService.listByCity(city, pageable));
    }

    @Operation(
            summary = "List events by country",
            description = "Returns a paginated list of events taking place in the given country (case-insensitive). Public endpoint."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page of events returned successfully",
                    content = @Content(schema = @Schema(implementation = PageResponse.class))),
            @ApiResponse(responseCode = "400", description = "Missing required 'country' parameter",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/by-country")
    public ResponseEntity<PageResponse<EventResponse>> listByCountry(
            @Parameter(description = "Country to filter events by", required = true, example = "Brazil")
            @RequestParam String country,
            @Parameter(description = "Pagination and sorting options (default page size 20)")
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(eventService.listByCountry(country, pageable));
    }

    @Operation(
            summary = "List events by type",
            description = "Returns a paginated list of events of the given type (e.g. FESTIVAL, PARTY, SHOW). Public endpoint."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page of events returned successfully",
                    content = @Content(schema = @Schema(implementation = PageResponse.class))),
            @ApiResponse(responseCode = "400", description = "Missing required 'eventType' parameter",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/by-type")
    public ResponseEntity<PageResponse<EventResponse>> listByType(
            @Parameter(description = "Event type to filter by", required = true, example = "FESTIVAL")
            @RequestParam String eventType,
            @Parameter(description = "Pagination and sorting options (default page size 20)")
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(eventService.listByType(eventType, pageable));
    }

    @Operation(
            summary = "Get an event by id",
            description = "Returns the details of a single event."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Event returned successfully",
                    content = @Content(schema = @Schema(implementation = EventResponse.class))),
            @ApiResponse(responseCode = "400", description = "'eventId' is not a valid UUID",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Missing or invalid authentication token",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Event not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/{eventId}")
    public ResponseEntity<EventResponse> getEventById(
            @Parameter(description = "Id of the event to retrieve", required = true)
            @PathVariable UUID eventId) {
        return ResponseEntity.ok(eventService.getEventById(eventId));
    }

    @Operation(
            summary = "Delete an event",
            description = "Deletes an event. Only the user who created the event may delete it."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Event deleted successfully", content = @Content),
            @ApiResponse(responseCode = "400", description = "'eventId' is not a valid UUID",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Missing or invalid authentication token",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "The event belongs to another user",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Event not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> deleteEvent(
            Authentication authentication,
            @Parameter(description = "Id of the event to delete", required = true)
            @PathVariable UUID eventId) {
        String email = authentication.getName();
        eventService.deleteEvent(email, eventId);
        return ResponseEntity.noContent().build();
    }
}
