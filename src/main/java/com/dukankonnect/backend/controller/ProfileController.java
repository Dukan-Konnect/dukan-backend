package com.dukankonnect.backend.controller;

import com.dukankonnect.backend.model.AppUser;
import com.dukankonnect.backend.model.AuthRequests;
import com.dukankonnect.backend.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    private final UserRepository userRepository;

    public ProfileController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<?> getMyProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assert authentication != null;
        String currentPhoneNumber = authentication.getName();

        Optional<AppUser> userOptional = userRepository.findByPhoneNumber(currentPhoneNumber);

        if (userOptional.isPresent()) {
            AppUser currentUser = userOptional.get();
            return ResponseEntity.ok(currentUser);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User profile not found in database.");
        }
    }

    @PutMapping
    public ResponseEntity<String> updateMyProfile(@RequestBody AuthRequests.UpdateProfileRequest request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPhoneNumber = authentication.getName();

        Optional<AppUser> userOptional = userRepository.findByPhoneNumber(currentPhoneNumber);

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }

        AppUser user = userOptional.get();

        // only overwriting the existing data if the incoming request actually contains a new value
        if (request.getName() != null) {
            user.setName(request.getName());
        }

        if (request.getEmail() != null) {
            user.setEmail(request.getEmail());
        }

        userRepository.save(user);

        return ResponseEntity.ok("Profile updated successfully");
    }
}