package com.naldi.naldiProject.payload.response;

import java.util.List;

public class AllDetailedRequestResponse {
    private List<DetailedRequestResponse> requests;
    private String message;

    public AllDetailedRequestResponse(List<DetailedRequestResponse> requests, String message) {
        this.requests = requests;
        this.message = message;
    }

    public List<DetailedRequestResponse> getRequests() {
        return requests;
    }

    public void setRequests(List<DetailedRequestResponse> requests) {
        this.requests = requests;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
