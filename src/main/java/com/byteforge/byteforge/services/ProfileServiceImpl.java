package com.byteforge.byteforge.services;

import com.byteforge.byteforge.dto.request.ProfileRequestDto;
import com.byteforge.byteforge.dto.response.ProfileResponseDto;
import com.byteforge.byteforge.entities.Customer;
import com.byteforge.byteforge.entities.Profile;
import com.byteforge.byteforge.repositories.CustomerRepository;
import com.byteforge.byteforge.services.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final CustomerRepository customerRepository;

    @Override
    @Transactional(readOnly = true)
    public ProfileResponseDto getProfileByEmail(String email) {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        
        Profile profile = customer.getProfile();
        if (profile == null) {
            throw new RuntimeException("Profile not found");
        }

        return ProfileResponseDto.fromEntity(profile);
    }

    @Override
    @Transactional
    public ProfileResponseDto updateProfile(String email, ProfileRequestDto profileDto) {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        
        Profile profile = customer.getProfile();
        if (profile == null) {
            profile = new Profile();
            profile.setCustomer(customer);
            customer.setProfile(profile);
        }

        // Обновляем поля профиля
        profile.setFirstName(profileDto.firstName());
        profile.setLastName(profileDto.lastName());
        profile.setPhone(profileDto.phone());
        profile.setCity(profileDto.city());
        profile.setAddress(profileDto.address());
        profile.setPostIndex(profileDto.postIndex());
        profile.setBirthDate(profileDto.birthDate());

        customerRepository.save(customer);
        return ProfileResponseDto.fromEntity(profile);
    }
}