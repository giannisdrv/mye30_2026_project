package gr.uoi.cs.mye030.project_2026.controller;

import gr.uoi.cs.mye030.project_2026.model.Conference;
import gr.uoi.cs.mye030.project_2026.services.ConferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller 
public class ConferenceController {

    private final ConferenceService conferenceService;

    @Autowired
    public ConferenceController(ConferenceService conferenceService) {
        this.conferenceService = conferenceService;
    }

    @GetMapping("/conferences")
    public String showConferences(
            @RequestParam(value = "confId", required = false) Integer confId,
            @RequestParam(value = "startYear", required = false) Integer startYear,
            @RequestParam(value = "endYear", required = false) Integer endYear,
            Model model) {
        
        List<Conference> conferencesList = conferenceService.getAllConferences();
        model.addAttribute("conferences", conferencesList);
        
        if (confId != null) {
            Map<String, Object> profileData = conferenceService.getConferenceProfile(confId, startYear, endYear);
            
            model.addAttribute("confDetails", profileData.get("conference"));
            model.addAttribute("overallStats", profileData.get("overallStats"));
            model.addAttribute("yearlyStats", profileData.get("yearlyStats"));
            model.addAttribute("searchPerformed", true);
        }
        
        return "conferences/conferences"; 
    }
}