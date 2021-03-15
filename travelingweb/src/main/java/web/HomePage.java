package web;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbaccess.*;
import dbmodel.*;

public class HomePage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String SEARCH = "/search";
	private static final String DIRECTION = "/dir";

	String place_whole_tr = null;
	String place_a_tag = null;
	String place_name = null;
	String place_desc = null;
	String place_address = null;
	String place_web_site = null;
	BigDecimal place_rating = null;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String target_city = request.getParameter("targetCity");
//		String target_city = request.getParameter("selected_city");

		if (target_city == null)
			target_city = "";

		String target_country = this.GetCountry(target_city);

		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().append("<html><head><title>RouteTips - Главная страница</title></head>"
				+ "<link href=\"css/home_page.css\" rel=\"stylesheet\" type=\"text/css\">"
				+ "<body style=\"background-image:url(images/home_page.jpg); background-size:cover\">");
		
		SessionHelper.typeSessionHeader(request, response, "header-link-white");
		
		if (SessionHelper.IsAdmin(request)) {
			response.getWriter()
					.append("<html><head><title>RouteTips - Администрирование</title></head><body><form>"
							+ "<link href=\"css/style.css\" rel=\"stylesheet\" type=\"text/css\">"
							+ "<body>"
							+ "<form>"
							+ "<div><nav>"
							+ "<ul class=\"topmenu\">"						
								+ "<li><class=\"dropdown\">Администрирование</a>"
									+ "<ul class=\"submenu\">"
										+ "<li><a href=\"UserAdministration#users\">Пользователи</a><br><br></li>"
										+ "<li><a href=\"UserAdministration#attractions\">Достопримечательности</a><br><br></li>"
										+ "<li><a href=\"UserAdministration#hotels\">Отели</a><br><br></li>"
										+ "<li><a href=\"UserAdministration#restaurants\">Рестораны</a><br><br></li>"
									+ "</ul>"
								+ "</li>"
								+ "<li><class=\"dropdown\">Справочники</a>"
									+ "<ul class=\"submenu\">"
										+ "<li><a href=\"UserAdministration#continents\">Континенты</a><br><br></li>"
										+ "<li><a href=\"UserAdministration#countries\">Страны</a><br><br></li>"
										+ "<li><a href=\"UserAdministration#cities\">Города</a><br><br></li>"
										+ "<li><a href=\"UserAdministration#cuisine\">Виды кухни</a><br><br></li>"							
										+ "<li><a href=\"UserAdministration#roles\">Роли пользователей</a><br><br></li>"
									+ "</ul>"
								+ "</li>"	
								+ "<li><class=\"dropdown\">Сервис</a>"
									+ "<ul class=\"submenu\">"
										+ "<li><a href=\"Statistics\">Статистика</a><br><br></li>"
										+ "<li><a href=\"HomePage\">Составление маршрута</a><br><br></li>"
									+ "</ul>"
								+ "</li>"
							+ "</ul></nav></div>");

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

			response.getWriter().append("</form></body></html>");
		} 

		response.getWriter().append("<form style=\"width:1250px; margin:100px auto\">"
				+ "<div style=\"font-family: Helvetica, sans-serif; font-size:18px\" align=\"center\"><label style=\"margin-right:10px\">Выберите город</label>  ");

		response.getWriter().append("<input type=\"text\" name=\"targetCity\" value=\"" + target_city + "\" >");
