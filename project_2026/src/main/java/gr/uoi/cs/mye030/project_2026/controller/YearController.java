package gr.uoi.cs.mye030.project_2026.controller;

import gr.uoi.cs.mye030.project_2026.services.ConferenceService;
import gr.uoi.cs.mye030.project_2026.services.JournalService;
import gr.uoi.cs.mye030.project_2026.services.YearService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class YearController {

    private final YearService yearService;
    private final ConferenceService conferenceService;
    private final JournalService journalService;

    public YearController(YearService yearService, ConferenceService conferenceService, JournalService journalService) {
        this.yearService = yearService;
        this.conferenceService = conferenceService;
        this.journalService = journalService;
    }

    @GetMapping("/years")
    public String showYearProfile(
            @RequestParam(value = "year", required = false) Integer year,
            @RequestParam(value = "confId", required = false) Integer confId,
            @RequestParam(value = "journalId", required = false) Integer journalId,
            @RequestParam(value = "authorName", required = false) String authorName,
            Model model) {

        model.addAttribute("availableYears", yearService.getAvailableYears());
        model.addAttribute("conferences", conferenceService.getAllConferences());
        model.addAttribute("journals", journalService.getAllJournals());

        if (year != null) {
            Map<String, Object> profileData = yearService.getYearProfile(year, confId, journalId, authorName);
            
            model.addAttribute("stats", profileData.get("stats"));
            model.addAttribute("publications", profileData.get("publications"));
            model.addAttribute("searchPerformed", true);
            
            model.addAttribute("selectedYear", year);
            model.addAttribute("selectedConfId", confId);
            model.addAttribute("selectedJournalId", journalId);
            model.addAttribute("selectedAuthorName", authorName);
        }

        return "years/years"; 
    }
}