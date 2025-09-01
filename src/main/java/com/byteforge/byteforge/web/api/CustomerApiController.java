package com.byteforge.byteforge.web.api;

import com.byteforge.byteforge.dto.request.UserRoleUpdateRequest;
import com.byteforge.byteforge.dto.response.CustomerRoleManagementDto;
import com.byteforge.byteforge.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerApiController {

    private final CustomerService customerService;

    @GetMapping("/role-management")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<CustomerRoleManagementDto>> getCustomersForRoleManagement(
            Pageable pageable,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String role) {
        Page<CustomerRoleManagementDto> customers = customerService.getAllCustomersForRoleManagement(pageable, search, role);
        return ResponseEntity.ok(customers);
    }

    @PutMapping("/role-management")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> updateUserRoles(@RequestBody UserRoleUpdateRequest request) {
        try {
            customerService.updateUserRoles(request);
            return ResponseEntity.ok("Roles updated successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
