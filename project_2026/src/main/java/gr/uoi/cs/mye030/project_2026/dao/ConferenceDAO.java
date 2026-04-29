package gr.uoi.cs.mye030.project_2026.dao;

import gr.uoi.cs.mye030.project_2026.model.Conference;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;

@Repository 
public class ConferenceDAO {

    private final JdbcTemplate jdbcTemplate;

    public ConferenceDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Conference> findAll() {
        String sql = "SELECT * FROM Conference ORDER BY acronym ASC";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Conference.class));
    }

    public Conference findById(int id) {
        String sql = "SELECT * FROM Conference WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Conference.class), id);
    }
    
    public Map<String, Object> getConferenceOverallStats(int confId, Integer startYear, Integer endYear) {
        StringBuilder sql = new StringBuilder(
            "SELECT " +
            "COUNT(DISTINCT a.id) AS total_articles, " +
            "COUNT(DISTINCT aa.author_id) AS distinct_authors, " +
            "COUNT(aa.author_id) AS total_authors, " + 
            "MIN(a.year) AS first_year, " + 
            "MAX(a.year) AS last_year " +
            "FROM Article a " +
            "LEFT JOIN Article_Author aa ON a.id = aa.article_id " +
            "WHERE a.booktitle_id = ? "
        );

        List<Object> params = new ArrayList<>();
        params.add(confId);

        if (startYear != null) {
            sql.append("AND a.year >= ? ");
            params.add(startYear);
        }
        if (endYear != null) {
            sql.append("AND a.year <= ? ");
            params.add(endYear);
        }

        return jdbcTemplate.queryForMap(sql.toString(), params.toArray());
    }
    
    public List<java.util.Map<String, Object>> getConferenceYearlyStats(int confId, Integer startYear, Integer endYear) {
        StringBuilder sql = new StringBuilder(
            "SELECT " +
            "a.year AS pub_year, " +
            "COUNT(DISTINCT a.id) AS articles_this_year, " +
            "COUNT(DISTINCT aa.author_id) AS distinct_authors_this_year, " +
            "COUNT(aa.author_id) AS total_authors_this_year " +
            "FROM Article a " +
            "LEFT JOIN Article_Author aa ON a.id = aa.article_id " +
            "WHERE a.booktitle_id = ? "
        );

        List<Object> params = new ArrayList<>();
        params.add(confId);

        if (startYear != null) {
            sql.append("AND a.year >= ? ");
            params.add(startYear);
        }
        if (endYear != null) {
            sql.append("AND a.year <= ? ");
            params.add(endYear);
        }

        sql.append("GROUP BY a.year ORDER BY a.year ASC");

        return jdbcTemplate.queryForList(sql.toString(), params.toArray());
    }
}