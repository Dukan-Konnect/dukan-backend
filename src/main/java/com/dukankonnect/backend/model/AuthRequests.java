package com.dukankonnect.backend.model;

public class AuthRequests {

    public static class LoginRequest {
        private String phoneNumber;
        public String getPhoneNumber() { return phoneNumber; }
        public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    }

    public static class VerifyRequest {
        private String phoneNumber;
        private String otp;
        public String getPhoneNumber() { return phoneNumber; }
        public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
        public String getOtp() { return otp; }
        public void setOtp(String otp) { this.otp = otp; }
    }

    public static class ProfileUpdateRequest {
        private String phoneNumber;
        private String name;
        private String email;

        public String getPhoneNumber() { return phoneNumber; }
        public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
    }


    public static class VerifyResponse {
        private String message;
        private boolean isNewUser;
        private String token;

        public VerifyResponse(String message, boolean isNewUser, String token) {
            this.message = message;
            this.isNewUser = isNewUser;
            this.token = token;
        }

        public String getMessage() { return message; }
        public boolean isNewUser() { return isNewUser; }
        public String getToken() { return token; }
    }

    public static class UpdateProfileRequest {
        private String name;
        private String email;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
    }
}