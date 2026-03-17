package com.dukankonnect.backend.controller;

import com.dukankonnect.backend.model.AppUser;
import com.dukankonnect.backend.model.AuthRequests;
import com.dukankonnect.backend.repository.UserRepository;
import com.dukankonnect.backend.security.JwtService;
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
    private final JwtService jwtService;

    public ProfileController(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @GetMapping
    public ResponseEntity<?> getMyProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assert authentication != null;
        String currentPhoneNumber = authentication.getName();

        Optional<AppUser> userOptional = userRepository.findByPhoneNumber(currentPhoneNumber);

        if (userOptional.isPresent()) {
            AppUser currentUser = userOptional.get();
            return ResponseEntity.ok(new AuthRequests.UserProfileResponse(currentUser));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User profile not found in database.");
        }
    }

    @PutMapping
    public ResponseEntity<String> updateMyProfile(@RequestBody AuthRequests.UpdateProfileRequest request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not authenticated.");
        }
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

    @PostMapping("/phone/request")
    public ResponseEntity<?> requestPhoneUpdate(@RequestBody AuthRequests.LoginRequest request) {
        String newPhone = request.getPhoneNumber();

        Optional<AppUser> existingUser = userRepository.findByPhoneNumber(newPhone);
        if (existingUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Phone number is already registered to another account.");
        }

        return ResponseEntity.ok("OTP sent to new phone number: " + newPhone);
    }

    @PostMapping("/phone/verify")
    public ResponseEntity<?> verifyPhoneUpdate(@RequestBody AuthRequests.VerifyRequest request) {
        String newPhone = request.getPhoneNumber();
        String enteredOtp = request.getOtp();

        if (!"123456".equals(enteredOtp)) {
            return ResponseEntity.badRequest().body("Invalid OTP.");
        }

        if (userRepository.findByPhoneNumber(newPhone).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Phone number is already taken.");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not authenticated.");
        }
        String currentPhoneNumber = authentication.getName();

        AppUser currentUser = userRepository.findByPhoneNumber(currentPhoneNumber)
                .orElseThrow(() -> new RuntimeException("User not found in database."));

        currentUser.setPhoneNumber(newPhone);
        userRepository.save(currentUser);

        String newToken = jwtService.generateToken(newPhone);

        AuthRequests.VerifyResponse response = new AuthRequests.VerifyResponse(
                "Phone number updated successfully!",
                false,
                newToken
        );

        return ResponseEntity.ok(response);
    }
}