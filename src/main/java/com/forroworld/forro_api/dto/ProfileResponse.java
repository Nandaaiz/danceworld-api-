package com.forroworld.forro_api.dto;

public class ProfileResponse {
    private String displayName;
    private String bio;
    private String city;
    private String country;
    private String profilePhotoUrl;
    private String danceStyles;
    private String artistType;
    private String email;
    private String userType;

    public ProfileResponse(String displayName, String bio, String city,
                           String country, String profilePhotoUrl,
                           String danceStyles, String artistType,
                           String email, String userType) {
        this.displayName = displayName;
        this.bio = bio;
        this.city = city;
        this.country = country;
        this.profilePhotoUrl = profilePhotoUrl;
        this.danceStyles = danceStyles;
        this.artistType = artistType;
        this.email = email;
        this.userType = userType;
    }

    public String getDisplayName() { return displayName; }
    public String getBio() { return bio; }
    public String getCity() { return city; }
    public String getCountry() { return country; }
    public String getProfilePhotoUrl() { return profilePhotoUrl; }
    public String getDanceStyles() { return danceStyles; }
    public String getArtistType() { return artistType; }
    public String getEmail() { return email; }
    public String getUserType() { return userType; }
}