package com.byteforge.byteforge.web.mvc;

import com.byteforge.byteforge.dto.CategoryDto;
import com.byteforge.byteforge.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public String showCategories(@RequestParam(required = false) String logout, Model model) {
        if (logout != null) {
            model.addAttribute("logoutMessage", "You have been logged out successfully");
        }
        List<CategoryDto> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);

        return "categories";
    }
}


