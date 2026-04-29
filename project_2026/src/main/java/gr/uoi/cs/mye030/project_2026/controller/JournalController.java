package gr.uoi.cs.mye030.project_2026.controller;

import gr.uoi.cs.mye030.project_2026.model.Journal;
import gr.uoi.cs.mye030.project_2026.services.JournalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class JournalController {

    private final JournalService journalService;

    @Autowired
    public JournalController(JournalService journalService) {
        this.journalService = journalService;
    }

    @GetMapping("/journals")
    public String showJournals(
            @RequestParam(value = "journalId", required = false) Integer journalId,
            @RequestParam(value = "startYear", required = false) Integer startYear,
            @RequestParam(value = "endYear", required = false) Integer endYear,
            Model model) {
        
        List<Journal> journalsList = journalService.getAllJournals();
        model.addAttribute("journals", journalsList);
        
        if (journalId != null) {
            Map<String, Object> profileData = journalService.getJournalProfile(journalId, startYear, endYear);
            
            model.addAttribute("journalDetails", profileData.get("journal"));
            model.addAttribute("overallStats", profileData.get("overallStats"));
            model.addAttribute("yearlyStats", profileData.get("yearlyStats"));
            model.addAttribute("searchPerformed", true);
            
        }
        
        return "journals/journals"; 
    }
}