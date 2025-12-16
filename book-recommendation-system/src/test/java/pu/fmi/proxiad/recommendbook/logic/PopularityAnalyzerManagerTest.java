package pu.fmi.proxiad.recommendbook.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pu.fmi.proxiad.recommendbook.model.Book;
import pu.fmi.proxiad.recommendbook.model.Member;
import pu.fmi.proxiad.recommendbook.storage.LibraryStorage;

import static org.assertj.core.api.Assertions.assertThat;

class PopularityAnalyzerManagerTest {

	private LibraryStorage storage;
	private PopularityAnalyzerManager popularityAnalyzerManager;
	
	@BeforeEach
	void setUp() {
	}
	
	//	TODO:	 Задача 1: Основен тест
	//	Описание:
	//		Имаме няколко книги и няколко членове, всеки е заемал различни книги.
	// 	Задание: 
	// 		Напишете тест, който проверява дали методът връща книгите, 
	// 		сортирани по брой заемания (от най-популярната към най-малко заеманата).
	@Test
	void testPredictPopularBooks() {
		
	    Book book1 = new Book(1, "Java Basics", "Author A", "Programming");
	    Book book2 = new Book(2, "Advanced Java", "Author B", "Programming");
	    Book book3 = new Book(3, "History of Art", "Author C", "Art");

	    Member member1 = new Member(1, "Ivan", 20);
	    member1.borrowBook(1);
	    member1.borrowBook(2);

	    Member member2 = new Member(2, "Stoyan", 25);
	    member2.borrowBook(2);
	    member2.borrowBook(3);
	    
	    // TODO - Аrrange: 1. Създайте Mockito mock за LibraryStorage и върнете горните списъци
	    // TODO - Аrrange: 2. Създайте PopularityAnalyzerManager с mock обекта

	    // TODO - Act: 3. Извикайте predictPopularBooks() и запазете резултата
	    
	    // TODO- Assert: 4. Проверете, че книгите са сортирани по брой заемания
	}
	
	// TODO: Задача 2: Празна библиотека
	// Описание:
	//		Няма налични книги в системата.
	// Задание:
	//		Напишете тест, който проверява, че методът predictPopularBooks() връща празен списък.
	@Test
	void testPredictPopularBooksNoBooksInTheLibrary() {
		 // TODO: Създайте mock за LibraryStorage и върнете празни списъци за книги и членове

	    // TODO: Създайте PopularityAnalyzerManager с mock обекта

	    // TODO: Извикайте predictPopularBooks() и проверете, че резултатът е празен
	}
	
	// TODO: Задача 3: Читатели без заемани книги
	// Описание:
	//		Има книги, но членовете не са заемали нищо.
	// Задание:
	//		Напишете тест, който проверява, че методът връща всички книги в оригиналния ред.
	@Test
	void testPredictPopularBooksNoBorrowedBooksByTheMembers() {

		Book book1 = new Book(1, "Java Basics", "Author A", "Programming");
	    Book book2 = new Book(2, "Advanced Java", "Author B", "Programming");

	    Member member1 = new Member(1, "Ivan", 20);
	    Member member2 = new Member(2, "Stoyan", 25);

	    // TODO: Създайте mock за LibraryStorage и върнете горните списъци

	    // TODO: Създайте PopularityAnalyzerManager с mock обекта

	    // TODO: Извикайте predictPopularBooks() и проверете, че резултатът съдържа всички книги
	}
	
	// TODO: Задача 4: Равен брой заемания
	// Описание:
	//		Няколко книги имат еднакъв брой заемания.
	// Задание:
	//		Напишете тест, който проверява, че методът връща всички книги,
	//		като тези с равен брой могат да бъдат в произволен ред.
	@Test
	void testPredictPopularBooksEqualBorrowCount() {
		
	    Book book1 = new Book(1, "Book A", "Author A", "Programming");
	    Book book2 = new Book(2, "Book B", "Author B", "Programming");

	    Member member1 = new Member(1, "Ivan", 20);
	    member1.borrowBook(1);

	    Member member2 = new Member(2, "Stoyan", 25);
	    member2.borrowBook(2);

	    // TODO: Създайте mock за LibraryStorage и върнете горните списъци

	    // TODO: Създайте PopularityAnalyzerManager с mock обекта

	    // TODO: Извикайте predictPopularBooks() и проверете, че резултатът съдържа всички книги
	    // assertThat(result).containsExactlyInAnyOrder(book1, book2);
	}

}
