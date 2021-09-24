package com.naldi.naldiProject.payload.response;

import com.naldi.naldiProject.models.EStatus;

public class RequestResponse {
    private Integer id;
    private EStatus status;
    private Integer daysRequested;
    private Integer daysRemaining;
    private String reason;
    private String response;

    public RequestResponse(Integer id, EStatus status, Integer daysRequested, Integer daysRemaining, String reason, String response) {
        this.id = id;
        this.status = status;
        this.daysRequested = daysRequested;
        this.daysRemaining = daysRemaining;
        this.reason = reason;
        this.response = response;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
