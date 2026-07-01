package com.forroworld.forro_api.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class TravelScheduleResponse {
    private UUID id;
    private String userEmail;
    private String userDisplayName;
    private String userType;
    private String destinationCity;
    private String destinationCountry;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String notes;

    public TravelScheduleResponse(UUID id, String userEmail, String userDisplayName,
                                  String userType, String destinationCity,
                                  String destinationCountry, LocalDateTime startDate,
                                  LocalDateTime endDate, String notes) {
        this.id = id;
        this.userEmail = userEmail;
        this.userDisplayName = userDisplayName;
        this.userType = userType;
        this.destinationCity = destinationCity;
        this.destinationCountry = destinationCountry;
        this.startDate = startDate;
        this.endDate = endDate;
        this.notes = notes;
    }

    public UUID getId() { return id; }
    public String getUserEmail() { return userEmail; }
    public String getUserDisplayName() { return userDisplayName; }
    public String getUserType() { return userType; }
    public String getDestinationCity() { return destinationCity; }
    public String getDestinationCountry() { return destinationCountry; }
    public LocalDateTime getStartDate() { return startDate; }
    public LocalDateTime getEndDate() { return endDate; }
    public String getNotes() { return notes; }
}