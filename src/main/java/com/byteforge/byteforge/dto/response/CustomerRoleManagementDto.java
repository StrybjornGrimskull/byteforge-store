package com.byteforge.byteforge.dto.response;

import java.util.Collection;

public record CustomerRoleManagementDto (
        Integer id,
        String email,
        Collection<String> authorities) {}
