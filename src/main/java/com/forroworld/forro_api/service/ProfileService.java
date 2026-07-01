package com.forroworld.forro_api.service;

import com.forroworld.forro_api.dto.ProfileRequest;
import com.forroworld.forro_api.dto.ProfileResponse;
import com.forroworld.forro_api.model.Profile;
import com.forroworld.forro_api.model.User;
import com.forroworld.forro_api.repository.ProfileRepository;
import com.forroworld.forro_api.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    public ProfileService(ProfileRepository profileRepository, UserRepository userRepository) {
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
    }

    public ProfileResponse getProfile(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Profile profile = profileRepository.findByUser(user)
                .orElse(new Profile());

        return toResponse(profile, user);
    }

    public ProfileResponse updateProfile(String email, ProfileRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Profile profile = profileRepository.findByUser(user)
                .orElse(new Profile());

        profile.setUser(user);
        profile.setDisplayName(request.getDisplayName());
        profile.setBio(request.getBio());
        profile.setCity(request.getCity());
        profile.setCountry(request.getCountry());
        profile.setProfilePhotoUrl(request.getProfilePhotoUrl());
        profile.setDanceStyles(request.getDanceStyles());
        profile.setArtistType(request.getArtistType());
        profile.setInstagramUrl(request.getInstagramUrl());
        profile.setYoutubeUrl(request.getYoutubeUrl());
        profile.setSpotifyUrl(request.getSpotifyUrl());
        profile.setWebsiteUrl(request.getWebsiteUrl());

        profileRepository.save(profile);
        return toResponse(profile, user);
    }

    public List<ProfileResponse> findTeachersByCity(String city) {
        return profileRepository.findByCityAndUser_UserType(city, "TEACHER")
                .stream()
                .map(p -> toResponse(p, p.getUser()))
                .collect(java.util.stream.Collectors.toList());
    }

    public List<ProfileResponse> findArtistsByCity(String city) {
        return profileRepository.findByCityAndUser_UserType(city, "ARTIST")
                .stream()
                .map(p -> toResponse(p, p.getUser()))
                .collect(java.util.stream.Collectors.toList());
    }

    public List<ProfileResponse> findTeachersByCountry(String country) {
        return profileRepository.findByCountryAndUser_UserType(country, "TEACHER")
                .stream()
                .map(p -> toResponse(p, p.getUser()))
                .collect(java.util.stream.Collectors.toList());
    }

    public List<ProfileResponse> findArtistsByCountry(String country) {
        return profileRepository.findByCountryAndUser_UserType(country, "ARTIST")
                .stream()
                .map(p -> toResponse(p, p.getUser()))
                .collect(java.util.stream.Collectors.toList());
    }

    public List<ProfileResponse> searchByName(String name) {
        return profileRepository.findByDisplayNameContaining(name)
                .stream()
                .map(p -> toResponse(p, p.getUser()))
                .collect(java.util.stream.Collectors.toList());
    }

    public List<ProfileResponse> searchByNameAndType(String name, String userType) {
        return profileRepository.findByDisplayNameContainingAndUserType(name, userType)
                .stream()
                .map(p -> toResponse(p, p.getUser()))
                .collect(java.util.stream.Collectors.toList());
    }

    private ProfileResponse toResponse(Profile profile, User user) {
        return new ProfileResponse(
                profile.getDisplayName(),
                profile.getBio(),
                profile.getCity(),
                profile.getCountry(),
                profile.getProfilePhotoUrl(),
                profile.getDanceStyles(),
                profile.getArtistType(),
                user.getEmail(),
                user.getUserType().name(),
                profile.getInstagramUrl(),
                profile.getYoutubeUrl(),
                profile.getSpotifyUrl(),
                profile.getWebsiteUrl()
        );
    }
}