package dbaccess;

import java.util.List;

import org.hibernate.Session;

import dbmodel.Continents;
import dbmodel.Countries;
import dbmodel.Cuisine;
import dbmodel.Hotels;

public class Cuisenee {
	
	public List<Cuisine> getCuisineList() {

		Session session = HibernateHelper.getSession();
		session.beginTransaction();
				
		List<Cuisine> cuisineData = 
				session.createQuery("SELECT cuis FROM Cuisine cuis", Cuisine.class)
				.getResultList();
		
		HibernateHelper.closeSession();
		
		return cuisineData;		
	}
	
	public Cuisine getCuisineByACuisineId (int cuisine_id) {
		
		Session session = HibernateHelper.getSession();
		session.beginTransaction();
				
		Cuisine cuisineData = 
				session.createQuery("SELECT cuis FROM Cuisine cuis WHERE cuis.idCuisine = :cuisine_id", Cuisine.class)
				.setParameter("cuisine_id", cuisine_id)
				.getSingleResult();
		
		HibernateHelper.closeSession();
		
		return cuisineData;
	}
	
	public String deleteCuisine (int cuisine_id) {

		System.out.println("deleteCuisine is called. cuisine_id = " + cuisine_id);
		
		String retval = null;
		Session session = HibernateHelper.getSession();
		session.beginTransaction();

		Cuisine cuis = session.get(Cuisine.class, cuisine_id);

		try {
			session.delete(cuis);
			session.getTransaction().commit();
			retval = String.format("Вид кухни %s успешно удалён", cuis.getCuisineType());
		} catch (Exception eх) {
			retval = String.format("Невозможно удалить вид кухни %s.<br><br>Ошибка: %s", cuis.getCuisineType(), eх.getCause().getCause().getMessage());
		} finally {
			session.close();
		}
		return retval;
	}
	
	public String updateCuisine (int cuisine_id, String cuisine_t) {
		System.out.println("updateCuisine is called.");
	
		Session session = HibernateHelper.getSession();
		session.beginTransaction();
		
		String retval = null;
		Cuisine cuis = new Cuisine();
		
		cuis.setIdCuisine(cuisine_id);
		cuis.setCuisineType(cuisine_t);
		
		session.update(cuis);
		try {
			session.getTransaction().commit();
			retval = String.format("Кухня %s успешно изменена", cuis.getCuisineType());
		} catch (Exception eх) {
			System.out.println(eх.getCause().getCause().getMessage());
			retval = String.format("Невозможно изменить кухню %s.<br><br>Ошибка: %s", cuis.getCuisineType(), eх.getCause().getCause().getMessage());
		} finally {
			session.close();
		}
		return retval;
	}

	public String createCuisine(String cuisine_type) {
		
		System.out.println("createCuisine has been called.");
		
		Session session = HibernateHelper.getSession();
		session.beginTransaction();
		
		String retval = null;
		try {

			session.save( new Cuisine(cuisine_type));
			retval = "Новый вид кухни успешно добавлна";
			
		} catch (Exception ex) {
			retval = ex.getMessage();
		}
		session.getTransaction().commit();
		session.close();
		
		System.out.println("Cuisine has been created.");	
		
		return retval;
	}
}
