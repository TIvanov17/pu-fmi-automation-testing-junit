package pu.fmi.proxiad.recommendbook.logic;

import java.util.HashMap;
import java.util.List;

import pu.fmi.proxiad.recommendbook.model.Book;
import pu.fmi.proxiad.recommendbook.model.Member;
import pu.fmi.proxiad.recommendbook.storage.LibraryStorage;

public class PopularityAnalyzerManager {

	private final LibraryStorage libraryStorage;

	public PopularityAnalyzerManager(LibraryStorage repository) {
		this.libraryStorage = repository;
	}

	// Прогнозира кои книги ще са популярни на база на броя на заемите
	public List<Book> predictPopularBooks() {
		List<Book> allBooks = libraryStorage.getAllBooks();
		List<Member> allMembers = libraryStorage.getAllMembers();

		// Създаваме Map с броя на заемите за всяка книга
		var borrowCount = new HashMap<Book, Integer>();
		for (Book book : allBooks) {
			int count = 0;
			for (Member member : allMembers) {
				if (member.getBorrowedBookIds().contains(book.getId())) {
					count++;
				}
			}
			borrowCount.put(book, count);
		}

		// Сортираме книгите по брой заемания (descending)
		allBooks.sort((b1, b2) -> borrowCount.get(b2) - borrowCount.get(b1));

		return allBooks;
	}
}
