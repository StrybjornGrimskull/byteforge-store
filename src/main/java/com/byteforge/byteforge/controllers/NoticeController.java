package com.byteforge.byteforge.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class NoticeController {
    @GetMapping("/notices")
    public String helloPage() {
        return "hello";
    }
}
