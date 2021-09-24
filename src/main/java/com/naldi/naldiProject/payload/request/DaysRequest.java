package com.naldi.naldiProject.payload.request;

import javax.validation.constraints.NotBlank;

public class DaysRequest {
    private Integer daysRequested;

    private Integer daysLeft;

    @NotBlank
    private String reason;

    public Integer getDaysRequested() {
        return daysRequested;
    }

    public Integer getDaysLeft() {
        return daysLeft;
    }

    public String getReason() {
        return reason;
    }

    public void setDaysRequested(Integer daysRequested) {
        this.daysRequested = daysRequested;
    }

    public void setDaysLeft(Integer daysLeft) {
        this.daysLeft = daysLeft;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
