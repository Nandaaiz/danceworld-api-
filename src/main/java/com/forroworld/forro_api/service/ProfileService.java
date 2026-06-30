package com.forroworld.forro_api.service;

import com.forroworld.forro_api.dto.ProfileRequest;
import com.forroworld.forro_api.dto.ProfileResponse;
import com.forroworld.forro_api.model.Profile;
import com.forroworld.forro_api.model.User;
import com.forroworld.forro_api.repository.ProfileRepository;
import com.forroworld.forro_api.repository.UserRepository;
import org.springframework.stereotype.Service;

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

        return new ProfileResponse(
                profile.getDisplayName(),
                profile.getBio(),
                profile.getCity(),
                profile.getCountry(),
                profile.getProfilePhotoUrl(),
                profile.getDanceStyles(),
                profile.getArtistType(),
                user.getEmail(),
                user.getUserType().name()
        );
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

        profileRepository.save(profile);

        return new ProfileResponse(
                profile.getDisplayName(),
                profile.getBio(),
                profile.getCity(),
                profile.getCountry(),
                profile.getProfilePhotoUrl(),
                profile.getDanceStyles(),
                profile.getArtistType(),
                user.getEmail(),
                user.getUserType().name()
        );
    }
}