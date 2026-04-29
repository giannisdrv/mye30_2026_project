package gr.uoi.cs.mye030.project_2026.model;

public class Article{
	private int id;
	private Integer booktitle_id;
	private Integer journal_id;
	private String key_id;
	private String mdate;
	private String pages;
	private String title;
	private String url;
	public void setId(int id) {
		this.id = id;
	}

	public void setBooktitle_id(Integer booktitle_id) {
		this.booktitle_id = booktitle_id;
	}

	public void setJournal_id(Integer journal_id) {
		this.journal_id = journal_id;
	}

	public void setKey_id(String key_id) {
		this.key_id = key_id;
	}

	public void setMdate(String mdate) {
		this.mdate = mdate;
	}

	public void setPages(String pages) {
		this.pages = pages;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setYear(int year) {
		this.year = year;
	}

	private int year;
	
	public Article() {
    }
	
	public Article(int id, Integer booktitle_id, Integer journal_id, String key_id, String mdate, String pages, String title,
			String url, int year) {
		super();
		this.id = id;
		this.booktitle_id = booktitle_id;
		this.journal_id = journal_id;
		this.key_id = key_id;
		this.mdate = mdate;
		this.pages = pages;
		this.title = title;
		this.url = url;
		this.year = year;
	}

	public int getId() {
		return id;
	}

	public Integer getConference_id() {
		return booktitle_id;
	}

	public Integer getJournal_id() {
		return journal_id;
	}

	public String getKey() {
		return key_id;
	}

	public String getMdate() {
		return mdate;
	}

	public String getPages() {
		return pages;
	}

	public String getTitle() {
		return title;
	}

	public String getUrl() {
		return url;
	}

	public int getYear() {
		return year;
	}

	
}