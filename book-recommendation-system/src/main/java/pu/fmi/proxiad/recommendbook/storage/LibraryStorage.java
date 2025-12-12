package pu.fmi.proxiad.recommendbook.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import pu.fmi.proxiad.recommendbook.model.Book;
import pu.fmi.proxiad.recommendbook.model.Member;

public class LibraryStorage {

	private final List<Book> books = new ArrayList<>();
	private final List<Member> members = new ArrayList<>();

	public void saveBook(Book book) {
		books.add(book);
	}

	public void saveMember(Member member) {
		members.add(member);
	}

	public Book findBookById(int id) {
		return books.stream().filter(b -> b.getId() == id).findFirst().orElse(null);
	}

	public Member findMemberById(int id) {
		return members.stream().filter(m -> m.getId() == id).findFirst().orElse(null);
	}

	public List<Book> getAllBooks() {
		return new ArrayList<>(books);
	}

	public List<Member> getAllMembers() {
		return new ArrayList<>(members);
	}
}
