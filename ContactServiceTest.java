package org.snhu.cs320.contact;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.snhu.cs320.exceptions.ValidationException;

public class ContactServiceTest {
	
	@BeforeEach
	void init() {
		ContactService.CONTACT_DATABASE.clear();
	}
	
	@Test
	void addSuccess() {
		Contact contact = new Contact("12345", "First", "Last", "5553334444", "1234 Loblolly Lane");
		assertThat(ContactService.add(contact)).isTrue();
		assertThat(ContactService.CONTACT_DATABASE).containsEntry("12345", contact);
	}
	
	@Test
	void addExistingId() {
		Contact contact = new Contact("12345", "First", "Last", "5553334444", "1234 Loblolly Lane");
		assertThat(ContactService.add(contact)).isTrue();
		assertThat(ContactService.add(contact)).isFalse();
	}
	
	@Test
	void deleteSuccess() {
		Contact contact = new Contact("12345", "First", "Last", "5553334444", "1234 Loblolly Lane");
		assertThat(ContactService.add(contact)).isTrue();
		assertThat(ContactService.delete("12345")).isTrue();
		assertThat(ContactService.CONTACT_DATABASE).doesNotContainKey("12345");
	}
	
	@Test
	void deleteNonExisting() {
		assertThat(ContactService.delete("12345")).isFalse();
	}
	
	@Test
	void updateSuccess() {
		Contact contact = new Contact("12345", "First", "Last", "5553334444", "1234 Loblolly Lane");
		assertThat(ContactService.add(contact)).isTrue();
		
		Contact updated = new Contact("12345", "First", "Last", "5553334444", "1234 LongLeaf Lane");
		assertThat(ContactService.update("12345", updated)).isTrue();
		
		assertThat(ContactService.CONTACT_DATABASE)
			.extracting("12345")
			.hasFieldOrPropertyWithValue("address", "1234 LongLeaf Lane");
	}
	
	@Test
	void updateNonExistent() {
		Contact updated = new Contact("12345", "First", "Last", "5553334444", "1234 Loblolly Lane");
		assertThat(ContactService.update("12345", updated)).isFalse();
	}
	
	@Test
	void updateThrowsExceptionOnInvalidData() {
		
		Contact contact = new Contact("12345", "First", "Last", "5553334444", "1234 Loblolly Lane");
		assertThat(ContactService.add(contact)).isTrue();
		
		Contact updated = new Contact("12345", "First", "Last", "5553334444", "1234 LongLeaf Lane");
		updated.setPhone("555333444@");
	
		assertThatThrownBy(() -> ContactService.update("12345", updated))
		.isInstanceOf(ValidationException.class);
	}
}
