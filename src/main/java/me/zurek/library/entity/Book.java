package me.zurek.library.entity;

public class Book {

	private Integer ID; // ArrayList accepts only int indices (long is not possible). I assumed that
						// there wouldn't be over 2 billion books. In other case, I should choose other
						// container for books. Moreover, if I would have so much books, I would use a
						// database, not the Java collection ;)
	private String title;
	private String author;
	private Integer year;
	private Person lentTo;

	public Book(String title, String author, Integer year) {
		super();
		this.title = title;
		this.author = author;
		this.year = year;
	}

	public Integer getID() {
		return ID;
	}

	public void setID(Integer newID) {
		ID = newID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Person getLentTo() {
		return lentTo;
	}

	public void setLentTo(Person lentTo) {
		this.lentTo = lentTo;
	}
}
