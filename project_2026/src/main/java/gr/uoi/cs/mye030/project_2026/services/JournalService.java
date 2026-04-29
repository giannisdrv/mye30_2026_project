package gr.uoi.cs.mye030.project_2026.services;

import java.util.List;
import java.util.Map;

import gr.uoi.cs.mye030.project_2026.model.Journal;

public interface JournalService{
	List<Journal> getAllJournals();
	Map<String, Object> getJournalProfile(int journalId, Integer startYear, Integer endYear);
	
}