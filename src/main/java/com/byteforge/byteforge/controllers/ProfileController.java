package com.byteforge.byteforge.controllers;

import com.byteforge.byteforge.dto.request.ProfileRequestDto;
import com.byteforge.byteforge.dto.response.ProfileResponseDto;
import com.byteforge.byteforge.services.ProfileService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping
    public ResponseEntity<ProfileResponseDto> getProfile(Authentication authentication) {
        String email = authentication.getName();
        ProfileResponseDto profile = profileService.getProfileByEmail(email);
        return ResponseEntity.ok(profile);
    }

    @PutMapping
    public ResponseEntity<ProfileResponseDto> updateProfile(
            @RequestBody @Valid ProfileRequestDto profileDto,
            Authentication authentication) {
        String email = authentication.getName();
        ProfileResponseDto updatedProfile = profileService.updateProfile(email, profileDto);
        return ResponseEntity.ok(updatedProfile);
    }
}