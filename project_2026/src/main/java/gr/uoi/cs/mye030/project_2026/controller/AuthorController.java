package gr.uoi.cs.mye030.project_2026.controller;

import gr.uoi.cs.mye030.project_2026.services.AuthorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/authors")
    public String showAuthorProfile(
            @RequestParam(value = "authorName", required = false) String authorName,
            Model model) {

        if (authorName != null && !authorName.trim().isEmpty()) {
            Map<String, Object> profileData = authorService.getAuthorProfile(authorName.trim());
            
            model.addAttribute("searchPerformed", true);
            model.addAttribute("searchedName", authorName.trim());
            
            if (profileData != null) {
                model.addAttribute("authorFound", true);
                model.addAttribute("stats", profileData.get("stats"));
                model.addAttribute("yearlyStats", profileData.get("yearlyStats"));
            } else {
                model.addAttribute("authorFound", false);
            }
        }

        return "authors/authors";
    }
}