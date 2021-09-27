package com.naldi.naldiProject.controllers;

import com.naldi.naldiProject.models.*;
import com.naldi.naldiProject.models.EStatus;
import com.naldi.naldiProject.models.Request;
import com.naldi.naldiProject.models.User;
import com.naldi.naldiProject.payload.request.*;
import com.naldi.naldiProject.payload.response.*;
import com.naldi.naldiProject.payload.response.*;
import com.naldi.naldiProject.repository.DaysRepository;
import com.naldi.naldiProject.repository.UserRepository;
import com.naldi.naldiProject.security.jwt.JwtUtils;
import com.naldi.naldiProject.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/mod")
public class ModController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    DaysRepository daysRepository;

    @GetMapping("/allRequests")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<?> getUserRequests() {
        List<User> userList = userRepository.findAll();
        List<DetailedRequestResponse> list = new ArrayList<DetailedRequestResponse>();
        userList.forEach(user -> {
            Set<Request> requests = user.getRequests();
            requests.forEach(request -> {
                if(request.getStatus() == EStatus.STATUS_PENDING){
                    list.add(new DetailedRequestResponse(user.getId(), user.getUsername(), user.getEmail(), request.getId(),request.getStatus(),request.getDaysRequested(),
                            request.getDaysRemaining(),request.getReason(),request.getResponse()));
                }
            });
        });
        return ResponseEntity.ok(new AllDetailedRequestResponse(list, "Request list successfully sent"));
    }

    @PostMapping("/searchRequests")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<?> searchUserRequests(@Valid @RequestBody ModRequest modRequest) {
        List<User> userList = userRepository.findAll();
        List<DetailedRequestResponse> list = new ArrayList<DetailedRequestResponse>();
        userList.forEach(user -> {
            if(user.getUsername().contains(modRequest.getUsername())){
                Set<Request> requests = user.getRequests();
                requests.forEach(request -> {
                    if(request.getStatus() == EStatus.STATUS_PENDING){
                        list.add(new DetailedRequestResponse(user.getId(), user.getUsername(), user.getEmail(), request.getId(),request.getStatus(),request.getDaysRequested(),
                                request.getDaysRemaining(),request.getReason(),request.getResponse()));
                    }
                });
            }
        });
        return ResponseEntity.ok(new AllDetailedRequestResponse(list, "Request list successfully sent " + modRequest.getUsername()));
    }

    @PostMapping("/response")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<?> adminDayResponse(@Valid @RequestBody AdminDaysRequest daysRequest) {
        User user = userRepository.findById(daysRequest.getUserId()).get();
        Request request = daysRepository.findById(daysRequest.getRequestId()).get();
        request.setResponse(daysRequest.getResponse());

        String strStatus = daysRequest.getStatus();
        EStatus status;

        if(strStatus.equals("STATUS_ACCEPTED")){
            status = EStatus.STATUS_ACCEPTED;
            user.setDays(user.getDays() - daysRequest.getDaysRequested());
            userRepository.save(user);
        }else{
            status = EStatus.STATUS_DENIED;
        }

        request.setStatus(status);
        daysRepository.save(request);
        return ResponseEntity.ok(new DaysResponse(request.getId(), request.getDaysRequested(), request.getReason(),
                request.getStatus(),request.getResponse(), "Request processed"));
    }
}
