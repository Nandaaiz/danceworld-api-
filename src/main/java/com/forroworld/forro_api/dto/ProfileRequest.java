package com.forroworld.forro_api.dto;

public class ProfileRequest {
    private String displayName;
    private String bio;
    private String city;
    private String country;
    private String profilePhotoUrl;
    private String danceStyles;
    private String artistType;

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
}