package pu.fmi.proxiad.recommendbook.logic;

import java.util.Optional;

import pu.fmi.proxiad.recommendbook.model.Member;
import pu.fmi.proxiad.recommendbook.storage.LibraryStorage;

public class MemberManager {

	private final LibraryStorage libraryStorage;

	public MemberManager(LibraryStorage repository) {
		this.libraryStorage = repository;
	}

	public void addMember(Member member) {
		if (member == null) {
			throw new IllegalArgumentException("Member cannot be null");
		}

		if (member.getName() == null || member.getName().isBlank()) {
			throw new IllegalArgumentException("Member name cannot be empty");
		}

		if (member.getAge() < 12) {
			throw new IllegalArgumentException("Member is too young");
		}

		if (libraryStorage.findMemberById(member.getId()) != null) {
			throw new IllegalStateException("Member already exists");
		}

		libraryStorage.saveMember(member);
	}

	public Member findMemberById(int id) {
		if (id < 0) {
			throw new IllegalArgumentException("ID cannot be negative");
		}
		return libraryStorage.findMemberById(id);
	}
}
