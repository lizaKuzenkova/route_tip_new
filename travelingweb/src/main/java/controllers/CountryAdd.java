package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbaccess.Country;
import web.SessionHelper;
import web.Utility;

public class CountryAdd extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String country_name = null;
		String country_capital = null;
		String country_population = null;
		String country_area = null;
		String country_continent = null;
		String headerSuffix = "Страны";
		String resp = null;

		String header = "<html><head><title>RouteTips - %s</title></head>"
				+ "<link href=\"css/admin_add.css\" rel=\"stylesheet\" type=\"text/css\">"
				+ "<body>";
		
		String continents_list = Utility.BuildDropDownContinents(response);
		
		String body = "<h1>%s</h1>"
				+ "<form>"
				+ "<table>"
				+ "<tr>"
				+ "<td><label for=\"country_name\">Страна</label></td>"
				+ "<td><input type=\"text\" id=\"country_name\" name=\"country_name\" value=\"%s\"></td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td><label for=\"country_capital\">Столица</label></td>"
				+ "<td><input type=\"text\" id=\"country_capital\" name=\"country_capital\" value=\"%s\"></td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td><label for=\"country_population\">Население</label></td>"
				+ "<td><input type=\"text\" id=\"country_population\" name=\"country_population\" value=\"%s\"></td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td><label for=\"country_area\">Площадь</label></td>"
				+ "<td><input type=\"text\" id=\"country_area\" name=\"country_area\" value=\"%s\"></td>"
				+ "</tr>"
				+ "<td><label for=\"country_continent\">Континент</label></td>"
				+ "<td>%s</td>"
				+ "<tr>"
				+ "<td colspan=2 align=\"center\"><input type=\"submit\" value=\"Подтвердить\"></td>"
				+ "</tr>";

		String footer = "</table></form></body></html>";
				
		response.setContentType("text/html; charset=UTF-8");

		response.getWriter().append(String.format(header, headerSuffix));
		SessionHelper.typeSessionHeader(request, response);
		response.getWriter().append(String.format(body, headerSuffix, "", "", "", "", continents_list));
		response.getWriter().append(footer);
		
		country_name = request.getParameter("country_name");
		country_capital = request.getParameter("country_capital");
		country_population = request.getParameter("country_population");
		country_area = request.getParameter("country_area");
		country_continent = request.getParameter("selected_continent");
		
		if (country_name == null)
			country_name = "";
		

		if (country_name != "") {
			Country c = new Country();
			resp = c.createCountry(country_name, country_capital, Integer.parseInt(country_population), Integer.parseInt(country_area), country_continent);
			response.getWriter().append("<br><br><h2>" + resp
					+ "</h2><br><br><form action=\"/travelingweb/UserAdministration\"><input type=\"submit\" value=\"Продолжить\">");
		} 
	}
}
