package dbaccess;

import java.util.List;
import org.hibernate.Session;
import dbmodel.Cities;
import dbmodel.Continents;
import dbmodel.Countries;

public class City {
	
	public List<Cities> getCityList() {
		Session session = HibernateHelper.getSession();
		session.beginTransaction();
				
		List<Cities> cityData = 
				session.createQuery("SELECT ct FROM Cities ct", Cities.class)
				.getResultList();
		
		HibernateHelper.closeSession();
		
		return cityData;		
	}
	
	public Cities getCityByCityId (int city_id) {		
		Session session = HibernateHelper.getSession();
		session.beginTransaction();
				
		Cities cityData = 
				session.createQuery("SELECT ct FROM Cities ct WHERE ct.idCity = :city_id", Cities.class)
				.setParameter("city_id", city_id)
				.getSingleResult();
		
		HibernateHelper.closeSession();
		
		return cityData;
	}
	
	public String deleteCity (int city_id) {

		System.out.println("deleteCity is called. city_id = " + city_id);
		
		String retval = null;
		Session session = HibernateHelper.getSession();
		session.beginTransaction();

		Cities ct = session.get(Cities.class, city_id);

		try {
			session.delete(ct);
			session.getTransaction().commit();
			retval = String.format("Город %s успешно удален", ct.getCityName());
		} catch (Exception eх) {
			retval = String.format("Невозможно удалить город %s.<br><br>Ошибка: %s", ct.getCityName(), eх.getCause().getCause().getMessage());
		} finally {
			session.close();
		}
		return retval;
	}
	
	public String updateCity(int city_id, String city_nm) {
		System.out.println("updateCity is called.");

		Session session = HibernateHelper.getSession();
		session.beginTransaction();
		
		String retval = null;
		Cities ct = new Cities();
		
		ct.setIdCity(city_id);
		ct.setCityName(city_nm);

		Countries cntr = new Countries();
		cntr.setIdCountry(this.getCityByCityId(city_id).getCountries().getIdCountry());
		ct.setCountries(cntr);
		
		session.update(ct);
		try {
			session.getTransaction().commit();
			retval = String.format("Город %s успешно изменён", ct.getCityName());
		} catch (Exception eх) {
			System.out.println(eх.getCause().getCause().getMessage());
			retval = String.format("Невозможно изменить город %s.<br><br>Ошибка: %s", ct.getCityName(), eх.getCause().getCause().getMessage());
		} finally {
			session.close();
		}
		return retval;
	}

	public String createCity(String city_name, String city_country) {
		System.out.println("createCity has been called.");
		
		Session session = HibernateHelper.getSession();
		session.beginTransaction();
		
		Countries country = (Countries)session.createQuery("FROM Countries WHERE countryName = :country_name")
				.setParameter("country_name", city_country)
				.getSingleResult();
		
		String retval = null;
		try {

			session.save(new Cities(country, city_name));
			retval = "Город успешно добавлена";
			
		} catch (Exception ex) {
			retval = ex.getMessage();
		}
		session.getTransaction().commit();
		session.close();
		
		System.out.println("City has been created.");	
		
		return retval;
	}
}
