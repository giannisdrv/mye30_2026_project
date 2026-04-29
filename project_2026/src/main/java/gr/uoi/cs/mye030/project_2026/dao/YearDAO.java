package gr.uoi.cs.mye030.project_2026.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class YearDAO {

    private final JdbcTemplate jdbcTemplate;

    public YearDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Integer> getAvailableYears() {
        String sql = "SELECT DISTINCT year FROM Article WHERE year IS NOT NULL ORDER BY year DESC";
        return jdbcTemplate.queryForList(sql, Integer.class);
    }

    public Map<String, Object> getYearStats(int year) {
        String sql = 
            "SELECT " +
            "COUNT(DISTINCT a.id) AS total_articles, " +
            "COUNT(DISTINCT a.journal_id) AS distinct_journals, " +
            "COUNT(DISTINCT a.booktitle_id) AS distinct_conferences, " +
            "COUNT(DISTINCT aa.author_id) AS distinct_authors, " +
            "COUNT(aa.author_id) AS total_authors " +
            "FROM Article a " +
            "LEFT JOIN Article_Author aa ON a.id = aa.article_id " +
            "WHERE a.year = ?";
            
        return jdbcTemplate.queryForMap(sql, year);
    }

    public List<Map<String, Object>> getPublicationsByYearWithFilters(int year, Integer confId, Integer journalId, String authorName) {
        
        StringBuilder sql = new StringBuilder(
            "SELECT a.title AS article_title, " +
            "c.acronym AS conference_name, " +
            "j.title AS journal_name, " +
            "(SELECT GROUP_CONCAT(au.name SEPARATOR ', ') " +
            " FROM Article_Author aa2 JOIN Author au ON aa2.author_id = au.id " +
            " WHERE aa2.article_id = a.id) AS authors_list " +
            "FROM Article a " +
            "LEFT JOIN Conference c ON a.booktitle_id = c.id " +
            "LEFT JOIN Journal j ON a.journal_id = j.id " +
            "WHERE a.year = ? "
        );

        List<Object> params = new ArrayList<>();
        params.add(year);

        if (confId != null) {
            sql.append("AND a.booktitle_id = ? ");
            params.add(confId);
        }

        if (journalId != null) {
            sql.append("AND a.journal_id = ? ");
            params.add(journalId);
        }

        if (authorName != null && !authorName.trim().isEmpty()) {
            sql.append("AND a.id IN (" +
                       "  SELECT aa3.article_id FROM Article_Author aa3 " +
                       "  JOIN Author au3 ON aa3.author_id = au3.id " +
                       "  WHERE au3.name = ?" +
                       ") ");
            params.add(authorName.trim()); 
        }

        sql.append("ORDER BY a.title ASC LIMIT 200");

        return jdbcTemplate.queryForList(sql.toString(), params.toArray());
    }
}