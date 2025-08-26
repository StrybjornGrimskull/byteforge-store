package com.byteforge.byteforge.services;

import com.byteforge.byteforge.dto.request.ProfileRequestDto;
import com.byteforge.byteforge.dto.response.CustomerNameFromProfileDto;
import com.byteforge.byteforge.dto.response.ProfileResponseDto;
import com.byteforge.byteforge.entities.Customer;
import com.byteforge.byteforge.entities.Profile;
import com.byteforge.byteforge.repositories.CustomerRepository;
import com.byteforge.byteforge.repositories.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final CustomerRepository customerRepository;
    private final ProfileRepository profileRepository;

    @Transactional(readOnly = true)
    public ProfileResponseDto getProfileByEmail(String email) {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("Customer not found"));
        
        Profile profile = customer.getProfile();
        if (profile == null) throw new NoSuchElementException("Profile not found");
        
        return new ProfileResponseDto(
                profile.getCustomerId(),
                profile.getFirstName(),
                profile.getLastName(),
                profile.getPhone(),
                profile.getCity(),
                profile.getAddress(),
                profile.getPostIndex(),
                profile.getBirthDate(),
                profile.getPhone(),
                customer.getEmail()
        );
    }

    @Transactional(readOnly = true)
    public CustomerNameFromProfileDto getCustomerNameByEmail(String email) {
        return profileRepository.findCustomerNameByEmail(email);
    }

    @Transactional
    public void updateProfile(String email, ProfileRequestDto profileDto) {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("Customer not found"));
        
        Profile profile = customer.getProfile();
        if (profile == null) {
            profile = new Profile();
            profile.setCustomer(customer);
            customer.setProfile(profile);
        }

        profile.setFirstName(profileDto.firstName());
        profile.setLastName(profileDto.lastName());
        profile.setPhone(profileDto.phone());
        profile.setCity(profileDto.city());
        profile.setAddress(profileDto.address());
        profile.setPostIndex(profileDto.postIndex());
        profile.setBirthDate(profileDto.birthDate());

        customerRepository.save(customer);
    }
}