package com.byteforge.byteforge.web.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class LegalController {

    @GetMapping("/terms")
    public String termsOfService() {
        return "terms";
    }

    @GetMapping("/privacy")
    public String privacyPolicy() {
        return "privacy";
    }
}
