package dbaccess;

import java.util.List;
import org.hibernate.Session;
import dbmodel.Attractions;
import dbmodel.Cities;
import dbmodel.Continents;
import dbmodel.Countries;

public class Attraction {

	public List<Attractions> getAttractionsByCityName(String CityName){
		
		Session session = HibernateHelper.getSession();
		session.beginTransaction();
		
		List<Attractions> AttractionData = 
				session.createQuery("select a from Attractions a JOIN a.cities c WHERE c.cityName = :city_name", Attractions.class)
				.setParameter("city_name", CityName)
				.getResultList();
		
		HibernateHelper.closeSession();
		return AttractionData;
	}
	
	public List<Attractions> getAttractionList() {

		Session session = HibernateHelper.getSession();
		session.beginTransaction();
				
		List<Attractions> attractionData = 
				session.createQuery("SELECT att FROM Attractions att", Attractions.class)
				.getResultList();
		
		HibernateHelper.closeSession();
		
		return attractionData;		
	}
	
	public Attractions getAttractionByAttractionId (int attraction_id) {
		
		Session session = HibernateHelper.getSession();
		session.beginTransaction();
				
		Attractions attractionData = 
				session.createQuery("SELECT att FROM Attractions att WHERE att.idAttraction = :attraction_id", Attractions.class)
				.setParameter("attraction_id", attraction_id)
				.getSingleResult();
		
		HibernateHelper.closeSession();
		
		return attractionData;
	}
	
	public Attractions getAttractionByAttractionName (String attraction_name) {
		
		Session session = HibernateHelper.getSession();
		session.beginTransaction();
				
		Attractions attractionData = 
				session.createQuery("SELECT att FROM Attractions att WHERE att.attractionName = :attraction_name", Attractions.class)
				.setParameter("attraction_name", attraction_name)
				.getSingleResult();
		
		HibernateHelper.closeSession();
		
		return attractionData;
	}
	
	public String deleteAttraction (int attraction_id) {
		System.out.println("deleteAttraction is called. attraction_id = " + attraction_id);
		
		String retval = null;
		Session session = HibernateHelper.getSession();
		session.beginTransaction();

		Attractions att = session.get(Attractions.class, attraction_id);

		try {
			session.delete(att);
			session.getTransaction().commit();
			retval = String.format("Достопримечательность %s успешно удалена", att.getAttractionName());
		} catch (Exception eх) {
			retval = String.format("Невозможно удалить достопримечательность %s.<br><br>Ошибка: %s", att.getAttractionName(), eх.getCause().getCause().getMessage());
		} finally {
			session.close();
		}
		return retval;
	}
	
	public String updateAttraction(int attraction_id, String attraction_nm, String attraction_descr) {
		System.out.println("updateAttraction is called.");

		Session session = HibernateHelper.getSession();
		session.beginTransaction();
		
		String retval = null;
		Attractions att = new Attractions();
		
		att.setIdAttraction(attraction_id);
		att.setAttractionName(attraction_nm);
		att.setAttDescription(attraction_descr);

		Cities c = new Cities();
		c.setIdCity(this.getAttractionByAttractionId(attraction_id).getCities().getIdCity());
		att.setCities(c);
		
		session.update(att);
		try {
			session.getTransaction().commit();
			retval = String.format("Достопримечательность %s успешно изменена", att.getAttractionName());
		} catch (Exception eх) {
			System.out.println(eх.getCause().getCause().getMessage());
			retval = String.format("Невозможно изменить достопримечательность %s.<br><br>Ошибка: %s", att.getAttractionName(), eх.getCause().getCause().getMessage());
		} finally {
			session.close();
		}
		return retval;
		
	}

	public String createAttraction(String attraction_name, String attraction_descr, String attraction_city) {
		
		System.out.println("createAttraction has been called.");
		
		Session session = HibernateHelper.getSession();
		session.beginTransaction();
		
		Cities city = (Cities)session.createQuery("FROM Cities WHERE cityName = :city_name")
				.setParameter("city_name", attraction_city)
				.getSingleResult();
		
		String retval = null;
		try {

			session.save( new Attractions(city, attraction_name, attraction_descr, null));
			retval = "Достопримечательность успешно добавлена";
			
		} catch (Exception ex) {
			retval = ex.getMessage();
		}
		session.getTransaction().commit();
		session.close();
		
		System.out.println("Attraction has been created.");	
		
		return retval;		

	}
}
