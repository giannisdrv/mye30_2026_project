package gr.uoi.cs.mye030.project_2026.services;

import gr.uoi.cs.mye030.project_2026.dao.JournalDAO;
import gr.uoi.cs.mye030.project_2026.model.Journal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JournalServiceImpl implements JournalService{

    private final JournalDAO journalDAO;

    @Autowired
    public JournalServiceImpl(JournalDAO journalDAO) {
        this.journalDAO = journalDAO;
    }
    
    @Override
    public List<Journal> getAllJournals() {
        return journalDAO.findAll();
    }
    
    @Override
    public Map<String, Object> getJournalProfile(int journalId, Integer startYear, Integer endYear) {
        Map<String, Object> profileData = new HashMap<>();

        Journal journal = journalDAO.findById(journalId);
        profileData.put("journal", journal);

        Map<String, Object> overallStats = journalDAO.getJournalOverallStats(journalId, startYear, endYear);
        
        int totalArticles = ((Number) overallStats.get("total_articles")).intValue();
        int totalAuthors = ((Number) overallStats.get("total_authors")).intValue();
        
        double avgAuthorsPerArticle = 0.0;
        String activeYearsStr = "N/A";
        double avgArticlesPerYear = 0.0;
        double avgAuthorsPerYear = 0.0;
        
        if (overallStats.get("first_year") != null && overallStats.get("last_year") != null) {
            int firstYear = ((Number) overallStats.get("first_year")).intValue();
            int lastYear = ((Number) overallStats.get("last_year")).intValue();
            
            activeYearsStr = firstYear + " - " + lastYear;
            
            int activeYearsCount = (lastYear - firstYear) + 1;
            
            if (activeYearsCount > 0) {
                avgArticlesPerYear = (double) totalArticles / activeYearsCount;
                avgAuthorsPerYear = (double) totalAuthors / activeYearsCount;
            }
        }
        
        if (totalArticles > 0) {
            avgAuthorsPerArticle = Math.round(((double) totalAuthors / totalArticles) * 10.0) / 10.0;
        }
        
        overallStats.put("active_years_str", activeYearsStr);
        overallStats.put("avg_articles_per_year", String.format(java.util.Locale.US, "%.2f", avgArticlesPerYear));
        overallStats.put("avg_authors_per_year", String.format(java.util.Locale.US, "%.2f", avgAuthorsPerYear));
        overallStats.put("avg_authors_per_article", String.format(java.util.Locale.US, "%.2f",avgAuthorsPerArticle));
        
        profileData.put("overallStats", overallStats);

        List<Map<String, Object>> yearlyStats = journalDAO.getJournalYearlyStats(journalId, startYear, endYear);
        profileData.put("yearlyStats", yearlyStats);

        return profileData;
    }
}