package com.dukankonnect.backend.controller;

import com.dukankonnect.backend.model.AppUser;
import com.dukankonnect.backend.model.AuthRequests;
import com.dukankonnect.backend.repository.UserRepository;
import com.dukankonnect.backend.security.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    public AuthController(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> requestOtp(@RequestBody AuthRequests.LoginRequest request) {
        String phone = request.getPhoneNumber();

        Optional<AppUser> existingUser = userRepository.findByPhoneNumber(phone);
        if (existingUser.isEmpty()) {
            AppUser newUser = new AppUser(phone);
            userRepository.save(newUser);
        }

        return ResponseEntity.ok("OTP sent successfully to " + phone);
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyOtp(@RequestBody AuthRequests.VerifyRequest request) {
        String phone = request.getPhoneNumber();
        String enteredOtp = request.getOtp();

        Optional<AppUser> userOptional = userRepository.findByPhoneNumber(phone);
        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found.");
        }

        AppUser user = userOptional.get();

        if ("123456".equals(enteredOtp)) {
            boolean isNewUser = (user.getName() == null || user.getName().trim().isEmpty());

            String token = jwtService.generateToken(user.getPhoneNumber());

            AuthRequests.VerifyResponse response = new AuthRequests.VerifyResponse(
                    "Login Successful!",
                    isNewUser,
                    token,
                    user.getName(),
                    user.getEmail()
            );
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body("Invalid OTP.");
        }
    }

    @PostMapping("/complete-profile")
    public ResponseEntity<String> completeProfile(@RequestBody AuthRequests.ProfileUpdateRequest request) {
        Optional<AppUser> userOptional = userRepository.findByPhoneNumber(request.getPhoneNumber());

        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found.");
        }

        AppUser user = userOptional.get();
        user.setName(request.getName());
        user.setEmail(request.getEmail());

        userRepository.save(user);

        return ResponseEntity.ok("Profile updated successfully!");
    }

    // ADMIN ENDPOINT to generate master key
    @PostMapping("/admin/master-key")
    public ResponseEntity<String> getMasterKey(@RequestParam String adminPhone) {
        String masterToken = jwtService.generateMasterAdminToken(adminPhone);
        return ResponseEntity.ok(masterToken);
    }
}