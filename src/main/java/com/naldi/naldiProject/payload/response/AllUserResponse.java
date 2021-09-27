package com.naldi.naldiProject.payload.response;

import java.util.List;

public class AllUserResponse {
    private List<UserResponse> users;
    private String message;

    public AllUserResponse(List<UserResponse> users, String message) {
        this.users = users;
        this.message = message;
    }

    public List<UserResponse> getUsers() {
        return users;
    }

    public void setUsers(List<UserResponse> users) {
        this.users = users;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
