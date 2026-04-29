package gr.uoi.cs.mye030.project_2026.model;


public class BestSubjectArea{
	private int id;
	private String name;
	
	public BestSubjectArea() {
		
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BestSubjectArea(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	
}