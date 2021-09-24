package com.naldi.naldiProject.payload.response;

import java.util.List;

public class AllRequestsResponse {
    private List<RequestResponse> requests;
    private String message;

    public AllRequestsResponse(List<RequestResponse> requests, String message) {
        this.requests = requests;
        this.message = message;
    }

    public List<RequestResponse> getRequests() {
        return requests;
    }

    public void setRequests(List<RequestResponse> requests) {
        this.requests = requests;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
