package gr.uoi.cs.mye030.project_2026.services;

import gr.uoi.cs.mye030.project_2026.dao.YearDAO;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class YearServiceImpl implements YearService{

    private final YearDAO yearDAO;

    public YearServiceImpl(YearDAO yearDAO) {
        this.yearDAO = yearDAO;
    }
    
    @Override
    public List<Integer> getAvailableYears() {
        return yearDAO.getAvailableYears();
    }
    
    @Override
    public Map<String, Object> getYearProfile(int year, Integer confId, Integer journalId, String authorName) {
        Map<String, Object> profileData = new HashMap<>();
        
        profileData.put("stats", yearDAO.getYearStats(year));
        profileData.put("publications", yearDAO.getPublicationsByYearWithFilters(year, confId, journalId, authorName));
        
        return profileData;
    }
}