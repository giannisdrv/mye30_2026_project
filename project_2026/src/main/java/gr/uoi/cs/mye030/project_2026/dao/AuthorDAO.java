package gr.uoi.cs.mye030.project_2026.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class AuthorDAO {

    private final JdbcTemplate jdbcTemplate;

    public AuthorDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Map<String, Object> getAuthorStats(String authorName) {
        String sql = 
            "SELECT " +
            "MIN(a.year) AS first_year, " +
            "MAX(a.year) AS last_year, " +
            "COUNT(DISTINCT a.id) AS total_articles " +
            "FROM Article a " +
            "JOIN Article_Author aa ON a.id = aa.article_id " +
            "JOIN Author au ON aa.author_id = au.id " +
            "WHERE au.name = ? AND a.year IS NOT NULL";
            
        Map<String, Object> results = jdbcTemplate.queryForMap(sql, authorName);
        if ( ((Number) results.get("total_articles")).intValue() == 0) {
            return null; 
        }
        return results;
    }

    // we return a list of map<string,object> since we have a groupby for the years
    public List<Map<String, Object>> getAuthorYearlyStats(String authorName) {
        String sql = 
            "SELECT " +
            "a.year AS pub_year, " +
            "COUNT(DISTINCT a.id) AS articles_count " +
            "FROM Article a " +
            "JOIN Article_Author aa ON a.id = aa.article_id " +
            "JOIN Author au ON aa.author_id = au.id " +
            "WHERE au.name = ? AND a.year IS NOT NULL " +
            "GROUP BY a.year " +
            "ORDER BY a.year ASC";
            
        return jdbcTemplate.queryForList(sql, authorName);
    }
}