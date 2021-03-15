package unittests;

import static org.junit.jupiter.api.Assertions.*;
import dbaccess.User;
import org.junit.jupiter.api.Test;

class UserTest {
	
	User u = new User();
//	String login = "unittestuser";
	String login = "testttttttt";
	String password = "unittestuserPassword";
	String first_name = "Unit";
	String last_name = "Test";
	String email = "U.T@unit.test";

/*	@Test
	void testCreateTestUser() {
		String newUserRetVal = u.createUser(login, password, first_name, last_name, email);
		assertEquals("Пользователь успешно зарегистрирован", newUserRetVal);
	}
	
	@Test
	void getTestUser() {
		int id_user = u.getUserIdByLoginPassword(login, password);
		System.out.println("Test user id=" + id_user);
		assertTrue(id_user > 0);
	} */
	
	@Test
	void deleteTestUser() {
		int id_user = u.getUserIdByLoginPassword(login, password);
		u.deleteUser(id_user);
		id_user = u.getUserIdByLoginPassword(login, password);
		assertFalse(id_user > 0);
	} 
}
