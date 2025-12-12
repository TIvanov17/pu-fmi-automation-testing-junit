package pu.fmi.proxiad.recommendbook.logic;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import pu.fmi.proxiad.recommendbook.model.Member;
import pu.fmi.proxiad.recommendbook.storage.LibraryStorage;

class MemberManagerTest {

	@Mock
	private LibraryStorage libraryStorage;

	private MemberManager memberManager;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		memberManager = new MemberManager(libraryStorage);
	}

	@Test
	void addMemberShouldCallToStorage() {
		Member member = new Member(1, "John", 14);

		memberManager.addMember(member);

		verify(libraryStorage, times(1)).saveMember(member);
	}

	@Test
	void findMemberByIdShouldReturnMemberWhenExists() {
		Member member = new Member(1, "John", 14);
		when(libraryStorage.findMemberById(1)).thenReturn(member);

		Member result = memberManager.findMemberById(1);

		assertThat(result).isNotNull();
		verify(libraryStorage, times(1)).findMemberById(1);
	}

}
