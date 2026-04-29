package gr.uoi.cs.mye030.project_2026.controller;

import gr.uoi.cs.mye030.project_2026.services.AnalyticsService;
import gr.uoi.cs.mye030.project_2026.services.ConferenceService;
import gr.uoi.cs.mye030.project_2026.services.JournalService;
import gr.uoi.cs.mye030.project_2026.services.YearService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class AnalyticsController {

    private final AnalyticsService analyticsService;
    private final ConferenceService conferenceService;
    private final JournalService journalService;
    private final YearService yearService;

    public AnalyticsController(AnalyticsService analyticsService, ConferenceService conferenceService, 
                               JournalService journalService, YearService yearService) {
        this.analyticsService = analyticsService;
        this.conferenceService = conferenceService;
        this.journalService = journalService;
        this.yearService = yearService;
    }

    @GetMapping("/linecharts")
    public String showLinecharts(
            @RequestParam(value = "analysisMode", defaultValue = "VENUES") String analysisMode,
            @RequestParam(value = "venueType", defaultValue = "CONF") String venueType,
            @RequestParam(value = "venueIds", required = false) List<Integer> venueIds,
            @RequestParam(value = "primaryForNames", required = false) List<String> primaryForNames,
            @RequestParam(value = "bestSubjectIds", required = false) List<Integer> bestSubjectIds,
            @RequestParam(value = "startYear", required = false) Integer startYear,
            @RequestParam(value = "endYear", required = false) Integer endYear,
            Model model) {

        model.addAttribute("conferences", conferenceService.getAllConferences());
        model.addAttribute("journals", journalService.getAllJournals());
        model.addAttribute("primaryFors", analyticsService.getAllPrimaryFoRs());
        model.addAttribute("bestSubjectAreas", analyticsService.getAllBestSubjectAreas());
        model.addAttribute("availableYears", yearService.getAvailableYears());

        List<Map<String, Object>> chartData = null;

        if ("VENUES".equals(analysisMode) && venueIds != null && !venueIds.isEmpty()) {
            chartData = analyticsService.getArticlesPerYear(venueType, venueIds, startYear, endYear);
            model.addAttribute("searchPerformed", true);
            
        } 
        else if ("CATEGORIES".equals(analysisMode)) {
            if ("CONF".equals(venueType) && primaryForNames != null && !primaryForNames.isEmpty()) {
                chartData = analyticsService.getCategoryEvolution(venueType, primaryForNames, null, startYear, endYear);
                model.addAttribute("searchPerformed", true);
            } else if ("JOURNAL".equals(venueType) && bestSubjectIds != null && !bestSubjectIds.isEmpty()) {
                chartData = analyticsService.getCategoryEvolution(venueType, null, bestSubjectIds, startYear, endYear);
                model.addAttribute("searchPerformed", true);
            }
        }

        model.addAttribute("chartData", chartData);
        model.addAttribute("analysisMode", analysisMode);
        model.addAttribute("selectedVenueType", venueType);
        model.addAttribute("selectedVenueIds", venueIds);
        model.addAttribute("selectedPrimaryFors", primaryForNames);
        model.addAttribute("selectedBestSubjects", bestSubjectIds);
        model.addAttribute("selectedStartYear", startYear);
        model.addAttribute("selectedEndYear", endYear);

        return "linecharts/linecharts";
    }
    
    @GetMapping("/barcharts")
    public String showBarcharts(
            @RequestParam(value = "venueType", defaultValue = "CONF") String venueType,
            @RequestParam(value = "venueIds", required = false) List<Integer> venueIds,
            @RequestParam(value = "publishers", required = false) List<String> selectedPublishers,
            @RequestParam(value = "startYear", required = false) Integer startYear,
            @RequestParam(value = "endYear", required = false) Integer endYear,
            Model model) {

        model.addAttribute("conferences", conferenceService.getAllConferences());
        model.addAttribute("journals", journalService.getAllJournals());
        model.addAttribute("allPublishers", analyticsService.getAllPublishers()); 
        model.addAttribute("availableYears", yearService.getAvailableYears());

        List<Map<String, Object>> chartData = null;

        if ("PUBLISHER".equals(venueType) && selectedPublishers != null && !selectedPublishers.isEmpty()) {
            chartData = analyticsService.getPublisherBarchartData(selectedPublishers);
            model.addAttribute("searchPerformed", true);
        } 
        else if (venueIds != null && !venueIds.isEmpty()) {
            chartData = analyticsService.getVenueBarchartData(venueType, venueIds, startYear, endYear);
            model.addAttribute("searchPerformed", true);
        }

        model.addAttribute("chartData", chartData);
        model.addAttribute("selectedVenueType", venueType);
        model.addAttribute("selectedVenueIds", venueIds);
        model.addAttribute("selectedPublishers", selectedPublishers);
        model.addAttribute("selectedStartYear", startYear);
        model.addAttribute("selectedEndYear", endYear);

        return "barcharts/barcharts";
    }
    
    @GetMapping("/scatterplots")
    public String showScatterPlots(
            @RequestParam(value = "scatterType", defaultValue = "JOURNAL_METRICS") String scatterType,
            
            @RequestParam(value = "xMetric", defaultValue = "totalrefs") String xMetric,
            @RequestParam(value = "yMetric", defaultValue = "total_cites_3y") String yMetric,
            
            @RequestParam(value = "venueType", defaultValue = "CONF") String venueType,
            @RequestParam(value = "venueIds", required = false) List<Integer> venueIds,
            Model model) {

        Map<String, String> metrics = new java.util.LinkedHashMap<>();
        metrics.put("totalrefs", "Total References");
        metrics.put("total_cites_3y", "Total Cites (3 Years)");
        metrics.put("citable_docs_3y", "Citable Docs (3 Years)");
        metrics.put("cites_doc_2y", "Cites / Doc (2 Years)");
        metrics.put("refs_doc", "Refs / Doc");
        model.addAttribute("metrics", metrics);
        model.addAttribute("selectedX", xMetric);
        model.addAttribute("selectedY", yMetric);

        
        model.addAttribute("conferences", conferenceService.getAllConferences());
        model.addAttribute("journals", journalService.getAllJournals());
        model.addAttribute("selectedVenueType", venueType);
        model.addAttribute("selectedVenueIds", venueIds);

        model.addAttribute("scatterType", scatterType);

        if ("JOURNAL_METRICS".equals(scatterType)) {
            List<Map<String, Object>> chartData1 = analyticsService.getJournalScatterData(xMetric, yMetric);
            model.addAttribute("chartData1", chartData1);
        } else if ("VENUE_YEARS".equals(scatterType) && venueIds != null && !venueIds.isEmpty()) {
            List<Map<String, Object>> chartData2 = analyticsService.getVenueScatterData(venueType, venueIds);
            model.addAttribute("chartData2", chartData2);
        }

        return "scatterplots/scatterplots";
    }
}