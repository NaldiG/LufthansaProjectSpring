package com.naldi.naldiProject.controllers;

import com.naldi.naldiProject.models.*;
import com.naldi.naldiProject.models.EStatus;
import com.naldi.naldiProject.models.Request;
import com.naldi.naldiProject.models.User;
import com.naldi.naldiProject.payload.request.AdminDaysRequest;
import com.naldi.naldiProject.payload.request.DaysRequest;
import com.naldi.naldiProject.payload.request.DeleteRequest;
import com.naldi.naldiProject.payload.request.UpdateRequest;
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
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	DaysRepository daysRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtUtils jwtUtils;

	@GetMapping("/all")
	public String allAccess() {
		return "No new announcements!";
	}

	@PostMapping("/update")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> updateUser(@Valid @RequestBody UpdateRequest updateRequest) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails;
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			userDetails = (UserDetailsImpl) authentication.getPrincipal();
		}else{
			return ResponseEntity.ok(new MessageResponse("User not updated!" + authentication.getPrincipal()));
		}

		Optional<User> user= userRepository.findById(userDetails.getId());
		user.ifPresent(value -> {
			value.setUsername(updateRequest.getUsername());
			value.setEmail(updateRequest.getEmail());
			value.setPassword(encoder.encode(updateRequest.getPassword()));
			userRepository.save(value);
		});

		authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(updateRequest.getUsername(), updateRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());

		return ResponseEntity.ok(new UpdateResponse(jwt,
				userDetails.getId(),
				userDetails.getUsername(),
				userDetails.getEmail(),
				roles,
				"User updated!",
				userDetails.getDays()));
	}

	@PostMapping("/request")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> userDayRequest(@Valid @RequestBody DaysRequest daysRequest) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails;
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			userDetails = (UserDetailsImpl) authentication.getPrincipal();
		}else{
			return ResponseEntity.ok(new MessageResponse("User not found!" + authentication.getPrincipal()));
		}

		if(daysRequest.getDaysRequested() > daysRequest.getDaysLeft()){
			return ResponseEntity.ok(new MessageResponse("Not enough free days left!" + authentication.getPrincipal()));
		}

		User user = userRepository.findById(userDetails.getId()).get();
		Request request = new Request(EStatus.STATUS_PENDING, daysRequest.getDaysRequested(), daysRequest.getDaysLeft(),
				daysRequest.getReason(), "Waiting for response", user);

		daysRepository.save(request);

		return ResponseEntity.ok(new DaysResponse(request.getId(), request.getDaysRequested(), request.getReason(),
				request.getStatus(),request.getResponse(), "Request submitted"));
	}

	@GetMapping("/allUserRequests")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> allUserDayRequests() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails;
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			userDetails = (UserDetailsImpl) authentication.getPrincipal();
		}else{
			return ResponseEntity.ok(new MessageResponse("User not found!" + authentication.getPrincipal()));
		}

		User user = userRepository.findById(userDetails.getId()).get();
		Set<Request> requests = user.getRequests();
		List<RequestResponse> list = new ArrayList<RequestResponse>();
		requests.forEach(request -> {
			list.add(new RequestResponse(request.getId(),request.getStatus(),request.getDaysRequested(),
					request.getDaysRemaining(),request.getReason(),request.getResponse()));
		});
		return ResponseEntity.ok(new AllRequestsResponse(list, "Request list successfully sent"));
	}

	@PostMapping("/deleteRequest")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> deleteRequests(@Valid @RequestBody DeleteRequest deleteRequest) {

		daysRepository.deleteById(deleteRequest.getId());
		return ResponseEntity.ok(new MessageResponse("Request Deleted " + deleteRequest.getId()));
	}
}
