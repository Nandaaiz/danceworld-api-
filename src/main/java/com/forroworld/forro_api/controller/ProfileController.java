package com.forroworld.forro_api.controller;

import com.forroworld.forro_api.dto.ProfileRequest;
import com.forroworld.forro_api.dto.ProfileResponse;
import com.forroworld.forro_api.exception.ErrorResponse;
import com.forroworld.forro_api.exception.ValidationErrorResponse;
import com.forroworld.forro_api.service.ProfileService;
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
import java.util.List;

@Tag(name = "Profiles", description = "Teacher and artist profile management and search")
@RestController
@RequestMapping("/api/profiles")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @Operation(
            summary = "Get the current user's profile",
            description = "Returns the profile of the authenticated user, or an empty profile if none has been created yet."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Profile returned successfully",
                    content = @Content(schema = @Schema(implementation = ProfileResponse.class))),
            @ApiResponse(responseCode = "401", description = "Missing or invalid authentication token",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Authenticated user no longer exists",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/me")
    public ResponseEntity<ProfileResponse> getMyProfile(Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(profileService.getProfile(email));
    }

    @Operation(
            summary = "Update the current user's profile",
            description = "Creates or updates the profile of the authenticated user."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Profile updated successfully",
                    content = @Content(schema = @Schema(implementation = ProfileResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request body",
                    content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Missing or invalid authentication token",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Authenticated user no longer exists",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("/me")
    public ResponseEntity<ProfileResponse> updateMyProfile(
            Authentication authentication,
            @Parameter(description = "Updated profile details", required = true)
            @Valid @RequestBody ProfileRequest request) {
        String email = authentication.getName();
        return ResponseEntity.ok(profileService.updateProfile(email, request));
    }

    @Operation(
            summary = "Find teachers by city",
            description = "Returns the profiles of all teachers based in the given city (case-insensitive)."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Profiles returned successfully",
                    content = @Content(schema = @Schema(implementation = ProfileResponse.class))),
            @ApiResponse(responseCode = "400", description = "Missing required 'city' parameter",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Missing or invalid authentication token",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/teachers/by-city")
    public ResponseEntity<List<ProfileResponse>> findTeachersByCity(
            @Parameter(description = "City to search teachers in", required = true, example = "Recife")
            @RequestParam String city) {
        return ResponseEntity.ok(profileService.findTeachersByCity(city));
    }

    @Operation(
            summary = "Find artists by city",
            description = "Returns the profiles of all artists based in the given city (case-insensitive)."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Profiles returned successfully",
                    content = @Content(schema = @Schema(implementation = ProfileResponse.class))),
            @ApiResponse(responseCode = "400", description = "Missing required 'city' parameter",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Missing or invalid authentication token",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/artists/by-city")
    public ResponseEntity<List<ProfileResponse>> findArtistsByCity(
            @Parameter(description = "City to search artists in", required = true, example = "Recife")
            @RequestParam String city) {
        return ResponseEntity.ok(profileService.findArtistsByCity(city));
    }

    @Operation(
            summary = "Find teachers by country",
            description = "Returns the profiles of all teachers based in the given country (case-insensitive)."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Profiles returned successfully",
                    content = @Content(schema = @Schema(implementation = ProfileResponse.class))),
            @ApiResponse(responseCode = "400", description = "Missing required 'country' parameter",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Missing or invalid authentication token",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/teachers/by-country")
    public ResponseEntity<List<ProfileResponse>> findTeachersByCountry(
            @Parameter(description = "Country to search teachers in", required = true, example = "Brazil")
            @RequestParam String country) {
        return ResponseEntity.ok(profileService.findTeachersByCountry(country));
    }

    @Operation(
            summary = "Find artists by country",
            description = "Returns the profiles of all artists based in the given country (case-insensitive)."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Profiles returned successfully",
                    content = @Content(schema = @Schema(implementation = ProfileResponse.class))),
            @ApiResponse(responseCode = "400", description = "Missing required 'country' parameter",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Missing or invalid authentication token",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/artists/by-country")
    public ResponseEntity<List<ProfileResponse>> findArtistsByCountry(
            @Parameter(description = "Country to search artists in", required = true, example = "Brazil")
            @RequestParam String country) {
        return ResponseEntity.ok(profileService.findArtistsByCountry(country));
    }

    @Operation(
            summary = "Search profiles by display name",
            description = "Returns profiles whose display name contains the given text (case-insensitive), " +
                    "optionally filtered by user type. Public endpoint."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Profiles returned successfully",
                    content = @Content(schema = @Schema(implementation = ProfileResponse.class))),
            @ApiResponse(responseCode = "400", description = "Missing required 'name' parameter",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/search")
    public ResponseEntity<List<ProfileResponse>> searchByName(
            @Parameter(description = "Text to search for in the display name", required = true, example = "Mestre")
            @RequestParam String name,
            @Parameter(description = "Optional user type filter (e.g. TEACHER, ARTIST)")
            @RequestParam(required = false) String userType) {
        if (userType != null && !userType.isEmpty()) {
            return ResponseEntity.ok(profileService.searchByNameAndType(name, userType.toUpperCase()));
        }
        return ResponseEntity.ok(profileService.searchByName(name));
    }
}
