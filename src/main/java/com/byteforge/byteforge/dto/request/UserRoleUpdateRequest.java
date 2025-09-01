package com.byteforge.byteforge.dto.request;

import java.util.List;

public record UserRoleUpdateRequest(
        Integer userId,
        List<String> roles
) {}
