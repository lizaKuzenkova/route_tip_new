package controllers;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbaccess.Attraction;
import dbaccess.Hotel;
import web.SessionHelper;
import web.Utility;

/**
 * Servlet implementation class HotelAdd
 */
public class HotelAdd extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String hotel_name = null;
		String hotel_addr = null;
		String hotel_rat = null;
		String hotel_stars = null;
		String hotel_website = null;
		String hotel_city = null;
		String headerSuffix = "Отели";
		String resp = null;

		String header = "<html><head><title>RouteTips - %s</title></head>"
				+ "<body>";
		
		String cities_list = Utility.BuildDropDownCities(response);
		
		String body = "<h1>%s</h1>"
				+ "<form>"
				+ "<table>"
				+ "<tr>"
				+ "<td><label for=\"hotel_name\">Отель</label></td>"
				+ "<td><input type=\"text\" id=\"hotel_name\" name=\"hotel_name\" value=\"%s\"></td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td><label for=\"hotel_addr\">Адрес</label></td>"
				+ "<td><input type=\"text\" id=\"hotel_addr\" name=\"hotel_addr\" value=\"%s\"></td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td><label for=\"hotel_rat\">Рейтинг</label></td>"
				+ "<td><input type=\"text\" id=\"hotel_rat\" name=\"hotel_rat\" value=\"%s\"></td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td><label for=\"hotel_stars\">Количество звезд</label></td>"
				+ "<td><input type=\"text\" id=\"hotel_stars\" name=\"hotel_stars\" value=\"%s\"></td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td><label for=\"hotel_website\">Веб-сайт</label></td>"
				+ "<td><input type=\"text\" id=\"hotel_website\" name=\"hotel_website\" value=\"%s\"></td>"
				+ "</tr>"
				+ "<td><label for=\"hotel_city\">Город</label></td>"
				+ "<td>%s</td>"
				+ "<tr>"
				+ "<td colspan=2 align=\"center\"><input type=\"submit\" value=\"Подтвердить\"></td>"
				+ "</tr>";

		String footer = "</table></form></body></html>";
				
		response.setContentType("text/html; charset=UTF-8");

		response.getWriter().append(String.format(header, headerSuffix));
		SessionHelper.typeSessionHeader(request, response);
		response.getWriter().append(String.format(body, headerSuffix, "", "", "", "", "", cities_list));
		response.getWriter().append(footer);
		
		hotel_name = request.getParameter("hotel_name");
		hotel_addr = request.getParameter("hotel_addr");
		hotel_rat = request.getParameter("hotel_rat");
		hotel_stars = request.getParameter("hotel_stars");
		hotel_website = request.getParameter("hotel_website");
		hotel_city = request.getParameter("selected_city");
		
		if (hotel_name == null)
			hotel_name = "";
		

		if (hotel_name != "") {
			Hotel h = new Hotel();
			resp = h.createHotel(hotel_name, hotel_addr, new BigDecimal(hotel_rat), Integer.parseInt(hotel_stars), hotel_website, hotel_city);
			response.getWriter().append("<br><br><h2>" + resp
					+ "</h2><br><br><form action=\"/travelingweb/UserAdministration\"><input type=\"submit\" value=\"Продолжить\">");
		} 
	}
}
