package gr.uoi.cs.mye030.project_2026.services;

import java.util.List;
import java.util.Map;

import gr.uoi.cs.mye030.project_2026.model.Conference;

public interface ConferenceService{
	List<Conference> getAllConferences();
	Map<String, Object> getConferenceProfile(int confId, Integer startYear, Integer endYear);
}