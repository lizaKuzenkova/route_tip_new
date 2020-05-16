package dbaccess;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import dbmodel.*;

public class User {
	Users u = new Users();
	
	public String getUserNameById (int user_id) {
		
		Session session = HibernateHelper.getSession();
		session.beginTransaction();
		
//		Test01 test01 = new Test01();
////		test01.setTest01Name("val from app - 02");
//		
////		session.save(test01);
//		System.out.println("Identity value: " + test01.getTest01Id());
		
		java.util.List<Users> UserData = session.createQuery("from Users", Users.class).getResultList();
		
		String user_name = null;
		
		for (Users UserRow : UserData ) {
			user_name = UserRow.getLastName();
			System.out.println(UserRow.getIdUser() + " - " + UserRow.getFirstName() + " " + UserRow.getLastName());
		}
		
//		User u = new User();
//		System.out.println("---- " + u.getUserById(111) + " =======");
		
//		Test01Home t01h = new Test01Home();
//		System.out.println("test01_id = 10:" + t01h.findById(10));
		
		session.getTransaction().commit();
		HibernateHelper.closeSession();
		
		System.out.println("Work done.");	
		return user_name;
	}

	public String createUser(String login, String password, String first_name, String last_name, String email) {
		System.out.println("createUser is called.");
		
		SessionFactory sessionFactory = null;
		
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
		        .configure() // configures settings from hibernate.cfg.xml
		        .build();
		try {
		    sessionFactory = (SessionFactory) new MetadataSources(registry).buildMetadata().buildSessionFactory();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		    StandardServiceRegistryBuilder.destroy(registry);
		}
		
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		Roles role = (Roles)session.createQuery("from Roles where roleName = 'клиент'").getSingleResult();

		String retval = null;
		try {
			session.save(new Users(role, last_name, first_name,  email, login, password, null));
			session.getTransaction().commit();
			retval = "Пользователь успешно зарегистрирован";
			
		} catch (Exception ex) {
			retval = ex.getMessage();
		}
		session.close();
		sessionFactory.close();
		
		System.out.println("User has been created.");	
		
		return retval;
	}
	
	public String updateUser(int user_id, String login, String password, String first_name, String last_name, String email) {
		System.out.println("updateUser is called.");

		Session session = HibernateHelper.getSession();
		session.beginTransaction();
		
		String retval = null;
		Users u = new Users();
		
		u.setIdUser(user_id);
		u.setFirstName(first_name);
		u.setLastName(last_name);
		u.setLogin(login);
		u.setUPassword(password);
		u.setMail(email);

		Roles r = new Roles();
		r.setIdRole(this.getUserByUserId(user_id).getRoles().getIdRole());
		u.setRoles(r);
		
		session.update(u);
		try {
			session.getTransaction().commit();
			retval = String.format("Пользователь %s %s успешно изменён", u.getLastName(), u.getFirstName());
		} catch (Exception eх) {
			System.out.println(eх.getCause().getCause().getMessage());
			retval = String.format("Невозможно изменить пользователя %s %s.<br><br>Ошибка: %s", u.getLastName(), u.getFirstName(), eх.getCause().getCause().getMessage());
		} finally {
			session.close();
		}
		return retval;
	}
	
	public String deleteUser (int user_id) {
		System.out.println("deleteUser is called.");
		
		String retval = null;
		Session session = HibernateHelper.getSession();
		session.beginTransaction();

		Users u = session.get(Users.class, user_id);
		
		try {
			session.delete(u);
			session.getTransaction().commit();
			retval = String.format("Пользователь %s %s успешно удалён", u.getLastName(), u.getFirstName());
		} catch (Exception eх) {
			retval = String.format("Невозможно удалить пользователя %s %s.<br><br>Ошибка: %s", u.getLastName(), u.getFirstName(), eх.getCause().getCause().getMessage());
		} finally {
			session.close();
		}
		return retval;
	}
	
	public int getUserIdByLoginPassword (String login, String password) {
		
		int user_id = -1;
		
		Session session = HibernateHelper.getSession();
		session.beginTransaction();
				
		List<Users> userData = 
				session.createQuery("SELECT u FROM Users u WHERE u.login = :user_name AND u.UPassword = :password", Users.class)
				.setParameter("user_name", login)
				.setParameter("password", password)
				.getResultList();
		
		HibernateHelper.closeSession();
		
		if (userData.size() > 0) {
			for (Users userRow : userData) {
				user_id = userRow.getIdUser();
			}
		};
		
		return user_id;
	}
	
	public Users getUserByUserId (int user_id) {
	
		Session session = HibernateHelper.getSession();
		session.beginTransaction();
				
		Users userData = 
				session.createQuery("SELECT u FROM Users u WHERE u.idUser = :user_id", Users.class)
				.setParameter("user_id", user_id)
				.getSingleResult();
		
		HibernateHelper.closeSession();
		
		return userData;
	}

	public List<Users> getUserList() {

		Session session = HibernateHelper.getSession();
		session.beginTransaction();
				
		List<Users> userData = 
				session.createQuery("SELECT u FROM Users u", Users.class)
				.getResultList();
		
		HibernateHelper.closeSession();
		
		return userData;		
	}

	public String createUserNew(String user_lname, String user_fname, String user_login, String user_password,
			String user_mail, String user_role) {
		
		System.out.println("createUserNew has been called.");
		
		Session session = HibernateHelper.getSession();
		session.beginTransaction();
		
		Roles role = (Roles)session.createQuery("FROM Roles WHERE roleName = :role_name")
				.setParameter("role_name", user_role)
				.getSingleResult();
		
		String retval = null;
		try {

			session.save(new Users(role, user_lname, user_fname, user_login, user_password, user_mail, null));
			retval = "Пользователь успешно добавлен";
			
		} catch (Exception ex) {
			retval = ex.getMessage();
		}
		session.getTransaction().commit();
		session.close();
		
		System.out.println("User has been created.");	
		
		return retval;
	}
}
