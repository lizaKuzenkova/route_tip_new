package dbaccess;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Session;

import dbmodel.AttractionStats;
import dbmodel.Attractions;
import dbmodel.HotelStats;
import dbmodel.Hotels;
import dbmodel.PlaceStats;
import dbmodel.PlaceTypes;
import dbmodel.RestaurantStats;
import dbmodel.Restaurants;

public class Stats {
	
	public enum PLACE_TYPE
	{
	    HOTEL,
	    ATTRACTION,
	    RESTAURANT
	}
	
	public static void collectStats(String[] place_ids, Stats.PLACE_TYPE place_type, Integer user_id) {
		System.out.println("collectStats has been called");
		System.out.println("place_type = " + place_type);
		
		Session session = HibernateHelper.getSession();
		session.beginTransaction();
		
		PlaceTypes pt = (PlaceTypes)session.createQuery("FROM PlaceTypes WHERE placeTypeCode = :place_type", PlaceTypes.class)
				.setParameter("place_type", place_type.toString())
				.getSingleResult();

		int place_id = -1;
		try {
			for (String place_name : place_ids) {
				if (place_type == PLACE_TYPE.ATTRACTION) {
					Attraction a = new Attraction();
					Attractions attr = a.getAttractionByAttractionName(place_name);
					place_id = attr.getIdAttraction();
				}
				if (place_type == PLACE_TYPE.HOTEL) {
					Hotel h = new Hotel();
					Hotels hotel = h.getHotelByHotelName(place_name);
					place_id =  hotel.getIdHotel();
				}
				if (place_type == PLACE_TYPE.RESTAURANT) {
					Restaurant r = new Restaurant();
					Restaurants hotel = r.getRestaurantByRestaurantName(place_name);
					place_id =  hotel.getIdRestaurant();
				}
				session.save( new PlaceStats(pt, place_id, user_id, new Timestamp(System.currentTimeMillis())));
			}
			session.getTransaction().commit();
		} catch (Exception ex) {
			System.out.println(ex.getCause().getCause().getMessage());
		} finally {
			session.close();
		}
		System.out.println("Statistices have been collected.");	
	}
	
	public static List<AttractionStats> getAttractionStats() {
		System.out.println("getAttractionStats has been called");
		
		Session session = HibernateHelper.getSession();
		session.beginTransaction();
		
		List<AttractionStats> attr_stats = session.createQuery("SELECT ast FROM AttractionStats ast", AttractionStats.class).getResultList();
		
		session.close();
		
		return attr_stats;
	}
	
	public static List<HotelStats> getHotelStats() {
		System.out.println("getHotelStats has been called");
		
		Session session = HibernateHelper.getSession();
		session.beginTransaction();
		
		List<HotelStats> hotel_stats = session.createQuery("SELECT hst FROM HotelStats hst", HotelStats.class).getResultList();
		
		session.close();
		
		return hotel_stats;
	}
	
	public static List<RestaurantStats> getRestaurantStats() {
		System.out.println("getRestaurantStats has been called");
		
		Session session = HibernateHelper.getSession();
		session.beginTransaction();
		
		List<RestaurantStats> restaurant_stats = session.createQuery("SELECT rst FROM RestaurantStats rst", RestaurantStats.class).getResultList();
		
		session.close();
		
		return restaurant_stats;
	}
}
