package web;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbaccess.Attraction;
import dbaccess.City;
import dbaccess.Continent;
import dbaccess.Country;
import dbaccess.Cuisenee;
import dbaccess.Hotel;
import dbaccess.Restaurant;
import dbaccess.User;
import dbmodel.Attractions;
import dbmodel.Cities;
import dbmodel.Continents;
import dbmodel.Countries;
import dbmodel.Cuisine;
import dbmodel.Hotels;
import dbmodel.Restaurants;
import dbmodel.Users;

public class UserAdministration extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html; charset=UTF-8");
		SessionHelper.typeSessionHeader(request, response);
		
		response.getWriter()
				.append("<html><head><title>RouteTips - Администрирование</title></head><body><form>"
						+ "<link href=\"css/style.css\" rel=\"stylesheet\" type=\"text/css\">"
						+ "<body>");
		
		boolean is_admin = SessionHelper.IsAdmin(request);
		boolean is_user = SessionHelper.IsUser(request);

		if (is_admin || is_user) {

			response.getWriter().append("<form>"
							+ "<div><nav>"
							+ "<ul class=\"topmenu\">"						
								+ "<li><class=\"dropdown\">Администрирование</a>"
									+ "<ul class=\"submenu\">"
										+ "<li><a href=\"#users\">Пользователи</a><br><br></li>"
										+ "<li><a href=\"#attractions\">Достопримечательности</a><br><br></li>"
										+ "<li><a href=\"#hotels\">Отели</a><br><br></li>"
										+ "<li><a href=\"#restaurants\">Рестораны</a><br><br></li>"
									+ "</ul>"
								+ "</li>"
								+ "<li><class=\"dropdown\">Справочники</a>"
									+ "<ul class=\"submenu\">"
										+ "<li><a href=\"#continents\">Континенты</a><br><br></li>"
										+ "<li><a href=\"#countries\">Страны</a><br><br></li>"
										+ "<li><a href=\"#cities\">Города</a><br><br></li>"
										+ "<li><a href=\"#cuisine\">Виды кухни</a><br><br></li>"							
										+ "<li><a href=\"#roles\">Роли пользователей</a><br><br></li>"
									+ "</ul>"
								+ "</li>"	
								+ "<li><class=\"dropdown\">Сервис</a>"
									+ "<ul class=\"submenu\">"
										+ "<li><a href=\"Statistics\">Статистика</a><br><br></li>"
										+ "<li><a href=\"HomePage\">Составление маршрута</a><br><br></li>"
									+ "</ul>"
								+ "</li>"
							+ "</ul></nav></div>");

			this.BuildUserList(response, is_admin);
			this.BuildCuisineList(response, is_admin);
			this.BuildContinentList(response, is_admin);
			this.BuildCountryList(response, is_admin);
			this.BuildCityList(response, is_admin);
			this.BuildAttractionList(response, is_admin);
			this.BuildRestaurantList(response, is_admin);
			this.BuildHotelList(response, is_admin);

			String user_id = request.getParameter("user_id");

			if (user_id == null) {
				user_id = "";
			}

			if (user_id != "") {
				String url = String.format("UserRegistration?user_id=%s", user_id);
				SessionHelper.setUserId(request, user_id);

				System.out.println("-----" + SessionHelper.getUserId(request));

				response.sendRedirect(url);
			}

		} else {
			response.getWriter().append("<h1>Доступ запрещён!</h1>");
		}
		response.getWriter().append("</form></body></html>");
	}
	
	////////////////////////////////////////////////
	//// Build Edit & Remove columns (Header) //////
	////////////////////////////////////////////////
	private String BuildEditRemoveColumns_Header (boolean is_admin) {
		String retval = "";
		if (is_admin) {
			retval = "<th>Изменить</th><th>Удалить</th>";
		}
		return retval;
	}

	//////////////////////////////////////////////
	//// Build Edit & Remove columns (Cell) //////
	//////////////////////////////////////////////
	private String BuildEditRemoveColumns_Cell (boolean is_admin, String class_name) {
		String retval = "";
		if (is_admin) {
			retval = "<td><a href=\"" + class_name + "Update?" + class_name.toLowerCase() + "_id=%d\">Изменить</a></td>"
					+ "<td><a href=\"" + class_name + "Delete?" + class_name.toLowerCase() + "_id=%d\">Удалить</a></td>";
		}
		return retval;
	}
	
	///////////////////////////
	//// Build User list //////
	///////////////////////////
	private void BuildUserList(HttpServletResponse response, boolean is_admin) throws IOException {

		User u = new User();
		List<Users> users = u.getUserList();

		int user_id = -1;
		String user_fn = null;
		String user_ln = null;
		String user_email = null;
		String user_login = null;
		String user_role = null;
		String user_TR_tag = null;

		response.getWriter()
				.append("<h2 id=\"users\">Пользователи</h2>" 
						+ "<table border=\"0\" cellspacing=\"0\" cellpadding=\"10\">"
						+ "<tr bgcolor=\"#EBEBEB\">" 
						+ "<th>ID</th>" 
						+ "<th>Имя</th>" 
						+ "<th>Фамилия</th>"
						+ "<th>email</th>" 
						+ "<th>Логин</th>" 
						+ "<th>Роль</th>" 
						+ this.BuildEditRemoveColumns_Header(is_admin)
						+ "</tr>");

		for (Users userRow : users) {

			user_id = userRow.getIdUser();
			user_fn = userRow.getFirstName();
			user_ln = userRow.getLastName();
			user_email = (userRow.getMail() == null ? "" : userRow.getMail());
			user_login = userRow.getLogin();
			user_role = (userRow.getRoles().getRoleName() == null ? "" : userRow.getRoles().getRoleName() );

			user_TR_tag = String.format(
					"<tr>" 
							+ "<td>%d</td>" 
							+ "<td>%s</td>" 
							+ "<td>%s</td>" 
							+ "<td>%s</td>" 
							+ "<td>%s</td>"
							+ "<td>%s</td>" 
							+ this.BuildEditRemoveColumns_Cell(is_admin, "User")
							+ "</tr>",
					user_id, user_fn, user_ln, user_email, user_login, user_role, user_id, user_id);

			response.getWriter().append(user_TR_tag);

		}
		response.getWriter().append("</table>");
	}
	
	/////////////////////////////////
	//// Build Attraction list //////
	/////////////////////////////////
	private void BuildAttractionList(HttpServletResponse response, boolean is_admin) throws IOException {

		Attraction att = new Attraction();
		List<Attractions> attractions = att.getAttractionList();

		int attraction_id = -1;
		String attraction_nm = null;
		String attraction_descr = null;
		String attraction_city = null;
		String attraction_TR_tag = null;

		response.getWriter()
				.append("<h2 id=\"attractions\">Достопримечательности</h2>"
						+ (is_admin ? "<a href=\"AttractionAdd\">Добавить достопримечательность</a><br><br>" : "")
						+ "<table border=\"0\" cellspacing=\"0\" cellpadding=\"10\">"
						+ "<tr bgcolor=\"#EBEBEB\">"
						+ "<th>ID</th>"
						+ "<th>Название</th>"
						+ "<th>Описание</th>"
						+ "<th>Город</th>"
						+ this.BuildEditRemoveColumns_Header(is_admin)
						+ "</tr>");

		for (Attractions attractionRow : attractions) {

			attraction_id = attractionRow.getIdAttraction();
			attraction_nm = attractionRow.getAttractionName();
			attraction_descr = attractionRow.getAttDescription();
			attraction_city = attractionRow.getCities().getCityName();

			attraction_TR_tag = String.format(
					"<tr>"
						+ "<td>%d</td>"
						+ "<td>%s</td>"
						+ "<td>%s</td>"
						+ "<td>%s</td>"
						+ this.BuildEditRemoveColumns_Cell(is_admin, "Attraction")
						+ "</tr>",
						attraction_id, attraction_nm, attraction_descr, attraction_city, attraction_id, attraction_id);

			response.getWriter().append(attraction_TR_tag);

		}
		response.getWriter().append("</table>");
	}
	
	/////////////////////////////////
	//// Build Restaurant list //////
	/////////////////////////////////
	private void BuildRestaurantList(HttpServletResponse response, boolean is_admin) throws IOException {
	
		Restaurant r = new Restaurant();
		List<Restaurants> restaurants = r.getRestaurantList();
		
		int restaurant_id = -1;
		String restaurant_nm = null;
		String restaurant_address = null;
		BigDecimal restaurant_rating = null;
		String restaurant_website = null;
		String restaurant_city = null;
		String restaurant_TR_tag = null;
		
		response.getWriter()
		.append("<h2 id=\"restaurants\">Рестораны</h2>"
				+ (is_admin ? "<a href=\"RestaurantAdd\">Добавить ресторан</a><br><br>" : "")
				+ "<table border=\"0\" cellspacing=\"0\" cellpadding=\"10\">"
				+ "<tr bgcolor=\"#ACC6C6\">"
				+ "<th>ID</th>"
				+ "<th>Название</th>"
				+ "<th>Адрес</th>"
				+ "<th>Рейтинг</th>"
				+ "<th>Веб-сайт</th>"
				+ "<th>Город</th>"
				+ this.BuildEditRemoveColumns_Header(is_admin)
				+ "</tr>");
		
		for (Restaurants restaurantRow : restaurants) {
		
			restaurant_id = restaurantRow.getIdRestaurant();
			restaurant_nm = restaurantRow.getRestName();
			restaurant_address = restaurantRow.getAddress();
			restaurant_rating = restaurantRow.getRating();
			restaurant_website = (restaurantRow.getWebsite() == null ? "" : restaurantRow.getWebsite());
			restaurant_city = restaurantRow.getCities().getCityName();
		
				restaurant_TR_tag = String.format(
			"<tr>"
			+ "<td>%d</td>"
			+ "<td>%s</td>"
			+ "<td>%s</td>"
			+ "<td>%s</td>"
			+ "<td>%s</td>"
			+ "<td>%s</td>"
			+ this.BuildEditRemoveColumns_Cell(is_admin, "Restaurant")
			+ "</tr>",
			restaurant_id, restaurant_nm, restaurant_address, restaurant_rating, restaurant_website, restaurant_city, restaurant_id, restaurant_id);
			
			response.getWriter().append(restaurant_TR_tag);
		
		}
			response.getWriter().append("</table>");
	}
	
	/////////////////////////////////
	//// Build Hotel list //////
	/////////////////////////////////
	private void BuildHotelList(HttpServletResponse response, boolean is_admin) throws IOException {
		
		Hotel h = new Hotel();
		List<Hotels> hotels = h.getHotelList();
		
		int hotel_id = -1;
		String hotel_nm = null;
		String hotel_address = null;
		BigDecimal hotel_rating = null;
		String hotel_stars = null;
		String hotel_website = null;
		String hotel_city = null;
		String hotel_TR_tag = null;
		
		response.getWriter()
		.append("<h2 id=\"hotels\">Список отелей</h2>"
				+ (is_admin ? "<a href=\"HotelAdd\">Добавить отель</a><br><br>" : "")
				+ "<table border=\"0\" cellspacing=\"0\" cellpadding=\"10\">"
				+ "<tr bgcolor=\"#EBEBEB\">"
				+ "<th>ID</th>"
				+ "<th>Название</th>"
				+ "<th>Адрес</th>"
				+ "<th>Рейтинг</th>"
				+ "<th>Количество звезд</th>"
				+ "<th>Веб-сайт</th>"
				+ "<th>Город</th>"
				+ this.BuildEditRemoveColumns_Header(is_admin)
				+ "</tr>");
		
		for (Hotels hotelRow : hotels) {
		
			hotel_id = hotelRow.getIdHotel();
			hotel_nm = hotelRow.getHotelName();
			hotel_address = hotelRow.getAddress();
			hotel_rating = hotelRow.getRating();
			hotel_stars = (hotelRow.getAmountOfStars() == null ? "" : Integer.toString(hotelRow.getAmountOfStars()));
			hotel_website = (hotelRow.getWebsite() == null ? "" : hotelRow.getWebsite());
			hotel_city = hotelRow.getCities().getCityName();
			
			hotel_TR_tag = String.format(
			"<tr>"
			+ "<td>%d</td>"
			+ "<td>%s</td>"
			+ "<td>%s</td>"
			+ "<td>%s</td>"
			+ "<td>%s</td>"
			+ "<td>%s</td>"
			+ "<td>%s</td>"
			+ this.BuildEditRemoveColumns_Cell(is_admin, "Hotel")
			+ "</tr>",
			hotel_id, hotel_nm, hotel_address, hotel_rating, hotel_stars, hotel_website, hotel_city, hotel_id, hotel_id);
			
			response.getWriter().append(hotel_TR_tag);
		
		}
		response.getWriter().append("</table>");
	}
	
	/////////////////////////////////
	//// Build Country list //////
	/////////////////////////////////
	private void BuildCountryList(HttpServletResponse response, boolean is_admin) throws IOException {
	
		Country cntr = new Country();
		List<Countries> countries = cntr.getCountryList();
		
		int country_id = -1;
		String country_nm = null;
		String country_capt = null;
		int country_pop = -1;
		int country_ar = -1;
		String country_cont = null;
		String country_TR_tag = null;
		
		response.getWriter()
			.append("<h2 id=\"countries\">Страны</h2>"
			+ (is_admin ? "<a href=\"CountryAdd\">Добавить страну</a><br><br>" : "" )
			+ "<table border=\"0\" cellspacing=\"0\" cellpadding=\"10\">"
			+ "<tr bgcolor=\"#EBEBEB\">"
			+ "<th>ID</th>"
			+ "<th>Название</th>"
			+ "<th>Столица</th>"
			+ "<th>Население (млн)</th>"
			+ "<th>Площадь (км.кв.)</th>"
			+ "<th>Континент</th>"
			+ this.BuildEditRemoveColumns_Header(is_admin)
			+ "</tr>");
		
		for (Countries countryRow : countries) {
		
			country_id = countryRow.getIdCountry();
			country_nm = countryRow.getCountryName();
			country_capt = countryRow.getCapital();
			country_pop = countryRow.getPopulation();
			country_ar = countryRow.getArea();
			country_cont = countryRow.getContinents().getContinentName();
			
			country_TR_tag = String.format(
				"<tr>"
				+ "<td>%d</td>"
				+ "<td>%s</td>"
				+ "<td>%s</td>"
				+ "<td>%s</td>"
				+ "<td>%s</td>"
				+ "<td>%s</td>"
				+ this.BuildEditRemoveColumns_Cell(is_admin, "Country")
				+ "</tr>",
				country_id, country_nm, country_capt, country_pop, country_ar, country_cont, country_id, country_id);
			
			response.getWriter().append(country_TR_tag);
		
		}
		response.getWriter().append("</table>");
	}
	
	/////////////////////////////////
	//// Build City list //////
	/////////////////////////////////
	private void BuildCityList(HttpServletResponse response, boolean is_admin) throws IOException {
	
		City ct = new City();
		List<Cities> cities = ct.getCityList();
		
		int city_id = -1;
		String city_nm = null;
		String city_country = null;
		String city_TR_tag = null;
		
		response.getWriter()
			.append("<h2 id=\"cities\">Города</h2>"
					+ (is_admin ? "<a href=\"CityAdd\">Добавить город</a><br><br>" : "")
					+ "<table border=\"0\" cellspacing=\"0\" cellpadding=\"10\">"
					+ "<tr bgcolor=\"#EBEBEB\">"
					+ "<th>ID</th>"
					+ "<th>Название</th>"
					+ "<th>Страна</th>"
					+ this.BuildEditRemoveColumns_Header(is_admin)
					+ "</tr>");
		
		for (Cities cityRow : cities) {
		
			city_id = cityRow.getIdCity();
			city_nm = cityRow.getCityName();
			city_country = cityRow.getCountries().getCountryName();
			
			city_TR_tag = String.format(
				"<tr>"
					+ "<td>%d</td>"
					+ "<td>%s</td>"
					+ "<td>%s</td>"
					+ this.BuildEditRemoveColumns_Cell(is_admin, "City")
					+ "</tr>",
					city_id, city_nm, city_country, city_id, city_id);
			
			response.getWriter().append(city_TR_tag);
		
		}
		response.getWriter().append("</table>");
	}
	
	/////////////////////////////////
	//// Build Continent list //////
	/////////////////////////////////
	private void BuildContinentList(HttpServletResponse response, boolean is_admin) throws IOException {
	
		Continent cont = new Continent();
		List<Continents> continents = cont.getContinentList();
		
		int continent_id = -1;
		String continent_nm = null;
		String continent_TR_tag = null;
		
		response.getWriter()
			.append("<h2 id=\"continents\">Континенты</h2>"
				+ "<table border=\"0\" cellspacing=\"0\" cellpadding=\"10\">"
				+ "<tr bgcolor=\"#EBEBEB\">"
				+ "<th>ID</th>"
				+ "<th>Название</th>"
				+ this.BuildEditRemoveColumns_Header(is_admin)
				+ "</tr>");
		
		for (Continents continentRow : continents) {
		
			continent_id = continentRow.getIdContinent();
			continent_nm = continentRow.getContinentName();
			
			continent_TR_tag = String.format(
				"<tr>"
					+ "<td>%d</td>"
					+ "<td>%s</td>"
					+ this.BuildEditRemoveColumns_Cell(is_admin, "Continent")
					+ "</tr>",
					continent_id, continent_nm, continent_id, continent_id);
			
			response.getWriter().append(continent_TR_tag);
		
		}
		response.getWriter().append("</table>");
	}
	
	/////////////////////////////////
	//// Build Cuisine list //////
	/////////////////////////////////
	private void BuildCuisineList(HttpServletResponse response, boolean is_admin) throws IOException {
	
		Cuisenee cuis = new Cuisenee();
		List<Cuisine> cuisine = cuis.getCuisineList();
		
		int cuisine_id = -1;
		String cuisine_t = null;
		String cuisine_TR_tag = null;
		
		response.getWriter()
			.append("<h2 id=\"cuisine\">Виды кухни</h2>"
					+ (is_admin ? "<a href=\"CuisineAdd\">Добавить вид кухни</a><br><br>" : "")
					+ "<table border=\"0\" cellspacing=\"0\" cellpadding=\"10\">"
					+ "<tr bgcolor=\"#EBEBEB\">"
					+ "<th>ID</th>"
					+ "<th>Название</th>"
					+ this.BuildEditRemoveColumns_Header(is_admin)
					+ "</tr>");
		
		for (Cuisine сuisineRow : cuisine) {
		
			cuisine_id = сuisineRow.getIdCuisine();
			cuisine_t = сuisineRow.getCuisineType();
			
			cuisine_TR_tag = String.format(
				"<tr>"
					+ "<td>%d</td>"
					+ "<td>%s</td>"
					+ this.BuildEditRemoveColumns_Cell(is_admin, "Cuisine")
					+ "</tr>",
					cuisine_id, cuisine_t, cuisine_id, cuisine_id);
			
			response.getWriter().append(cuisine_TR_tag);
		
		}
		response.getWriter().append("</table>");
	}

}
