package gr.uoi.cs.mye030.project_2026.model;

public class Conference{
	private int id;
	private String title;
	private String acronym;
	private String ranking;
	private String primaryFOR;
	
	public Conference(){
		
	}

	public Conference(int id, String title, String acronym, String ranking, String primaryFOR) {
		super();
		this.id = id;
		this.title = title;
		this.acronym = acronym;
		this.ranking = ranking;
		this.primaryFOR = primaryFOR;
	}

	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getAcronym() {
		return acronym;
	}

	public String getRanking() {
		return ranking;
	}

	public String getPrimaryFOR() {
		return primaryFOR;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setAcronym(String acronym) {
		this.acronym = acronym;
	}

	public void setRanking(String ranking) {
		this.ranking = ranking;
	}

	public void setPrimaryFOR(String primaryFOR) {
		this.primaryFOR = primaryFOR;
	}
	
	
	
}