package com.forroworld.forro_api.controller;

import com.forroworld.forro_api.exception.ErrorResponse;
import com.forroworld.forro_api.service.EventAttendanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Tag(name = "Event Attendance", description = "Marking and browsing attendance for events")
@RestController
@RequestMapping("/api/events")
public class EventAttendanceController {

    private final EventAttendanceService attendanceService;

    public EventAttendanceController(EventAttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @Operation(
            summary = "Mark attendance for an event",
            description = "Sets the authenticated user's attendance status (e.g. GOING, INTERESTED, WENT) for an event."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Attendance status updated successfully",
                    content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "400", description = "'eventId' is not a valid UUID, or 'status' is missing",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Missing or invalid authentication token",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "User or event not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/{eventId}/attendance")
    public ResponseEntity<Map<String, Object>> markAttendance(
            Authentication authentication,
            @Parameter(description = "Id of the event", required = true)
            @PathVariable UUID eventId,
            @Parameter(description = "Attendance status", required = true, example = "GOING")
            @RequestParam String status) {
        String email = authentication.getName();
        return ResponseEntity.ok(attendanceService.markAttendance(email, eventId, status));
    }

    @Operation(
            summary = "List an event's attendees",
            description = "Returns the email and attendance status of every user who has marked attendance for the event."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Attendees returned successfully",
                    content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "400", description = "'eventId' is not a valid UUID",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Missing or invalid authentication token",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Event not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/{eventId}/attendees")
    public ResponseEntity<List<Map<String, Object>>> getAttendees(
            @Parameter(description = "Id of the event", required = true)
            @PathVariable UUID eventId) {
        return ResponseEntity.ok(attendanceService.getEventAttendees(eventId));
    }

    @Operation(
            summary = "List the current user's attendances",
            description = "Returns every event the authenticated user has marked attendance for, with its status."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Attendances returned successfully",
                    content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "401", description = "Missing or invalid authentication token",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Authenticated user no longer exists",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/my-attendances")
    public ResponseEntity<List<Map<String, Object>>> getMyAttendances(
            Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(attendanceService.getMyAttendances(email));
    }
}
