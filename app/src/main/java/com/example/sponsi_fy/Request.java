package com.example.sponsi_fy;
public class Request {
    private String requestId;
    private String clubName;
    private String sponsorName;
    private String requestDetails;

    public Request() {
        // Default constructor for Firebase
    }

    public Request(String requestId, String clubName, String sponsorName, String requestDetails) {
        this.requestId = requestId;
        this.clubName = clubName;
        this.sponsorName = sponsorName;
        this.requestDetails = requestDetails;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getClubName() {
        return clubName;
    }

    public String getSponsorName() {
        return sponsorName;
    }

    public String getRequestDetails() {
        return requestDetails;
    }
}
