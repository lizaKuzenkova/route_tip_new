package controllers;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbaccess.Hotel;
import dbaccess.Restaurant;
import web.SessionHelper;
import web.Utility;

/**
 * Servlet implementation class RestaurantAdd
 */
public class RestaurantAdd extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String restaurant_name = null;
		String restaurant_addr = null;
		String restaurant_rat = null;
		String restaurant_website = null;
		String restaurant_city = null;
		String headerSuffix = "Рестораны";
		String resp = null;

		String header = "<html><head><title>RouteTips - %s</title></head>"
				+ "<body>";
		
		String cities_list = Utility.BuildDropDownCities(response);
		
		String body = "<h1>%s</h1>"
				+ "<form>"
				+ "<table>"
				+ "<tr>"
				+ "<td><label for=\"restaurant_name\">Ресторан</label></td>"
				+ "<td><input type=\"text\" id=\"restaurant_name\" name=\"restaurant_name\" value=\"%s\"></td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td><label for=\"restaurant_addr\">Адрес</label></td>"
				+ "<td><input type=\"text\" id=\"restaurant_addr\" name=\"restaurant_addr\" value=\"%s\"></td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td><label for=\"restaurant_rat\">Рейтинг</label></td>"
				+ "<td><input type=\"text\" id=\"restaurant_rat\" name=\"restaurant_rat\" value=\"%s\"></td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td><label for=\"restaurant_website\">Веб-сайт</label></td>"
				+ "<td><input type=\"text\" id=\"restaurant_website\" name=\"restaurant_website\" value=\"%s\"></td>"
				+ "</tr>"
				+ "<td><label for=\"restaurant_city\">Город</label></td>"
				+ "<td>%s</td>"
				+ "<tr>"
				+ "<td colspan=2 align=\"center\"><input type=\"submit\" value=\"Подтвердить\"></td>"
				+ "</tr>";

		String footer = "</table></form></body></html>";
				
		response.setContentType("text/html; charset=UTF-8");

		response.getWriter().append(String.format(header, headerSuffix));
		SessionHelper.typeSessionHeader(request, response);
		response.getWriter().append(String.format(body, headerSuffix, "", "", "", "", cities_list));
		response.getWriter().append(footer);
		
		restaurant_name = request.getParameter("restaurant_name");
		restaurant_addr = request.getParameter("restaurant_addr");
		restaurant_rat = request.getParameter("restaurant_rat");
		restaurant_website = request.getParameter("restaurant_website");
		restaurant_city = request.getParameter("selected_city");
		
		if (restaurant_name == null)
			restaurant_name = "";
		

		if (restaurant_name != "") {
			Restaurant r = new Restaurant();
			resp = r.createRestaurant(restaurant_name, restaurant_addr, new BigDecimal(restaurant_rat), restaurant_website, restaurant_city);
			response.getWriter().append("<br><br><h2>" + resp
					+ "</h2><br><br><form action=\"/travelingweb/UserAdministration\"><input type=\"submit\" value=\"Продолжить\">");
		} 
	}
}
