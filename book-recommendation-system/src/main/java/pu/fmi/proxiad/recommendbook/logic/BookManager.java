package pu.fmi.proxiad.recommendbook.logic;

import java.util.List;

import pu.fmi.proxiad.recommendbook.model.Book;
import pu.fmi.proxiad.recommendbook.storage.LibraryStorage;

public class BookManager {

	private final LibraryStorage libraryStorage;

	public BookManager(LibraryStorage repository) {
		this.libraryStorage = repository;
	}

	public void addBook(Book book) {
		if (book == null) {
			throw new IllegalArgumentException("Book cannot be null");
		}

		if (book.getTitle() == null || book.getTitle().isBlank()) {
			throw new IllegalArgumentException("Book title cannot be empty");
		}

		if (libraryStorage.getAllBooks().stream().anyMatch(b -> b.getId() == book.getId())) {
			throw new IllegalStateException("Book already exists");
		}

		libraryStorage.saveBook(book);
	}

	public List<Book> getAllBooks() {
		return libraryStorage.getAllBooks();
	}
}
