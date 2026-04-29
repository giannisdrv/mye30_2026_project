package gr.uoi.cs.mye030.project_2026.services;

import java.util.List;
import java.util.Map;

public interface YearService{
	List<Integer> getAvailableYears();
	Map<String, Object> getYearProfile(int year, Integer confId, Integer journalId, String authorName);
	
}