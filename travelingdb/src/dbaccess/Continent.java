package dbaccess;

import java.util.List;

import org.hibernate.Session;
import dbmodel.Continents;
import dbmodel.Countries;

public class Continent {
	
	public List<Continents> getContinentList() {

		Session session = HibernateHelper.getSession();
		session.beginTransaction();
				
		List<Continents> continentData = 
				session.createQuery("SELECT cont FROM Continents cont", Continents.class)
				.getResultList();
		
		HibernateHelper.closeSession();
		
		return continentData;		
	}
	
	public Continents getContinentByContinentId (int continent_id) {
		
		Session session = HibernateHelper.getSession();
		session.beginTransaction();
				
		Continents continentData = 
				session.createQuery("SELECT cont FROM Continents cont WHERE cont.idContinent = :continent_id", Continents.class)
				.setParameter("continent_id", continent_id)
				.getSingleResult();
		
		HibernateHelper.closeSession();
		
		return continentData;
	}
	
	public String deleteContinent (int continent_id) {
		
		System.out.println("deleteContinent is called. continent_id = " + continent_id);
		
		String retval = null;
		Session session = HibernateHelper.getSession();
		session.beginTransaction();

		Continents cont = session.get(Continents.class, continent_id);

		try {
			session.delete(cont);
			session.getTransaction().commit();
			retval = String.format("Континент %s успешно удалена", cont.getContinentName());
		} catch (Exception eх) {
			retval = String.format("Невозможно удалить континент %s.<br><br>Ошибка: %s", cont.getContinentName(), eх.getCause().getCause().getMessage());
		} finally {
			session.close();
		}
		return retval;
	}
	
	public String updateContinent(int continent_id, String continent_nm) {
		System.out.println("updateContinent is called.");

		Session session = HibernateHelper.getSession();
		session.beginTransaction();
		
		String retval = null;
		Continents cont = new Continents();
		
		cont.setIdContinent(continent_id);
		cont.setContinentName(continent_nm);
		
		session.update(cont);
		try {
			session.getTransaction().commit();
			retval = String.format("Континент %s успешно изменён", cont.getContinentName());
		} catch (Exception eх) {
			System.out.println(eх.getCause().getCause().getMessage());
			retval = String.format("Невозможно изменить континент %s.<br><br>Ошибка: %s", cont.getContinentName(), eх.getCause().getCause().getMessage());
		} finally {
			session.close();
		}
		return retval;
	}
}
