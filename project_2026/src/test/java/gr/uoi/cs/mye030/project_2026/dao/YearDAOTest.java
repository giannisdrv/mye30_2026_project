package gr.uoi.cs.mye030.project_2026.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest 
class YearDAOTest {

    @Autowired
    private YearDAO yearDAO;

    @Test
    void testGetAvailableYears() {
        List<Integer> years = yearDAO.getAvailableYears();
        assertNotNull(years);
        assertFalse(years.isEmpty(), "Η βάση δεν πρέπει να είναι άδεια!");
    }

    @Test
    void testGetYearStats() {
        int testYear = 2010;
        Map<String, Object> stats = yearDAO.getYearStats(testYear);
        
        assertNotNull(stats);
        assertTrue(stats.containsKey("total_articles"));
    }
}