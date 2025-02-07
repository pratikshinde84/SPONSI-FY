package com.example.sponsi_fy;
public class Request {
    private String email;
    private String eventName;
    private String mobileNumber;
    private String name;
    private String organization;
    private String username;

    public Request() {
    }

    public Request(String email, String eventName, String mobileNumber, String name, String organization, String username) {
        this.email = email;
        this.eventName = eventName;
        this.mobileNumber = mobileNumber;
        this.name = name;
        this.organization = organization;
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
