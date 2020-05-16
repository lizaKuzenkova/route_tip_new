package dbaccess;

import java.util.List;

import org.hibernate.Session;

import dbmodel.Cities;
import dbmodel.Countries;
import dbmodel.Roles;

public class Rolee {
	
	public List<Roles> getRoleList() {
		Session session = HibernateHelper.getSession();
		session.beginTransaction();
				
		List<Roles> roleData = 
				session.createQuery("SELECT r FROM Roles r", Roles.class)
				.getResultList();
		
		HibernateHelper.closeSession();
		
		return roleData;		
	}
}
