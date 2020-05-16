package dbaccess;

import java.math.BigDecimal;
import java.util.List;
import org.hibernate.Session;

import dbmodel.Attractions;
import dbmodel.Cities;
import dbmodel.Continents;
import dbmodel.Countries;
import dbmodel.Hotels;
import dbmodel.Restaurants;

public class Hotel {
	public List<Hotels> getHotelsByCityName(String CityName){
		
		Session session = HibernateHelper.getSession();
		session.beginTransaction();
		
		List<Hotels> HotelData = 
				session.createQuery("SELECT h FROM Hotels h JOIN h.cities c WHERE c.cityName = :city_name", Hotels.class)
				.setParameter("city_name", CityName)
				.getResultList();
		
		HibernateHelper.closeSession();
		return HotelData;
	}
	
	public List<Hotels> getHotelList() {

		Session session = HibernateHelper.getSession();
		session.beginTransaction();
				
		List<Hotels> hotelData = 
				session.createQuery("SELECT h FROM Hotels h", Hotels.class)
				.getResultList();
		
		HibernateHelper.closeSession();
		
		return hotelData;		
	}
	
	public Hotels getHotelByHotelId (int hotel_id) {
		
		Session session = HibernateHelper.getSession();
		session.beginTransaction();
				
		Hotels hotelData = 
				session.createQuery("SELECT h FROM Hotels h WHERE h.idHotel = :hotel_id", Hotels.class)
				.setParameter("hotel_id", hotel_id)
				.getSingleResult();
		
		HibernateHelper.closeSession();
		
		return hotelData;
	}
	
	public String deleteHotel (int hotel_id) {
		System.out.println("deleteHotel is called. hotel_id = " + hotel_id);
		
		String retval = null;
		Session session = HibernateHelper.getSession();
		session.beginTransaction();

		Hotels h = session.get(Hotels.class, hotel_id);

		try {
			session.delete(h);
			session.getTransaction().commit();
			retval = String.format("Отель %s успешно удалён", h.getHotelName());
		} catch (Exception eх) {
			retval = String.format("Невозможно удалить отель %s.<br><br>Ошибка: %s", h.getHotelName(), eх.getCause().getCause().getMessage());
		} finally {
			session.close();
		}
		return retval;
	}
	
	public String updateHotel(int hotel_id, String hotel_nm, String hotel_address, BigDecimal hotel_rating, int hotel_stars, String hotel_website) {
		System.out.println("updateHotel is called.");

		Session session = HibernateHelper.getSession();
		session.beginTransaction();
		
		String retval = null;
		Hotels h = new Hotels();
		
		h.setIdHotel(hotel_id);
		h.setHotelName(hotel_nm);
		h.setAddress(hotel_address);
		h.setRating(hotel_rating);
		h.setAmountOfStars(hotel_stars);
		h.setWebsite(hotel_website);

		Cities c = new Cities();
		c.setIdCity(this.getHotelByHotelId(hotel_id).getCities().getIdCity());
		h.setCities(c);
		
		session.update(h);
		try {
			session.getTransaction().commit();
			retval = String.format("Отель %s успешно изменён", h.getHotelName());
		} catch (Exception eх) {
			System.out.println(eх.getCause().getCause().getMessage());
			retval = String.format("Невозможно изменить отель %s.<br><br>Ошибка: %s", h.getHotelName(), eх.getCause().getCause().getMessage());
		} finally {
			session.close();
		}
		return retval;
	}

	public String createHotel(String hotel_name, String hotel_addr, BigDecimal hotel_rat, int hotel_stars,
			String hotel_website, String hotel_city) {
		
		System.out.println("createHotel has been called.");
		
		Session session = HibernateHelper.getSession();
		session.beginTransaction();
		
		Cities city = (Cities)session.createQuery("FROM Cities WHERE cityName = :city_name")
				.setParameter("city_name", hotel_city)
				.getSingleResult();
		
		String retval = null;
		try {

			session.save( new Hotels(city, hotel_name, hotel_addr, hotel_rat, hotel_stars, hotel_website));
			retval = "Отель успешно добавлена";
			
		} catch (Exception ex) {
			retval = ex.getMessage();
		}
		session.getTransaction().commit();
		session.close();
		
		System.out.println("Hotel has been created.");	
		
		return retval;
	}

	public Hotels getHotelByHotelName(String hotel_name) {
		Session session = HibernateHelper.getSession();
		session.beginTransaction();
				
		Hotels hotelData = 
				session.createQuery("FROM Hotels WHERE hotelName = :hotel_name", Hotels.class)
				.setParameter("hotel_name", hotel_name)
				.getSingleResult();
		
		HibernateHelper.closeSession();
		
		return hotelData;
	}
}
