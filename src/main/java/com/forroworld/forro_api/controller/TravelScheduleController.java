package com.forroworld.forro_api.controller;

import com.forroworld.forro_api.dto.TravelScheduleRequest;
import com.forroworld.forro_api.dto.TravelScheduleResponse;
import com.forroworld.forro_api.exception.ErrorResponse;
import com.forroworld.forro_api.exception.ValidationErrorResponse;
import com.forroworld.forro_api.service.TravelScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Tag(name = "Travel Schedule", description = "Teacher and artist travel plans")
@RestController
@RequestMapping("/api/travels")
public class TravelScheduleController {

    private final TravelScheduleService travelService;

    public TravelScheduleController(TravelScheduleService travelService) {
        this.travelService = travelService;
    }

    @Operation(
            summary = "Create a travel entry",
            description = "Adds a new travel plan for the authenticated user."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Travel entry created successfully",
                    content = @Content(schema = @Schema(implementation = TravelScheduleResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request body",
                    content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Missing or invalid authentication token",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Authenticated user no longer exists",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping
    public ResponseEntity<TravelScheduleResponse> createTravel(
            Authentication authentication,
            @Parameter(description = "Details of the travel plan to create", required = true)
            @Valid @RequestBody TravelScheduleRequest request) {
        String email = authentication.getName();
        return ResponseEntity.ok(travelService.createTravel(email, request));
    }

    @Operation(
            summary = "List the current user's travel plans",
            description = "Returns every travel entry created by the authenticated user."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Travel entries returned successfully",
                    content = @Content(schema = @Schema(implementation = TravelScheduleResponse.class))),
            @ApiResponse(responseCode = "401", description = "Missing or invalid authentication token",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Authenticated user no longer exists",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/my-travels")
    public ResponseEntity<List<TravelScheduleResponse>> getMyTravels(
            Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(travelService.getMyTravels(email));
    }

    @Operation(
            summary = "Find travel plans by destination city",
            description = "Returns travel entries whose destination city matches the given value (case-insensitive). Public endpoint."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Travel entries returned successfully",
                    content = @Content(schema = @Schema(implementation = TravelScheduleResponse.class))),
            @ApiResponse(responseCode = "400", description = "Missing required 'city' parameter",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/by-city")
    public ResponseEntity<List<TravelScheduleResponse>> findByCity(
            @Parameter(description = "Destination city to search for", required = true, example = "Recife")
            @RequestParam String city) {
        return ResponseEntity.ok(travelService.findByCity(city));
    }

    @Operation(
            summary = "Find travel plans by destination city and date",
            description = "Returns travel entries with the given destination city whose date range includes the given date. Public endpoint."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Travel entries returned successfully",
                    content = @Content(schema = @Schema(implementation = TravelScheduleResponse.class))),
            @ApiResponse(responseCode = "400", description = "Missing required parameter, or 'date' is not a valid ISO-8601 date-time",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/by-city-and-date")
    public ResponseEntity<List<TravelScheduleResponse>> findByCityAndDate(
            @Parameter(description = "Destination city to search for", required = true, example = "Recife")
            @RequestParam String city,
            @Parameter(description = "ISO-8601 date-time to match against the travel's date range",
                    required = true, example = "2026-09-01T00:00:00")
            @RequestParam String date) {
        LocalDateTime dateTime = LocalDateTime.parse(date);
        return ResponseEntity.ok(travelService.findByCityAndDate(city, dateTime));
    }

    @Operation(
            summary = "Delete a travel entry",
            description = "Deletes a travel entry. Only the user who created it may delete it."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Travel entry deleted successfully", content = @Content),
            @ApiResponse(responseCode = "400", description = "'travelId' is not a valid UUID",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Missing or invalid authentication token",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "The travel entry belongs to another user",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Travel entry not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping("/{travelId}")
    public ResponseEntity<Void> deleteTravel(
            Authentication authentication,
            @Parameter(description = "Id of the travel entry to delete", required = true)
            @PathVariable UUID travelId) {
        String email = authentication.getName();
        travelService.deleteTravel(email, travelId);
        return ResponseEntity.noContent().build();
    }
}
