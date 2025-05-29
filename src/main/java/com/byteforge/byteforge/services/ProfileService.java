package com.byteforge.byteforge.services;

import com.byteforge.byteforge.dto.request.ProfileRequestDto;
import com.byteforge.byteforge.dto.response.ProfileResponseDto;

public interface ProfileService {
    ProfileResponseDto getProfileByEmail(String email);
    ProfileResponseDto updateProfile(String email, ProfileRequestDto profileDto);
}