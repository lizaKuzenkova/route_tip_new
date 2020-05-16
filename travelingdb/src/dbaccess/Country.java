package dbaccess;

import java.util.List;

import org.hibernate.Session;

import dbmodel.Cities;
import dbmodel.Continents;
import dbmodel.Countries;

public class Country {

	public String getCountryByCityName(String CityName) {

		Session session = HibernateHelper.getSession();
		session.beginTransaction();

		List<Countries> CountryData = session
				.createQuery("SELECT cntr FROM Countries cntr JOIN cntr.citieses c WHERE c.cityName = :city_name",
						Countries.class)
				.setParameter("city_name", CityName).getResultList();

		HibernateHelper.closeSession();

		String country_name = null;

		for (Countries country : CountryData) {
			country_name = country.getCountryName();
		}

		return country_name;
	}
	
	public List<Countries> getCountryList() {

		Session session = HibernateHelper.getSession();
		session.beginTransaction();
				
		List<Countries> countryData = 
				session.createQuery("SELECT cntr FROM Countries cntr", Countries.class)
				.getResultList();
		
		HibernateHelper.closeSession();
		
		return countryData;		
	}
	
	public Countries getCountryByCountryId (int country_id) {
		
		Session session = HibernateHelper.getSession();
		session.beginTransaction();
				
		Countries countryData = 
				session.createQuery("SELECT cntr FROM Countries cntr WHERE cntr.idCountry = :country_id", Countries.class)
				.setParameter("country_id", country_id)
				.getSingleResult();
		
		HibernateHelper.closeSession();
		
		return countryData;
	}
	
	public String deleteCountry (int country_id) {
		System.out.println("deleteCountry is called. country_id = " + country_id);
		
		String retval = null;
		Session session = HibernateHelper.getSession();
		session.beginTransaction();

		Countries cntr = session.get(Countries.class, country_id);

		try {
			session.delete(cntr);
			session.getTransaction().commit();
			retval = String.format("Страна %s успешно удалена", cntr.getCountryName());
		} catch (Exception eх) {
			retval = String.format("Невозможно удалить страну %s.<br><br>Ошибка: %s", cntr.getCountryName(), eх.getCause().getCause().getMessage());
		} finally {
			session.close();
		}
		return retval;
	}
	
	public String updateCountry(int country_id, String country_nm, String country_capt, int country_pop, int country_ar) {
		System.out.println("updateCountry is called.");

		Session session = HibernateHelper.getSession();
		session.beginTransaction();
		
		String retval = null;
		Countries cntr = new Countries();
		
		cntr.setIdCountry(country_id);
		cntr.setCountryName(country_nm);
		cntr.setCapital(country_capt);
		cntr.setPopulation(country_pop);
		cntr.setArea(country_ar);
		
		Continents cont = new Continents();
		cont.setIdContinent(this.getCountryByCountryId(country_id).getContinents().getIdContinent());
		cntr.setContinents(cont);
		
		session.update(cntr);
		try {
			session.getTransaction().commit();
			retval = String.format("Страна %s успешно изменена", cntr.getCountryName());
		} catch (Exception eх) {
			System.out.println(eх.getCause().getCause().getMessage());
			retval = String.format("Невозможно изменить страну %s.<br><br>Ошибка: %s", cntr.getCountryName(), eх.getCause().getCause().getMessage());
		} finally {
			session.close();
		}
		return retval;
	}
	
	public String createCountry (String country_name, String country_capital, int country_population, int country_area, String country_continent) {
		System.out.println("createCountry has been called.");
		
		Session session = HibernateHelper.getSession();
		session.beginTransaction();
		
		Continents continent = (Continents)session.createQuery("FROM Continents WHERE continentName = :continent_name")
				.setParameter("continent_name", country_continent)
				.getSingleResult();
		
		String retval = null;
		try {

			session.save( new Countries(continent, country_name, country_capital, country_population, country_area, null));
			retval = "Страна успешно добавлена";
			
		} catch (Exception ex) {
			retval = ex.getMessage();
		}
		session.getTransaction().commit();
		session.close();
		
		System.out.println("Country has been created.");	
		
		return retval;		
	}
}
