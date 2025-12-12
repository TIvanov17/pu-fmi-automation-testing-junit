package pu.fmi.proxiad.recommendbook.model;

public class Book {
	private int id;
	private String title;
	private String author;
	private String genre;

	public Book(int id, String title, String author, String genre) {
		this.id = id;
		this.title = title;
		this.author = author;
		this.genre = genre;
	}

	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}

	public String getGenre() {
		return genre;
	}

}
