package com.example.sponsi_fy;
public class Application {
    private String eventName, eventType, eventLocation, eventDate;

    public Application() {
        // Default constructor required for calls to DataSnapshot.getValue(Application.class)
    }

    public Application(String eventName, String eventType, String eventLocation, String eventDate) {
        this.eventName = eventName;
        this.eventType = eventType;
        this.eventLocation = eventLocation;
        this.eventDate = eventDate;
    }

    public String getEventName() {
        return eventName;
    }

    public String getEventType() {
        return eventType;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public String getEventDate() {
        return eventDate;
    }
}
