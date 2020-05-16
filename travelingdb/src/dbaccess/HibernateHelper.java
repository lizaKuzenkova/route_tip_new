package dbaccess;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateHelper {

	private static Session _session;
	private static SessionFactory _sessionFactory = null;
	
	public static Session getSession() {
		
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
		        .configure() // configures settings from hibernate.cfg.xml
		        .build();
		try {
		    _sessionFactory = (SessionFactory) new MetadataSources(registry).buildMetadata().buildSessionFactory();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		    StandardServiceRegistryBuilder.destroy(registry);
		}
		
		_session = _sessionFactory.openSession();
		System.out.println("Session has been opened");
		
		return _session;
	}
	
	public static void closeSession() {
		_session.close();
		_sessionFactory.close();
		System.out.println("Session and session factory have been closed.");
	}
}
