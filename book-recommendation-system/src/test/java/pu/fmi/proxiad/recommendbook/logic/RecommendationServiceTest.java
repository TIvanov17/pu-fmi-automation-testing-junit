package pu.fmi.proxiad.recommendbook.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;

import pu.fmi.proxiad.recommendbook.model.Book;
import pu.fmi.proxiad.recommendbook.model.Member;
import pu.fmi.proxiad.recommendbook.storage.LibraryStorage;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

class RecommendationServiceTest {

	@Test
	void testRecommendBooks() {
		LibraryStorage storage = Mockito.mock(LibraryStorage.class);
		// Аrrange
		Book book1 = new Book(1, "Java Basics", "Author A", "Programming");
		Book book2 = new Book(2, "Advanced Java", "Author B", "Programming");
		Book book3 = new Book(3, "History of Art", "Author C", "Art");

		Mockito.when(storage.getAllBooks()).thenReturn(List.of(book1, book2, book3));

		Member member = new Member(1, "Alice", 20);
		member.borrowBook(book1.getId()); // вече е чел Java Basics

		// Act
		RecommendationManager manager = new RecommendationManager(storage);
		List<Book> recommendations = manager.recommendBooks(member);

		// Assert
		assertThat(recommendations).containsExactly(book2);
	}

}
