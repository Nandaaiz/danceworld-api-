package com.forroworld.forro_api.controller;

import com.forroworld.forro_api.dto.ProfileRequest;
import com.forroworld.forro_api.dto.ProfileResponse;
import com.forroworld.forro_api.service.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/me")
    public ResponseEntity<ProfileResponse> getMyProfile(Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(profileService.getProfile(email));
    }

    @PutMapping("/me")
    public ResponseEntity<ProfileResponse> updateMyProfile(
            Authentication authentication,
            @RequestBody ProfileRequest request) {
        String email = authentication.getName();
        return ResponseEntity.ok(profileService.updateProfile(email, request));
    }

    @GetMapping("/teachers/by-city")
    public ResponseEntity<List<ProfileResponse>> findTeachersByCity(@RequestParam String city) {
        return ResponseEntity.ok(profileService.findTeachersByCity(city));
    }

    @GetMapping("/artists/by-city")
    public ResponseEntity<List<ProfileResponse>> findArtistsByCity(@RequestParam String city) {
        return ResponseEntity.ok(profileService.findArtistsByCity(city));
    }

    @GetMapping("/teachers/by-country")
    public ResponseEntity<List<ProfileResponse>> findTeachersByCountry(@RequestParam String country) {
        return ResponseEntity.ok(profileService.findTeachersByCountry(country));
    }

    @GetMapping("/artists/by-country")
    public ResponseEntity<List<ProfileResponse>> findArtistsByCountry(@RequestParam String country) {
        return ResponseEntity.ok(profileService.findArtistsByCountry(country));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProfileResponse>> searchByName(
            @RequestParam String name,
            @RequestParam(required = false) String userType) {
        if (userType != null && !userType.isEmpty()) {
            return ResponseEntity.ok(profileService.searchByNameAndType(name, userType.toUpperCase()));
        }
        return ResponseEntity.ok(profileService.searchByName(name));
    }
}