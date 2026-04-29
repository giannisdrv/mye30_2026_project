package gr.uoi.cs.mye030.project_2026.model;

public class Journal{
	private int id;
	private Integer ranking;
	private String title;
	private String oa;
	private String country;
	private Double sjr;
	private Double citescore;
	private Integer hindex;
	private String bestquartile;
	private Integer bestSubjectArea_id;
	private Integer totalrefs;
	private Integer total_cites_3y;
	private Integer citable_docs_3y;
	private Integer cites_doc_2y;
	private Double refs_doc;
	private String publisher;
	
	public Journal() {
		
	}

	public Journal(int id, Integer ranking, String title, String oa, String country, Double sjr, Double citescore,
			Integer hindex, String bestquartile, Integer bestSubjectArea_id, Integer totalrefs, Integer total_cites_3y,
			Integer citable_docs_3y, Integer cites_doc_2y, Double refs_doc, String publisher) {
		super();
		this.id = id;
		this.ranking = ranking;
		this.title = title;
		this.oa = oa;
		this.country = country;
		this.sjr = sjr;
		this.citescore = citescore;
		this.hindex = hindex;
		this.bestquartile = bestquartile;
		this.bestSubjectArea_id = bestSubjectArea_id;
		this.totalrefs = totalrefs;
		this.total_cites_3y = total_cites_3y;
		this.citable_docs_3y = citable_docs_3y;
		this.cites_doc_2y = cites_doc_2y;
		this.refs_doc = refs_doc;
		this.publisher = publisher;
	}

	public int getId() {
		return id;
	}

	public Integer getRanking() {
		return ranking;
	}

	public String getTitle() {
		return title;
	}

	public String getOa() {
		return oa;
	}

	public String getCountry() {
		return country;
	}

	public Double getSjr() {
		return sjr;
	}

	public Double getCitescore() {
		return citescore;
	}

	public Integer getHindex() {
		return hindex;
	}

	public String getBestquartile() {
		return bestquartile;
	}

	public Integer getBestSubjectArea_id() {
		return bestSubjectArea_id;
	}

	public Integer getTotalrefs() {
		return totalrefs;
	}

	public Integer getTotal_cites_3y() {
		return total_cites_3y;
	}

	public Integer getCitable_docs_3y() {
		return citable_docs_3y;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setRanking(Integer ranking) {
		this.ranking = ranking;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setOa(String oa) {
		this.oa = oa;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setSjr(Double sjr) {
		this.sjr = sjr;
	}

	public void setCitescore(Double citescore) {
		this.citescore = citescore;
	}

	public void setHindex(Integer hindex) {
		this.hindex = hindex;
	}

	public void setBestquartile(String bestquartile) {
		this.bestquartile = bestquartile;
	}

	public void setBestSubjectArea_id(Integer bestSubjectArea_id) {
		this.bestSubjectArea_id = bestSubjectArea_id;
	}

	public void setTotalrefs(Integer totalrefs) {
		this.totalrefs = totalrefs;
	}

	public void setTotal_cites_3y(Integer total_cites_3y) {
		this.total_cites_3y = total_cites_3y;
	}

	public void setCitable_docs_3y(Integer citable_docs_3y) {
		this.citable_docs_3y = citable_docs_3y;
	}

	public void setCites_doc_2y(Integer cites_doc_2y) {
		this.cites_doc_2y = cites_doc_2y;
	}

	public void setRefs_doc(Double refs_doc) {
		this.refs_doc = refs_doc;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public Integer getCites_doc_2y() {
		return cites_doc_2y;
	}

	public Double getRefs_doc() {
		return refs_doc;
	}

	public String getPublisher() {
		return publisher;
	}
	
	
}