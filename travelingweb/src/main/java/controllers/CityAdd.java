package controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbaccess.City;
import web.SessionHelper;
import web.Utility;

/**
 * Servlet implementation class CityAdd
 */
public class CityAdd extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String city_name = null;
		String city_country = null;
		String headerSuffix = "Города";
		String resp = null;

		String header = "<html><head><title>RouteTips - %s</title></head>"
				+ "<body>";
		
		String countries_list = Utility.BuildDropDownCountries(response);
		
		String body = "<h1>%s</h1>"
				+ "<form>"
				+ "<table>"
				+ "<tr>"
				+ "<td><label for=\"city_name\">Город</label></td>"
				+ "<td><input type=\"text\" id=\"city_name\" name=\"city_name\" value=\"%s\"></td>"
				+ "</tr>"
				+ "<td><label for=\"city_country\">Страна</label></td>"
				+ "<td>%s</td>"
				+ "<tr>"
				+ "<td colspan=2 align=\"center\"><input type=\"submit\" value=\"Подтвердить\"></td>"
				+ "</tr>";

		String footer = "</table></form></body></html>";
				
		response.setContentType("text/html; charset=UTF-8");

		response.getWriter().append(String.format(header, headerSuffix));
		SessionHelper.typeSessionHeader(request, response);
		response.getWriter().append(String.format(body, headerSuffix, "", countries_list));
		response.getWriter().append(footer);
		
		city_name = request.getParameter("city_name");
		city_country = request.getParameter("selected_country");
		
		if (city_name == null)
			city_name = "";
		

		if (city_name != "") {
			City c = new City();
			resp = c.createCity(city_name, city_country);
			response.getWriter().append("<br><br><h2>" + resp
					+ "</h2><br><br><form action=\"/travelingweb/UserAdministration\"><input type=\"submit\" value=\"Продолжить\">");
		} 
	}
}
