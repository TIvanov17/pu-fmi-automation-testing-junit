package pu.fmi.proxiad.recommendbook.model;

import java.util.ArrayList;
import java.util.List;

public class Member {
	private int id;
	private String name;
	private int age;
	private List<Integer> borrowedBookIds = new ArrayList<>();

	public Member(int id, String name, int age) {
		this.id = id;
		this.name = name;
		this.age = age;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	public List<Integer> getBorrowedBookIds() {
		return borrowedBookIds;
	}

	public void borrowBook(int bookId) {
		borrowedBookIds.add(bookId);
	}
}