//		response.getWriter().append(Utility.BuildDropDownCities(response));
		
		response.getWriter().append("  <input type=\"submit\" value=\"Искать\"></div>");
				
		if (target_city != "") {
			
			response.getWriter().append(String.format(
					"<div align=\"center\"><h1><a href=\"https://www.google.com/maps%s/%s+%s\" target=\"_blank\" style=\"color:#242528\">%s</a></h1></div>",
					SEARCH, target_city, target_country, target_city));
			
			this.BuildAttractions(response, target_city);
			this.BuildRestaurants(response, target_city);
			this.BuildHotels(response, target_city);

			response.getWriter().append("<br><br><div align=\"center\"><input type=\"submit\" value=\"Построить маршрут\" name=\"buildRoute\"></div>");
		}
		
		this.BuildRoute(request, response, target_city);

		response.getWriter().append("</form>");
		response.getWriter().append("</body>");
		response.getWriter().append("</html>");
	}

	
	///////////////////////////
	//// Get Country //////////
	///////////////////////////
	private String GetCountry(String target_city) {
		Country c = new Country();
		return c.getCountryByCityName(target_city);
	}
	
	///////////////////////////
	//// Build Attractions ////
	///////////////////////////
	private void BuildAttractions (HttpServletResponse response, String target_city) throws IOException {
		
		Attraction attraction = new Attraction();
		List<Attractions> AttractionData = attraction.getAttractionsByCityName(target_city);

		response.getWriter()
				.append(
						"<h2>Достопримечательности</h2>"
						+ "<table style=\"background-color:#B6BFC6\" border=\"0\" cellspacing=\"0\" cellpadding=\"10\" width=\"100%\">"
						+ "<tr bgcolor=\"#EA9744\">"
						+ "<th>Выбор</th>"
						+ "<th>Достопримечательность</th>"
						+ "<th>Описание</th>"
						+ "</tr>");

		for (Attractions attractionRow : AttractionData) {
			place_name = attractionRow.getAttractionName();
			place_desc = attractionRow.getAttDescription();
			place_a_tag = String.format(
					"<a href=\"https://www.google.com/maps/search/%s+%s\" target=\"_blank\" style=\"color:#242528\">%s</a>",
					place_name, target_city, place_name);
			place_whole_tr = "<tr>"
					+ "<td><input type=\"checkbox\" name=\"checkbox_attraction_id\" value=\"" + place_name + "\"></td>"
					+ "<td>" + place_a_tag + "</td>"
					+ "<td>" + place_desc + "</td>"
					+ "</tr>";
			response.getWriter().append(place_whole_tr);
			System.out.println(place_a_tag);
		}
		response.getWriter().append("</table>");		
	}
	
	///////////////////////////
	//// Build Restaurants ////
	///////////////////////////
	private void BuildRestaurants (HttpServletResponse response, String target_city) throws IOException {

		String place_cuisineType_br = null;
		
		Restaurant restaurant = new Restaurant();
		List<Restaurants> RestaurantData = restaurant.getRestaurantsByCityName(target_city);

		response.getWriter()
		.append(
				"<h2 style=\"color:#D6D7C7\">Рестораны</h2>"
				+ "<table style=\"background-color:#B6BFC6\" border=\"0\" cellspacing=\"0\" cellpadding=\"10\" width=\"100%\">"
				+ "<tr bgcolor=\"#EA9744\">"
				+ "<th>Выбор</th>"
				+ "<th>Ресторан</th>"
				+ "<th>Кухня</th>"
				+ "<th>Адрес</th>"
				+ "<th>Рейтинг</th>"
				+ "</tr>");
		
		for (Restaurants restaurantRow : RestaurantData) {
			place_name = restaurantRow.getRestName();
			place_address = restaurantRow.getAddress();
			place_rating = restaurantRow.getRating();
			
			place_cuisineType_br = "";
			for (RestCuis cus : restaurantRow.getRestCuises()) {
				if (restaurantRow.getIdRestaurant() == cus.getRestaurants().getIdRestaurant()) {
					place_cuisineType_br = place_cuisineType_br + cus.getCuisine().getCuisineType() + "<br>";
				}
			}

			place_a_tag = String.format(
					"<a href=\"https://www.google.com/maps/search/%s+%s\" target=\"_blank\" style=\"color:#242528\">%s</a>",
					place_name, target_city, place_name);
			place_whole_tr = "<tr>"
					+ "<td><input type=\"checkbox\" name=\"checkbox_restaurant_id\" value=\"" + place_name + "\"></td>"
					+ "<td>" + place_a_tag + "</td>"
					+ "<td>" + place_cuisineType_br + "</td>"
					+ "<td>" + place_address + "</td>"
					+ "<td>" + place_rating + "</td>"
					+ "</tr>";
			response.getWriter().append(place_whole_tr);
			System.out.println(place_a_tag);
		}
		response.getWriter().append("</table>");
	}

	///////////////////////////
	//// Build Hotels /////////
	///////////////////////////
	private void BuildHotels (HttpServletResponse response, String target_city) throws IOException {
		
		int place_stars = -1;
		
		Hotel hotel = new Hotel();
		List<Hotels> HotelData = hotel.getHotelsByCityName(target_city);
		
		response.getWriter()
		.append("<h2 style=\"color:#D6D7C7\">Отели и гостиницы</h2>"
				+ "<table style=\"background-color:#B6BFC6\" border=\"0\" cellspacing=\"0\" cellpadding=\"10\" width=\"100%\">"
				+ "<tr bgcolor=\"#EA9744\">"
				+ "<th>Выбор</th>"
				+ "<th>Отель/Гостиница</th>"
				+ "<th>Адрес</th>"
				+ "<th>Рейтинг</th>"
				+ "<th>Звёзды</th>"
				+ "<th>Сайт</th>"
				+ "</tr>");
		
		for (Hotels hotelRow : HotelData) {
			place_name = hotelRow.getHotelName();
			place_address = hotelRow.getAddress();
			place_rating = hotelRow.getRating();
			place_stars = hotelRow.getAmountOfStars();
			place_web_site = "<a href=\"http://" + hotelRow.getWebsite() + "\" target=\"_blank\">" + hotelRow.getWebsite() + "</a>";
			
			place_a_tag = String.format(
					"<a href=\"https://www.google.com/maps/search/%s+%s\" target=\"_blank\" style=\"color:#242528\">%s</a>",
					place_name, target_city, place_name);
			place_whole_tr = "<tr>"
					+ "<td><input type=\"checkbox\" name=\"checkbox_hotel_id\" value=\"" + place_name + "\"></td>"
					+ "<td>" + place_a_tag + "</td>"
					+ "<td>" + place_address + "</td>"
					+ "<td>" + place_rating + "</td>"
					+ "<td>" + place_stars + "</td>"
					+ "<td>" + place_web_site + "</td>"
					+ "</tr>";
			response.getWriter().append(place_whole_tr);
		}
		response.getWriter().append("</table>");
	}
	
	/////////////////////
	//// Build route ////
	/////////////////////
	private void BuildRoute (HttpServletRequest request, HttpServletResponse response, String target_city) throws IOException {

		String PLACE_OR_ROUTE = null;
		String route_url_main = "https://www.google.com/maps";
		String route_url = "";
		int no_of_selected_places = 0;

		String build_route = request.getParameter("buildRoute");

		if (build_route == null)
			build_route = "";
		
		if (build_route != "") {
			
			String[] checkbox_place_ids = null;
			
			checkbox_place_ids = request.getParameterValues("checkbox_attraction_id");
			Integer user_id = Integer.parseInt(SessionHelper.getUserId(request));

			if (checkbox_place_ids != null) {
				if (checkbox_place_ids.length > 0) {
					for (int p = 0; p < checkbox_place_ids.length; p++) {
						route_url = route_url + "/" + checkbox_place_ids[p] + "+" + target_city;
						no_of_selected_places++;
					}
					route_url = route_url.replace(" ", "+");
					Stats.collectStats(checkbox_place_ids, Stats.PLACE_TYPE.ATTRACTION, user_id);
				}
			}
			
			checkbox_place_ids = request.getParameterValues("checkbox_hotel_id");

			if (checkbox_place_ids != null) {
				if (checkbox_place_ids.length > 0) {
					for (int p = 0; p < checkbox_place_ids.length; p++) {
						route_url = route_url + "/" + checkbox_place_ids[p] + "+" + target_city;
						no_of_selected_places++;
					}
					route_url = route_url.replace(" ", "+");
					Stats.collectStats(checkbox_place_ids, Stats.PLACE_TYPE.HOTEL, user_id);
				}
			}

			checkbox_place_ids = request.getParameterValues("checkbox_restaurant_id");

			if (checkbox_place_ids != null) {
				if (checkbox_place_ids.length > 0) {
					for (int p = 0; p < checkbox_place_ids.length; p++) {
						route_url = route_url + "/" + checkbox_place_ids[p] + "+" + target_city;
						no_of_selected_places++;
					}
					route_url = route_url.replace(" ", "+");
					Stats.collectStats(checkbox_place_ids, Stats.PLACE_TYPE.RESTAURANT, user_id);
				}
			}

			
			if (no_of_selected_places > 0) {
				if (no_of_selected_places == 1) {
					route_url = route_url_main + SEARCH + route_url;
					PLACE_OR_ROUTE = "место";
				} else {
					route_url = route_url_main + DIRECTION + route_url;
					PLACE_OR_ROUTE = "маршрут";
				}
				response.getWriter().append("<div align=\"center\"><h1><a href=\"" + route_url
						+ "\" target=\"_blank\" style=\"color:#D6D7C7\">Показать " + PLACE_OR_ROUTE + "</a></h1></div>");
			}
			else {
				response.getWriter().append("<div align=\"center\"><h1>Ничего не выбрано</h1></div>");
			}
		}		
	}
}
