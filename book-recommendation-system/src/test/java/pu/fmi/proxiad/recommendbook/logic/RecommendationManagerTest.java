package pu.fmi.proxiad.recommendbook.logic;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import pu.fmi.proxiad.recommendbook.model.Book;
import pu.fmi.proxiad.recommendbook.model.Member;
import pu.fmi.proxiad.recommendbook.storage.LibraryStorage;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

class RecommendationManagerTest {

	private LibraryStorage storage;
	private RecommendationManager manager;

	@BeforeEach
	void setUp() {
		System.out.println("Before Each Test I want to init all my classes that will be used.");
		storage = Mockito.mock(LibraryStorage.class);
		manager = new RecommendationManager(storage);
	}

	@Test
	void testRecommendBooks() {
		// Аrrange
		Book book1 = new Book(1, "Java Basics", "Author A", "Programming");
		Book book2 = new Book(2, "Advanced Java", "Author B", "Programming");
		Book book3 = new Book(3, "History of Art", "Author C", "Art");

		Mockito.when(storage.getAllBooks()).thenReturn(List.of(book1, book2, book3));

		Member member = new Member(1, "Alice", 20);
		member.borrowBook(book1.getId()); // вече е чел Java Basics

		// Act
		List<Book> recommendations = manager.recommendBooks(member);

		// Assert
		assertThat(recommendations).containsExactly(book2);
	}

	@Test
	@DisplayName("This test is asserting that Exception is thrown when the Member is NOT valid")
	void testRecommendBooksThatThrowsExceptionWhenMemberIsNull() {

		// Arrange
		Book book1 = new Book(1, "Java Basics", "Author A", "Programming");
		Book book2 = new Book(2, "Advanced Java", "Author B", "Programming");
		Book book3 = new Book(3, "History of Art", "Author C", "Art");

		// Моквам ИСТИНСКОТО поведение на storage класа с такова ДЕФИНИРИАНО В ТЕСТ
		// МЕТОДА
		// Заместваме нещото, което getAllBooks() метода ще ни върне с нещо, което НИЕ
		// искаме
		Mockito.when(storage.getAllBooks()).thenReturn(List.of(book1, book2, book3));

		assertThrows(Exception.class, () -> {
			manager.recommendBooks(null);
		});

		assertThrowsExactly(IllegalArgumentException.class, () -> {
			manager.recommendBooks(null);
		});
	}

	@Test
	void testRecommendBookThrowsExceptionWhenMemberNameIsEmpty() {
		// Arrange
		Member ivan = new Member(1, null, 20);

		assertThrowsExactly(IllegalStateException.class, () -> {
			manager.recommendBooks(ivan);
		});
	}

	@Test
	void testThatWeWIlNotRecommendBooksWhenTheMemberDoNotBorrowAny() {

		Member ivan = new Member(1, "Ivancho", 20);
//		ivan.borrowBook(0);

		List<Book> recommendedBooks = manager.recommendBooks(ivan);

		assertThat(recommendedBooks).isNotNull().isEmpty();
	}

	@Test
	void testThatWeWIlNotRecommendBooksThereIsNoSuchOnes() {

		Mockito.when(storage.getAllBooks()).thenReturn(List.of());

		Book book1 = new Book(1, "Java Basics", "Author A", "Programming");

		Member ivan = new Member(1, "Ivancho", 20);
		ivan.borrowBook(book1.getId());

		List<Book> recommendedBooks = manager.recommendBooks(ivan);

		assertThat(recommendedBooks).isNotNull().isEmpty();
	}

	@Test
	void testRecommendBooksWhenTheMemberHaveTwoGengeBooks() {
		// Аrrange
		Book book1 = new Book(1, "Java Basics", "Author A", "Programming");
		Book book2 = new Book(2, "Advanced Java", "Author B", "Programming");
		Book book3 = new Book(3, "History of Art", "Author C", "Art");
		Book book4 = new Book(4, "History of Art Advanced", "Author D", "Art");

		Mockito.when(storage.getAllBooks()).thenReturn(List.of(book1, book2, book3, book4));

		Member member = new Member(1, "Alice", 20);
		member.borrowBook(book1.getId()); // вече е чел Java Basics
		member.borrowBook(book4.getId()); // вече е чел History of Art Advanced

		// Act
		List<Book> recommendations = manager.recommendBooks(member);

		// Да проверите, че ще препоръчаме на читателят 2 книги от съответният жанр
		// assert....
		assertThat(recommendations).containsExactly(book2, book3);
	}

	@Test
	void testRecommendBooksDoesNotContainsWhenTheMemberHaveTwoGengeBooks() {
		// Аrrange
		Book book1 = new Book(1, "Java Basics", "Author A", "Programming");
		Book book2 = new Book(2, "Advanced Java", "Author B", "Programming");
		Book book3 = new Book(3, "History of Art", "Author C", "Art");
		Book book4 = new Book(4, "History of Art Advanced", "Author D", "Art");

		Mockito.when(storage.getAllBooks()).thenReturn(List.of(book1, book2, book3, book4));

		Member member = new Member(1, "Alice", 20);
		member.borrowBook(book1.getId()); // вече е чел Java Basics
		member.borrowBook(book4.getId()); // вече е чел History of Art Advanced

		// Act
		List<Book> recommendations = manager.recommendBooks(member);

		// Да проверите, че ще препоръчаме на читателят 2 книги от съответният жанр
		assertThat(recommendations).doesNotContain(book1, book4);
	}

	@ParameterizedTest
	@MethodSource("recommendBooksData")
	void testRecommendBooks(List<Book> allBooks, List<Integer> readBooks, List<Book> expected) {

		LibraryStorage storage = Mockito.mock(LibraryStorage.class);
		Mockito.when(storage.getAllBooks()).thenReturn(allBooks);

		Member member = new Member(1, "Alice", 20);
		readBooks.forEach(member::borrowBook);

		RecommendationManager manager = new RecommendationManager(storage);
		List<Book> result = manager.recommendBooks(member);

		assertThat(result).containsExactlyElementsOf(expected);
	}

	static Stream<Arguments> recommendBooksData() {
		Book book1 = new Book(1, "Java Basics", "Author A", "Programming");
		Book book2 = new Book(2, "Advanced Java", "Author B", "Programming");
		Book book3 = new Book(3, "History of Art", "Author C", "Art");
		Book book4 = new Book(4, "Modern Art", "Author D", "Art");

		return Stream.of(
				// Прочел е книга 1 -> очаква се книга 2
				Arguments.of(List.of(book1, book2, book3), List.of(1), List.of(book2)),

				// Не е прочел никакви книги -> очаква се празен списък
				Arguments.of(List.of(book1, book2, book3), List.of(), List.of()),

				// Прочел е всички книги -> очаква се празен списък
				Arguments.of(List.of(book1, book2, book3), List.of(1, 2, 3), List.of()),

				// Няма книги в библиотеката -> очаква се празен списък
				Arguments.of(List.of(), List.of(1), List.of()),

				// Прочел е книга 3 (Art) -> очаква се книга 4 (Art)
				Arguments.of(List.of(book1, book2, book3, book4), List.of(3), List.of(book4)));
	}

	@AfterEach
	void teardown() {
		System.out.println("After Each Test I want to set manager to null");
		manager = null;
	}
}
