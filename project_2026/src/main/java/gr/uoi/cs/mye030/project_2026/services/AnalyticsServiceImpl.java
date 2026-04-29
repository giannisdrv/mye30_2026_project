package gr.uoi.cs.mye030.project_2026.services;

import gr.uoi.cs.mye030.project_2026.dao.AnalyticsDAO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AnalyticsServiceImpl implements AnalyticsService{

    private final AnalyticsDAO analyticsDAO;

    public AnalyticsServiceImpl(AnalyticsDAO analyticsDAO) {
        this.analyticsDAO = analyticsDAO;
    }
    
    @Override
    public List<Map<String, Object>> getLinechartData(String venueType, List<Integer> venueIds, Integer startYear, Integer endYear) {
        return analyticsDAO.getArticlesPerYear(venueType, venueIds, startYear, endYear);
        
    } 
    
    @Override
    public List<Map<String, Object>> getVenueBarchartData(String venueType, List<Integer> venueIds, Integer startYear, Integer endYear) {
        List<Map<String, Object>> rawData = analyticsDAO.getVenueMetrics(venueType, venueIds, startYear, endYear);
        
        for (Map<String, Object> row : rawData) {
            int totalArticles = ((Number) row.get("total_articles")).intValue();
            int activeYears = ((Number) row.get("active_years")).intValue();
            int totalAuthors = ((Number) row.get("total_authors")).intValue();
            
            double avgArticlesYear = 0.0;
            double avgAuthorsYear = 0.0;
            
            if (activeYears > 0) {
                avgArticlesYear = (double) totalArticles / activeYears;
                avgAuthorsYear = (double) totalAuthors / activeYears;
            }
            
            row.put("avg_articles_year",String.format(java.util.Locale.US, "%.2f", avgArticlesYear));
            row.put("avg_authors_year", String.format(java.util.Locale.US, "%.2f", avgAuthorsYear));
        }
        
        return rawData;
    }
    
    @Override
    public List<Map<String, Object>> getPublisherBarchartData(List<String> publishers) {
        return analyticsDAO.getPublisherMetrics(publishers);
    }
    
    @Override
    public List<String> getAllPublishers() {
        return analyticsDAO.getAllPublishers();
    }
    
    @Override
    public List<Map<String, Object>> getJournalScatterData(String xMetric, String yMetric) {
        return analyticsDAO.getJournalScatterData(xMetric, yMetric);
    }
    
    @Override
    public List<Map<String, Object>> getVenueScatterData(String venueType, List<Integer> venueIds) {
        List<Map<String, Object>> rawData = analyticsDAO.getVenueScatterData(venueType, venueIds);
        
        for (Map<String, Object> row : rawData) {
            int totalArticles = ((Number) row.get("total_articles")).intValue();
            int totalAuthors = ((Number) row.get("total_authors")).intValue();
            
            double avgAuthors = 0.0;
            
            if (totalArticles > 0) {
                avgAuthors = (double) totalAuthors / totalArticles;
            }
            
            row.put("avg_authors", String.format(java.util.Locale.US, "%.2f", avgAuthors));
        }
        
        return rawData;
    }
    
    @Override
    public List<String> getAllPrimaryFoRs() {
        return analyticsDAO.getAllPrimaryFoRs();
    }
    
    @Override
    public List<Map<String, Object>> getAllBestSubjectAreas() {
        return analyticsDAO.getAllBestSubjectAreas();
    }
    
    @Override
    public List<Map<String, Object>> getCategoryEvolution(String venueType, List<String> primaryFors, List<Integer> bestSubjectIds, Integer startYear, Integer endYear) {
        return analyticsDAO.getCategoryEvolution(venueType, primaryFors, bestSubjectIds, startYear, endYear);
    }
    
    @Override
    public List<Map<String, Object>> getArticlesPerYear(String venueType, List<Integer> venueIds, Integer startYear, Integer endYear) {
        return analyticsDAO.getArticlesPerYear(venueType, venueIds, startYear, endYear);
    }
}