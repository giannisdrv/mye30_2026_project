package gr.uoi.cs.mye030.project_2026.services;
import java.util.Map;

public interface AuthorService{
	
	Map<String, Object> getAuthorProfile(String authorName);
}