package dbaccess;

import java.math.BigDecimal;
import java.util.List;
import org.hibernate.Session;

import dbmodel.Attractions;
import dbmodel.Cities;
import dbmodel.Hotels;
import dbmodel.Restaurants;

public class Restaurant {
	public List<Restaurants> getRestaurantsByCityName(String CityName){
		
		Session session = HibernateHelper.getSession();
		session.beginTransaction();
		
		List<Restaurants> RestaurantData = 
				session.createQuery("SELECT r FROM Restaurants r JOIN r.cities c WHERE c.cityName = :city_name", Restaurants.class)
				.setParameter("city_name", CityName)
				.getResultList();
		
		HibernateHelper.closeSession();
		return RestaurantData;
	}
	
	public List<Restaurants> getRestaurantList() {

		Session session = HibernateHelper.getSession();
		session.beginTransaction();
				
		List<Restaurants> restaurantData = 
				session.createQuery("SELECT rest FROM Restaurants rest", Restaurants.class)
				.getResultList();
		
		HibernateHelper.closeSession();
		
		return restaurantData;		
	}
	
	public Restaurants getRestaurantByRestaurantId (int restaurant_id) {
		
		Session session = HibernateHelper.getSession();
		session.beginTransaction();
				
		Restaurants restaurantData = 
				session.createQuery("SELECT rest FROM Restaurants rest WHERE rest.idRestaurant = :restaurant_id", Restaurants.class)
				.setParameter("restaurant_id", restaurant_id)
				.getSingleResult();
		
		HibernateHelper.closeSession();
		
		return restaurantData;
	}
	
	public String deleteRestaurant(int restaurant_id) {
		System.out.println("deleteRestaurant is called. restaurant_id = " + restaurant_id);
		
		String retval = null;
		Session session = HibernateHelper.getSession();
		session.beginTransaction();

		Restaurants rest = session.get(Restaurants.class, restaurant_id);

		try {
			session.delete(rest);
			session.getTransaction().commit();
			retval = String.format("Ресторан %s успешно удалён", rest.getRestName());
		} catch (Exception eх) {
			retval = String.format("Невозможно удалить ресторан %s.<br><br>Ошибка: %s", rest.getRestName(), eх.getCause().getCause().getMessage());
		} finally {
			session.close();
		}
		return retval;
	}
	
	public String updateRestaurant(int restaurant_id, String restaurant_nm, String restaurant_address, BigDecimal restaurant_rating, String restaurant_website) {
		System.out.println("updateRestaurant is called.");

		Session session = HibernateHelper.getSession();
		session.beginTransaction();
		
		String retval = null;
		Restaurants rest = new Restaurants();
		
		rest.setIdRestaurant(restaurant_id);
		rest.setRestName(restaurant_nm);
		rest.setAddress(restaurant_address);
		rest.setRating(restaurant_rating);
		rest.setWebsite(restaurant_website);

		Cities c = new Cities();
		c.setIdCity(this.getRestaurantByRestaurantId(restaurant_id).getCities().getIdCity());
		rest.setCities(c);
		
		session.update(rest);
		try {
			session.getTransaction().commit();
			retval = String.format("Ресторан %s успешно изменён", rest.getRestName());
		} catch (Exception eх) {
			System.out.println(eх.getCause().getCause().getMessage());
			retval = String.format("Невозможно изменить ресторан %s.<br><br>Ошибка: %s", rest.getRestName(), eх.getCause().getCause().getMessage());
		} finally {
			session.close();
		}
		return retval;
	}

	public String createRestaurant(String restaurant_name, String restaurant_addr, BigDecimal restaurant_rat,
			String restaurant_website, String restaurant_city) {
		
		System.out.println("createRestaurant has been called.");
		
		Session session = HibernateHelper.getSession();
		session.beginTransaction();
		
		Cities city = (Cities)session.createQuery("FROM Cities WHERE cityName = :city_name")
				.setParameter("city_name", restaurant_city)
				.getSingleResult();
		
		String retval = null;
		try {

			session.save(new Restaurants(city, restaurant_name, restaurant_addr, restaurant_rat, restaurant_website, null, null));
			retval = "Ресторан успешно добавлена";
			
		} catch (Exception ex) {
			retval = ex.getMessage();
		}
		session.getTransaction().commit();
		session.close();
		
		System.out.println("Restaurant has been created.");	
		
		return retval;
	}

	public Restaurants getRestaurantByRestaurantName(String restaurant_name) {
		
		Session session = HibernateHelper.getSession();
		session.beginTransaction();
				
		Restaurants restaurantData = 
				session.createQuery("FROM Restaurants WHERE restName = :restaurant_name", Restaurants.class)
				.setParameter("restaurant_name", restaurant_name)
				.getSingleResult();
		
		HibernateHelper.closeSession();
		
		return restaurantData;
	}
}
