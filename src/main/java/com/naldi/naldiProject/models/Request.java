package com.naldi.naldiProject.models;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(	name = "requests" )
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EStatus status;

    private Integer daysRequested;

    private Integer daysRemaining;

    @Size(max = 500)
    private String reason;

    @Size(max = 500)
    private String response;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Request() {
    }

    public Request(EStatus status, Integer daysRequested, Integer daysRemaining, String reason, String response, User user) {
        this.status = status;
        this.daysRequested = daysRequested;
        this.daysRemaining = daysRemaining;
        this.reason = reason;
        this.response = response;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
}
