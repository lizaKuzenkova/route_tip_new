package web;

import java.io.IOException;
import java.util.List;

import javax.management.relation.Role;
import javax.servlet.http.HttpServletResponse;

import dbaccess.City;
import dbaccess.Continent;
import dbaccess.Country;
import dbaccess.Rolee;
import dbmodel.Cities;
import dbmodel.Continents;
import dbmodel.Countries;
import dbmodel.Roles;


public class Utility {

	public static String BuildDropDownCities(HttpServletResponse response) throws IOException {

		City city = new City();
		List<Cities> cities = city.getCityList();
		
		String select_tag = "<select id=\"city\" name=\"selected_city\">";
		select_tag = select_tag + String.format("<option value=\"%s\">%s</option>", null, "<Выберите город>");

		for (Cities item : cities) {
			select_tag = select_tag + String.format("<option value=\"%s\">%s</option>", item.getCityName(), item.getCityName());
		}

		select_tag = select_tag + "</select>";
		
		return select_tag;

	}
	
	public static String BuildDropDownCountries (HttpServletResponse response) throws IOException {

		Country country = new Country();
		List<Countries> countries = country.getCountryList();
		
		String select_tag = "<select id=\"country\" name=\"selected_country\">";
		select_tag = select_tag + String.format("<option value=\"%s\">%s</option>", null, "<Выберите страну>");

		for (Countries item : countries) {
			select_tag = select_tag + String.format("<option value=\"%s\">%s</option>", item.getCountryName(), item.getCountryName());
		}

		select_tag = select_tag + "</select>";
		
		return select_tag;

	}
	
	public static String BuildDropDownContinents (HttpServletResponse response) throws IOException {

		Continent continent = new Continent();
		List<Continents> continents = continent.getContinentList();
		
		String select_tag = "<select id=\"continent\" name=\"selected_continent\">";
		select_tag = select_tag + String.format("<option value=\"%s\">%s</option>", null, "<Выберите континент>");

		for (Continents item : continents) {
			select_tag = select_tag + String.format("<option value=\"%s\">%s</option>", item.getContinentName(), item.getContinentName());
		}

		select_tag = select_tag + "</select>";
		
		return select_tag;
	}

	public static String BuildDropDownUserRoles(HttpServletResponse response) {

		Rolee role = new Rolee();
		List<Roles> roles = role.getRoleList();
		
		String select_tag = "<select id=\"role\" name=\"selected_role\">";
		select_tag = select_tag + String.format("<option value=\"%s\">%s</option>", null, "<Выберите роль>");

		for (Roles item : roles) {
			select_tag = select_tag + String.format("<option value=\"%s\">%s</option>", item.getRoleName(), item.getRoleName());
		}

		select_tag = select_tag + "</select>";
		
		return select_tag;
	}

	
}
