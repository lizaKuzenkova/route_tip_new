package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbaccess.Country;
import dbmodel.Countries;
import web.SessionHelper;

public class CountryUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html; charset=UTF-8");

		if (SessionHelper.IsAdmin(request)) {
			String country_id = request.getParameter("country_id");

			if (country_id == "" || country_id == null)
				request.getRequestDispatcher("/").forward(request, response);
			else {
				int countryId = Integer.parseInt(country_id);
				Country cntr = new Country();
				Countries country = cntr.getCountryByCountryId(countryId);

				String header = "<html><head><title>RouteTips - Изменение страны</title></head>"
					+ "<body>";
    		
				String body = "<h1>Изменение страны</h1>"
    				+ "<form>"
    				+ "<table>"
    				+ "<tr>"
    				+ "<td><label for=\"countryId\">Идентификатор страны</label></td>"
    				+ "<td><input type=\"text\" id=\"countryId\" name=\"countryId\" value=\"%d\" readonly></td>"
    				+ "</tr>"
    				+ "<tr>"
    				+ "<td><label for=\"cntrname\">Название</label></td>"
    				+ "<td><input type=\"text\" id=\"cntrname\" name=\"cntrname\" value=\"%s\"></td>"
    				+ "</tr>"
    				+ "<tr>"
    				+ "<td><label for=\"cntrcapt\">Столица</label></td>"
    				+ "<td><input type=\"text\" id=\"cntrcapt\" name=\"cntrcapt\" value=\"%s\"></td>"
    				+ "</tr>"
    				+ "<tr>"
    				+ "<td><label for=\"cntrpop\">Население</label></td>"
    				+ "<td><input type=\"text\" id=\"cntrpop\" name=\"cntrpop\" value=\"%s\"></td>"
    				+ "</tr>"
    				+ "<tr>"
    				+ "<td><label for=\"cntrar\">Площадь</label></td>"
    				+ "<td><input type=\"text\" id=\"cntrar\" name=\"cntrar\" value=\"%s\"></td>"
    				+ "</tr>"
    				+ "<tr>"
    				+ "<td colspan=2 align=\"center\"><br><br><input type=\"submit\" value=\"Изменить\" formaction=\"CountryUpdateAct\"></td>"
    				+ "</tr>";

				String footer = "</table></form></body></html>";

				response.getWriter().append(String.format(header));
				SessionHelper.typeSessionHeader(request, response);
				response.getWriter().append(String.format(body, countryId, country.getCountryName(), country.getCapital(), country.getPopulation(), country.getArea()));
				response.getWriter().append(footer);
			}
		} else {
			response.getWriter().append("<h1>Доступ запрещён!</h1>");
		}
	}
}
