package com.forroworld.forro_api.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class EventResponse {
    private UUID id;
    private String name;
    private String description;
    private String eventType;
    private String city;
    private String country;
    private LocalDateTime eventDate;
    private Boolean isFree;
    private BigDecimal price;
    private String currency;
    private String ticketUrl;
    private String createdByEmail;

    public EventResponse(UUID id, String name, String description, String eventType,
                         String city, String country, LocalDateTime eventDate,
                         Boolean isFree, BigDecimal price, String currency,
                         String ticketUrl, String createdByEmail) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.eventType = eventType;
        this.city = city;
        this.country = country;
        this.eventDate = eventDate;
        this.isFree = isFree;
        this.price = price;
        this.currency = currency;
        this.ticketUrl = ticketUrl;
        this.createdByEmail = createdByEmail;
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getEventType() { return eventType; }
    public String getCity() { return city; }
    public String getCountry() { return country; }
    public LocalDateTime getEventDate() { return eventDate; }
    public Boolean getIsFree() { return isFree; }
    public BigDecimal getPrice() { return price; }
    public String getCurrency() { return currency; }
    public String getTicketUrl() { return ticketUrl; }
    public String getCreatedByEmail() { return createdByEmail; }
}