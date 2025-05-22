package com.byteforge.byteforge.services;

import com.byteforge.byteforge.dto.response.WishlistItemDto;
import com.byteforge.byteforge.entities.WishlistItem;
import com.byteforge.byteforge.repositories.WishlistItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WishlistService {

    private final WishlistItemRepository wishlistItemRepository;

    public List<WishlistItemDto> getWishlistByUsername(String customerName) {
         return wishlistItemRepository.findAllProductCustomerbyName(customerName)
                 .stream()
                 .map(WishlistItemDto::fromEntity)
                 .collect(Collectors.toList());
    }
}
