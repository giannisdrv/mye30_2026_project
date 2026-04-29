package gr.uoi.cs.mye030.project_2026.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
public class AnalyticsDAO {

    private final JdbcTemplate jdbcTemplate;

    public AnalyticsDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    public List<String> getAllPublishers() {
        return jdbcTemplate.queryForList("SELECT DISTINCT publisher FROM Journal WHERE publisher IS NOT NULL AND publisher != '' ORDER BY publisher ASC", String.class);
    }
    
    public List<String> getAllPrimaryFoRs() {
        return jdbcTemplate.queryForList("SELECT DISTINCT primaryFOR FROM Conference WHERE primaryFOR IS NOT NULL AND primaryFOR != '' ORDER BY primaryFOR ASC", String.class);
    }
    
    public List<Map<String, Object>> getAllBestSubjectAreas() {
        return jdbcTemplate.queryForList("SELECT id, name FROM BestSubjectArea ORDER BY name ASC");
    }

    public List<Map<String, Object>> getArticlesPerYear(String venueType, List<Integer> venueIds, Integer startYear, Integer endYear) {
        if (venueIds == null || venueIds.isEmpty()) {
            return new ArrayList<>();
        }

        String inSql = String.join(",", Collections.nCopies(venueIds.size(), "?"));
        List<Object> params = new ArrayList<>(venueIds);
        StringBuilder sql = new StringBuilder();
        
        if ("CONF".equals(venueType)) {
            sql.append("SELECT c.acronym AS venue_name, a.year AS pub_year, COUNT(a.id) AS metric_count ");
            sql.append("FROM Article a JOIN Conference c ON a.booktitle_id = c.id ");
            sql.append("WHERE c.id IN (").append(inSql).append(") AND a.year IS NOT NULL ");
        } else {
            sql.append("SELECT j.title AS venue_name, a.year AS pub_year, COUNT(a.id) AS metric_count ");
            sql.append("FROM Article a JOIN Journal j ON a.journal_id = j.id ");
            sql.append("WHERE j.id IN (").append(inSql).append(") AND a.year IS NOT NULL ");
        }

        if (startYear != null) {
            sql.append("AND a.year >= ? ");
            params.add(startYear);
        }
        if (endYear != null) {
            sql.append("AND a.year <= ? ");
            params.add(endYear);
        }

        sql.append("GROUP BY venue_name, pub_year ORDER BY pub_year ASC");

        return jdbcTemplate.queryForList(sql.toString(), params.toArray());
    }
    
    public List<Map<String, Object>> getCategoryEvolution(String venueType, List<String> primaryFors, List<Integer> bestSubjectIds, Integer startYear, Integer endYear) {
        List<Object> params = new ArrayList<>();
        StringBuilder sql = new StringBuilder();

        if ("CONF".equals(venueType)) {
            if (primaryFors == null || primaryFors.isEmpty()) return new ArrayList<>();
            String inSql = String.join(",", Collections.nCopies(primaryFors.size(), "?"));
            params.addAll(primaryFors);

            sql.append("SELECT c.primaryFOR AS venue_name, a.year AS pub_year, COUNT(DISTINCT c.id) AS metric_count ");
            sql.append("FROM Article a JOIN Conference c ON a.booktitle_id = c.id ");
            sql.append("WHERE c.primaryFOR IN (").append(inSql).append(") AND a.year IS NOT NULL ");
        } else {
            if (bestSubjectIds == null || bestSubjectIds.isEmpty()) return new ArrayList<>();
            String inSql = String.join(",", Collections.nCopies(bestSubjectIds.size(), "?"));
            params.addAll(bestSubjectIds);

            sql.append("SELECT b.name AS venue_name, a.year AS pub_year, COUNT(DISTINCT j.id) AS metric_count ");
            sql.append("FROM Article a JOIN Journal j ON a.journal_id = j.id ");
            sql.append("JOIN BestSubjectArea b ON j.bestSubjectArea_id = b.id ");
            sql.append("WHERE b.id IN (").append(inSql).append(") AND a.year IS NOT NULL ");
        }

        if (startYear != null) {
            sql.append("AND a.year >= ? ");
            params.add(startYear);
        }
        if (endYear != null) {
            sql.append("AND a.year <= ? ");
            params.add(endYear);
        }

        sql.append("GROUP BY venue_name, pub_year ORDER BY pub_year ASC");
        return jdbcTemplate.queryForList(sql.toString(), params.toArray());
    }


