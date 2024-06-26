package org.snhu.cs320.contact;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.snhu.cs320.exceptions.ValidationException;

public class ContactService {
	
	static final Map<String, Contact> CONTACT_DATABASE = new ConcurrentHashMap<String, Contact>();
	
	private ContactService() {}
	
	public static boolean add(Contact contact) {
		if (CONTACT_DATABASE.containsKey(contact.getId())) return false;
		CONTACT_DATABASE.putIfAbsent(contact.getId(), contact);
		return true;
	}
	
	public static boolean delete(String id) {
		return CONTACT_DATABASE.remove(id) != null;
	}
	
	public static boolean update(String id, Contact updated) {
		Contact existing = CONTACT_DATABASE.get(id);
		
		if (existing == null) return false;
		
		existing.setFirstName(updated.getFirstName());
		existing.setLastName(updated.getLastName());
		existing.setPhone(updated.getPhone());
		existing.setAddress(updated.getAddress());
		
		existing.validate();
		
		return true;
	}
}
	
