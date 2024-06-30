package org.snhu.cs320.contact;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.snhu.cs320.exceptions.ValidationException;

public class ContactTest {

	@Test
	void testSuccessPath() {
		Contact contact = new Contact("1", "First", "Last", "5553334444", "1234 Lobolly Lane");
		assertThat(contact)
			.isNotNull()
			.hasFieldOrPropertyWithValue("id", "1")
			.hasFieldOrPropertyWithValue("firstName", "First")
			.hasFieldOrPropertyWithValue("lastName", "Last")
			.hasFieldOrPropertyWithValue("phone", "5553334444")
			.hasFieldOrPropertyWithValue("address", "1234 Lobolly Lane");
	}
	
	@ParameterizedTest
	@CsvSource({
		"'',First,Last,5553334444, 1234 Lobolly Lane", // Blank ID
		",First,Last,5553334444,1234 Lobolly Lane", // Null ID
		"12345678901,First,Last,5553334444,1234 Lobolly Lane", // ID is too Long
		
		"1,First,Last,,1234 Lobolly Lane", // Null Phone
		"1,First,Last,'',1234 Lobolly Lane", // Blank Phone
		"1,First,Last,555333444,1234 Lobolly Lane", // Phone too Short
		"1,First,Last,55533344449,1234 Lobolly Lane", // Phone too Long
		"1,First,Last,555333444A,1234 Lobolly Lane", // Phone with alpha characters
		"1,First,Last,555333444~,1234 Lobolly Lane", // Phone with alpha characters
		"1,First,Last,55533 4444,1234 Lobolly Lane", // Phone with space
		
		"1,'',Last,5553334444,1234 Lobolly Lane", // Blank First Name
		"1,,Last,5553334444,1234 Lobolly Lane", // Null First Name
		"1,FirstFirstF,Last,5553334444,1234 Lobolly Lane", // Too Long First Name
		"1,First,'',5553334444,1234 Lobolly Lane", // Blank Last Name
		"1,First,,5553334444,1234 Lobolly Lane", // Null Last Name
		"1,First,LastLastLas,5553334444,1234 Lobolly Lane", // Too Long Last Name
		
		"1,First,Last,5553334444,''", // Blank Address
		"1,First,Last,5553334444,,", // Null Address
		"1,First,Last,5553334444,1234 Lobolly Lane 1234 Loblolly", // Too Long Address
	})
	void invalidInputThrowsException() {
		assertThatThrownBy(() -> new Contact("", "First", "Last", "5553334444", "1234 Lobolly Lane"))
			.isInstanceOf(ValidationException.class);
	}
}
