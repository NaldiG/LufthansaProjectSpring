package com.naldi.naldiProject.controllers;

import com.naldi.naldiProject.models.*;
import com.naldi.naldiProject.models.EStatus;
import com.naldi.naldiProject.models.Request;
import com.naldi.naldiProject.models.User;
import com.naldi.naldiProject.payload.request.*;
import com.naldi.naldiProject.payload.response.*;
import com.naldi.naldiProject.payload.response.*;
import com.naldi.naldiProject.repository.DaysRepository;
import com.naldi.naldiProject.repository.RoleRepository;
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
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    DaysRepository daysRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    RoleRepository roleRepository;

    @GetMapping("/allUsers")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllUsers() {
       List<User> list = userRepository.findAll();
       List<UserResponse> userList = new ArrayList<UserResponse>();
       list.forEach(user -> {
            userList.add(new UserResponse(user.getId(), user.getUsername(), user.getEmail(), user.getDays(), user.getRoles()));
       });
       return ResponseEntity.ok(new AllUserResponse(userList, "User list successfully sent"));
    }

    @PostMapping("/findUsers")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getUsers(@Valid @RequestBody SearchRequest SearchRequest) {
        List<User> list = userRepository.findByUsernameContainingIgnoreCase(SearchRequest.getUsername());
        List<UserResponse> userList = new ArrayList<UserResponse>();
        list.forEach(user -> {
            userList.add(new UserResponse(user.getId(), user.getUsername(), user.getEmail(), user.getDays(), user.getRoles()));
        });
        return ResponseEntity.ok(new AllUserResponse(userList, "User list successfully sent"));
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UpdateUserRequest updateRequest) {

        Optional<User> user= userRepository.findById(updateRequest.getUserId());
        user.ifPresent(value -> {
            value.setUsername(updateRequest.getUsername());
            value.setEmail(updateRequest.getEmail());
            userRepository.save(value);
        });

        return ResponseEntity.ok(new MessageResponse("User Updated"));
    }

    @PostMapping("/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@Valid @RequestBody UserDeleteRequest deleteRequest) {

        Optional<User> user= userRepository.findById(deleteRequest.getId());
        user.ifPresent(value -> {
            userRepository.delete(value);
        });

        return ResponseEntity.ok(new MessageResponse("User Deleted"));
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createUser(@Valid @RequestBody SignupRequest signUpRequest) {

        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User created successfully!"));
    }


}
