package gr.uoi.cs.mye030.project_2026.services;

import gr.uoi.cs.mye030.project_2026.dao.AuthorDAO;
import org.springframework.stereotype.Service;

import java.util.HashMap;
//import java.util.List;
import java.util.Map;
import java.util.Locale;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDAO authorDAO;

    public AuthorServiceImpl(AuthorDAO authorDAO) {
        this.authorDAO = authorDAO;
    }
    
    @Override
    public Map<String, Object> getAuthorProfile(String authorName) {
        Map<String, Object> stats = authorDAO.getAuthorStats(authorName);
        
        if (stats == null) {
            return null; 
        }

        int firstYear = ((Number) stats.get("first_year")).intValue();
        int lastYear = ((Number) stats.get("last_year")).intValue();
        int totalArticles = ((Number) stats.get("total_articles")).intValue();
        
        int activeYears = (lastYear - firstYear) + 1;
        double avgArticlesPerYear = (double) totalArticles / activeYears;
        
        // we used local us because we had some problem with the , and . difference between languages 
        stats.put("avg_articles_per_year", String.format(Locale.US,"%.2f", avgArticlesPerYear));
        stats.put("active_years", activeYears);

        Map<String, Object> profileData = new HashMap<>();
        profileData.put("stats", stats);
        profileData.put("yearlyStats", authorDAO.getAuthorYearlyStats(authorName));
        
        return profileData;
    }
}