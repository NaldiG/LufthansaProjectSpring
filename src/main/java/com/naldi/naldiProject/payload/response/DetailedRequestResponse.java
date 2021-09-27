package com.naldi.naldiProject.payload.response;

import com.naldi.naldiProject.models.EStatus;

public class DetailedRequestResponse {
    private Long userID;
    private String username;
    private String email;
    private Integer requestId;
    private EStatus status;
    private Integer daysRequested;
    private Integer daysRemaining;
    private String reason;
    private String response;

    public DetailedRequestResponse(Long userID, String username, String email, Integer requestId, EStatus status, Integer daysRequested, Integer daysRemaining, String reason, String response) {
        this.userID = userID;
        this.username = username;
        this.email = email;
        this.requestId = requestId;
        this.status = status;
        this.daysRequested = daysRequested;
        this.daysRemaining = daysRemaining;
        this.reason = reason;
        this.response = response;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    public EStatus getStatus() {
        return status;
    }

    public void setStatus(EStatus status) {
        this.status = status;
    }

    public Integer getDaysRequested() {
        return daysRequested;
    }

    public void setDaysRequested(Integer daysRequested) {
        this.daysRequested = daysRequested;
    }

    public Integer getDaysRemaining() {
        return daysRemaining;
    }

    public void setDaysRemaining(Integer daysRemaining) {
        this.daysRemaining = daysRemaining;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