    public List<Map<String, Object>> getVenueMetrics(String venueType, List<Integer> venueIds, Integer startYear, Integer endYear) {
        if (venueIds == null || venueIds.isEmpty()) return new ArrayList<>();

        String inSql = String.join(",", Collections.nCopies(venueIds.size(), "?"));
        List<Object> params = new ArrayList<>(venueIds);
        StringBuilder sql = new StringBuilder();

        if ("CONF".equals(venueType)) {
            sql.append("SELECT c.acronym AS venue_name, ");
            sql.append("COUNT(DISTINCT a.id) AS total_articles, ");
            sql.append("COUNT(DISTINCT a.year) AS active_years, "); 
            sql.append("COUNT(aa.author_id) AS total_authors ");    
            sql.append("FROM Conference c ");
            sql.append("JOIN Article a ON c.id = a.booktitle_id ");
            sql.append("LEFT JOIN Article_Author aa ON a.id = aa.article_id ");
            sql.append("WHERE c.id IN (").append(inSql).append(") ");
        } else {
            sql.append("SELECT j.title AS venue_name, ");
            sql.append("COUNT(DISTINCT a.id) AS total_articles, ");
            sql.append("COUNT(DISTINCT a.year) AS active_years, ");
            sql.append("COUNT(aa.author_id) AS total_authors ");
            sql.append("FROM Journal j ");
            sql.append("JOIN Article a ON j.id = a.journal_id ");
            sql.append("LEFT JOIN Article_Author aa ON a.id = aa.article_id ");
            sql.append("WHERE j.id IN (").append(inSql).append(") ");
        }

        if (startYear != null) {
            sql.append("AND a.year >= ? ");
            params.add(startYear);
        }
        if (endYear != null) {
            sql.append("AND a.year <= ? ");
            params.add(endYear);
        }

        sql.append("GROUP BY venue_name ORDER BY total_articles DESC");
        return jdbcTemplate.queryForList(sql.toString(), params.toArray());
    }
    
    public List<Map<String, Object>> getPublisherMetrics(List<String> publishers) {
        if (publishers == null || publishers.isEmpty()) return new ArrayList<>();

        String inSql = String.join(",", Collections.nCopies(publishers.size(), "?"));
        
        String sql = 
            "SELECT publisher AS publisher_name, " +
            "COUNT(id) AS total_journals, " +
            "SUM(CASE WHEN bestquartile = 'Q1' THEN 1 ELSE 0 END) AS q1_count, " +
            "SUM(CASE WHEN bestquartile = 'Q2' THEN 1 ELSE 0 END) AS q2_count, " +
            "SUM(CASE WHEN bestquartile = 'Q3' THEN 1 ELSE 0 END) AS q3_count, " +
            "SUM(CASE WHEN bestquartile = 'Q4' THEN 1 ELSE 0 END) AS q4_count " +
            "FROM Journal " +
            "WHERE publisher IN (" + inSql + ") " +
            "GROUP BY publisher";

        return jdbcTemplate.queryForList(sql, publishers.toArray());
    }
    
    public List<Map<String, Object>> getJournalScatterData(String xMetric, String yMetric) {
        List<String> allowedMetrics = List.of(
            "totalrefs", "total_cites_3y", "citable_docs_3y", "cites_doc_2y", "refs_doc"
        );

        if (!allowedMetrics.contains(xMetric) || !allowedMetrics.contains(yMetric)) {
            return new ArrayList<>(); 
        }

        String sql = 
            "SELECT title AS venue_name, " + xMetric + " AS x_val, " + yMetric + " AS y_val " +
            "FROM Journal " +
            "WHERE " + xMetric + " IS NOT NULL AND " + yMetric + " IS NOT NULL " +
            "ORDER BY " + xMetric + " DESC LIMIT 2000";

        return jdbcTemplate.queryForList(sql);
    }
    
    public List<Map<String, Object>> getVenueScatterData(String venueType, List<Integer> venueIds) {
        if (venueIds == null || venueIds.isEmpty()) return new ArrayList<>();

        String inSql = String.join(",", Collections.nCopies(venueIds.size(), "?"));
        List<Object> params = new ArrayList<>(venueIds);
        StringBuilder sql = new StringBuilder();

        if ("CONF".equals(venueType)) {
            sql.append("SELECT c.acronym AS venue_name, a.year AS pub_year, ");
            sql.append("COUNT(DISTINCT a.id) AS total_articles, ");
            sql.append("COUNT(aa.author_id) AS total_authors "); 
            sql.append("FROM Conference c ");
            sql.append("JOIN Article a ON c.id = a.booktitle_id ");
            sql.append("LEFT JOIN Article_Author aa ON a.id = aa.article_id ");
            sql.append("WHERE c.id IN (").append(inSql).append(") AND a.year IS NOT NULL ");
            sql.append("GROUP BY c.acronym, a.year");
        } else {
            sql.append("SELECT j.title AS venue_name, a.year AS pub_year, ");
            sql.append("COUNT(DISTINCT a.id) AS total_articles, ");
            sql.append("COUNT(aa.author_id) AS total_authors "); 
            sql.append("FROM Journal j ");
            sql.append("JOIN Article a ON j.id = a.journal_id ");
            sql.append("LEFT JOIN Article_Author aa ON a.id = aa.article_id ");
            sql.append("WHERE j.id IN (").append(inSql).append(") AND a.year IS NOT NULL ");
            sql.append("GROUP BY j.title, a.year");
        }

        return jdbcTemplate.queryForList(sql.toString(), params.toArray());
    }
}