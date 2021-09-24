package com.naldi.naldiProject.payload.response;

import com.naldi.naldiProject.models.EStatus;

public class DaysResponse {
    private Integer id;
    private Integer days;
    private String reason;
    private EStatus status;
    private String response;
    private String message;

    public DaysResponse() {
    }

    public DaysResponse(Integer id, Integer days, String reason, EStatus status, String response, String message) {
        this.id = id;
        this.days = days;
        this.reason = reason;
        this.status = status;
        this.response = response;
        this.message = message;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public EStatus getStatus() {
        return status;
    }

    public void setStatus(EStatus status) {
        this.status = status;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
