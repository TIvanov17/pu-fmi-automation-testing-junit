package pu.fmi.proxiad.recommendbook.logic;

import java.util.List;
import java.util.stream.Collectors;

import pu.fmi.proxiad.recommendbook.model.Book;
import pu.fmi.proxiad.recommendbook.model.Member;
import pu.fmi.proxiad.recommendbook.storage.LibraryStorage;

public class RecommendationManager {

	private final LibraryStorage libraryStorage;

	public RecommendationManager(LibraryStorage repository) {
		this.libraryStorage = repository;
	}

	// Прост recommendation: препоръчва книги със същия жанр, които читателят е заемал
	public List<Book> recommendBooks(Member member) {

		if (member == null) {
			throw new IllegalArgumentException("Member cannot be null");
		}
		
		if(member.getName() == null || member.getName().isBlank()) {
			throw new IllegalStateException("Member's name cannot be empty");
		}

		if (member.getBorrowedBookIds() == null || member.getBorrowedBookIds().isEmpty()) {
			return List.of();
		}

		List<Integer> borrowedBookIds = member.getBorrowedBookIds();

		if (borrowedBookIds.isEmpty()) {
			return List.of();
		}

		List<Book> allBooks = libraryStorage.getAllBooks();

		if (allBooks == null || allBooks.isEmpty()) {
			return List.of();
		}

		List<String> borrowedGenres = allBooks.stream().filter(b -> borrowedBookIds.contains(b.getId()))
				.map(Book::getGenre).distinct().toList();

		return allBooks.stream()
				.filter(b -> !borrowedBookIds.contains(b.getId()) && borrowedGenres.contains(b.getGenre())).toList();
	}
}
