package com.forroworld.forro_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ProfileRequest {

    @NotBlank(message = "Display name is required")
    @Size(max = 100, message = "Display name must be at most 100 characters")
    private String displayName;

    @Size(max = 1000, message = "Bio must be at most 1000 characters")
    private String bio;

    @Size(max = 100, message = "City must be at most 100 characters")
    private String city;

    @Size(max = 100, message = "Country must be at most 100 characters")
    private String country;

    @Size(max = 500, message = "Profile photo URL must be at most 500 characters")
    private String profilePhotoUrl;

    @Size(max = 255, message = "Dance styles must be at most 255 characters")
    private String danceStyles;

    @Size(max = 100, message = "Artist type must be at most 100 characters")
    private String artistType;

    @Size(max = 500, message = "Instagram URL must be at most 500 characters")
    private String instagramUrl;

    @Size(max = 500, message = "YouTube URL must be at most 500 characters")
    private String youtubeUrl;

    @Size(max = 500, message = "Spotify URL must be at most 500 characters")
    private String spotifyUrl;

    @Size(max = 500, message = "Website URL must be at most 500 characters")
    private String websiteUrl;

    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }
    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    public String getProfilePhotoUrl() { return profilePhotoUrl; }
    public void setProfilePhotoUrl(String profilePhotoUrl) { this.profilePhotoUrl = profilePhotoUrl; }
    public String getDanceStyles() { return danceStyles; }
    public void setDanceStyles(String danceStyles) { this.danceStyles = danceStyles; }
    public String getArtistType() { return artistType; }
    public void setArtistType(String artistType) { this.artistType = artistType; }
    public String getInstagramUrl() { return instagramUrl; }
    public void setInstagramUrl(String instagramUrl) { this.instagramUrl = instagramUrl; }
    public String getYoutubeUrl() { return youtubeUrl; }
    public void setYoutubeUrl(String youtubeUrl) { this.youtubeUrl = youtubeUrl; }
    public String getSpotifyUrl() { return spotifyUrl; }
    public void setSpotifyUrl(String spotifyUrl) { this.spotifyUrl = spotifyUrl; }
    public String getWebsiteUrl() { return websiteUrl; }
    public void setWebsiteUrl(String websiteUrl) { this.websiteUrl = websiteUrl; }
}
