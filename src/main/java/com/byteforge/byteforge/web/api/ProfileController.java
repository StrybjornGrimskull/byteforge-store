package com.byteforge.byteforge.web.api;

import com.byteforge.byteforge.dto.request.ProfileRequestDto;
import com.byteforge.byteforge.dto.response.ProfileResponseDto;
import com.byteforge.byteforge.services.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/profile")
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping
    public ResponseEntity<ProfileResponseDto> getProfile(Authentication authentication) {
        String email = authentication.getName();
        ProfileResponseDto profile = profileService.getProfileByEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body(profile);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateProfile(
            @RequestBody @Valid ProfileRequestDto profileDto,
            Authentication authentication) {
        String email = authentication.getName();
        profileService.updateProfile(email, profileDto);
    }
}