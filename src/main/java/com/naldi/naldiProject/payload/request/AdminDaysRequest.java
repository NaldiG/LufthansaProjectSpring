package com.naldi.naldiProject.payload.request;

import javax.validation.constraints.NotBlank;

public class AdminDaysRequest {
    private Integer requestId;

    private Integer daysRequested;

    private Integer daysLeft;

    private String status;

    @NotBlank
    private String response;

    public Integer getDaysRequested() {
        return daysRequested;
    }

    public void setDaysRequested(Integer daysRequested) {
        this.daysRequested = daysRequested;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Integer getDaysLeft() {
        return daysLeft;
    }

    public void setDaysLeft(Integer daysLeft) {
        this.daysLeft = daysLeft;
    }

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }
}
