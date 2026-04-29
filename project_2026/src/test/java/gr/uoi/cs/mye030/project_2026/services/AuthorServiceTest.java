package gr.uoi.cs.mye030.project_2026.services;

import gr.uoi.cs.mye030.project_2026.dao.AuthorDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class AuthorServiceTest {

    private AuthorDAO authorDAO;
    private AuthorService authorService;

    @BeforeEach
    void setUp() {
        authorDAO = Mockito.mock(AuthorDAO.class);
        authorService = new AuthorServiceImpl(authorDAO);
    }

    @Test
    void testAverageArticlesCalculation() {
        Map<String, Object> mockStats = new HashMap<>();
        mockStats.put("first_year", 2010);
        mockStats.put("last_year", 2012); 
        mockStats.put("total_articles", 6);

        when(authorDAO.getAuthorStats("Test Author")).thenReturn(mockStats);

        Map<String, Object> result = authorService.getAuthorProfile("Test Author");

        assertNotNull(result);
        Map<String, Object> stats = (Map<String, Object>) result.get("stats");
        
        assertEquals("2.00", stats.get("avg_articles_per_year"));
        assertEquals(3, stats.get("active_years"));
    }
}