package com.byteforge.byteforge.web.mvc;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/notifications")
@PreAuthorize("isAuthenticated()")
public class NotificationController {

    @GetMapping
    public String notifications() {
        return "notifications";
    }
}
