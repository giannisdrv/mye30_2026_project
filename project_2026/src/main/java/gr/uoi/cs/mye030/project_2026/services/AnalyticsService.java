package gr.uoi.cs.mye030.project_2026.services;

import java.util.List;
import java.util.Map;

public interface AnalyticsService{
	
	List<Map<String, Object>> getLinechartData(String venueType, List<Integer> venueIds, Integer startYear, Integer endYear);
	List<Map<String, Object>> getVenueBarchartData(String venueType, List<Integer> venueIds, Integer startYear, Integer endYear);
	List<Map<String, Object>> getPublisherBarchartData(List<String> publishers);
	List<String> getAllPublishers();
	List<Map<String, Object>> getJournalScatterData(String xMetric, String yMetric);
	List<Map<String, Object>> getVenueScatterData(String venueType, List<Integer> venueIds);
	List<String> getAllPrimaryFoRs();
	List<Map<String, Object>> getAllBestSubjectAreas();
	List<Map<String, Object>> getCategoryEvolution(String venueType, List<String> primaryFors, List<Integer> bestSubjectIds, Integer startYear, Integer endYear);
	List<Map<String, Object>> getArticlesPerYear(String venueType, List<Integer> venueIds, Integer startYear, Integer endYear);
}