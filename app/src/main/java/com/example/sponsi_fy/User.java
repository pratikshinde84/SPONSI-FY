package com.example.sponsi_fy;

public class User {
    private String username;
    private String name;
    private String organization;
    private String email;
    private String mobileNumber;
    private String eventName; // New field for the event name

    // Default constructor
    public User() {
    }

    // Constructor with parameters
    public User(String username, String name, String organization, String email, String mobileNumber, String eventName) {
        this.username = username;
        this.name = name;
        this.organization = organization;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.eventName = eventName;
    }

    // Getter and setter methods
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
}
