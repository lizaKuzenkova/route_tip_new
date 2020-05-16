package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbaccess.City;
import dbmodel.Cities;
import web.SessionHelper;

public class CityUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html; charset=UTF-8");

		if (SessionHelper.IsAdmin(request)) {
			String city_id = request.getParameter("city_id");

			if (city_id == "" || city_id == null)
				request.getRequestDispatcher("/").forward(request, response);
			else {
				int cityId = Integer.parseInt(city_id);
				City ct = new City();
				Cities city = ct.getCityByCityId(cityId);

				String header = "<html><head><title>RouteTips - Изменение города</title></head>"
					+ "<body>";
    		
				String body = "<h1>Изменение города</h1>"
    				+ "<form>"
    				+ "<table>"
    				+ "<tr>"
    				+ "<td><label for=\"cityId\">Идентификатор города</label></td>"
    				+ "<td><input type=\"text\" id=\"cityId\" name=\"cityId\" value=\"%d\" readonly></td>"
    				+ "</tr>"
    				+ "<tr>"
    				+ "<td><label for=\"cityname\">Название</label></td>"
    				+ "<td><input type=\"text\" id=\"cityname\" name=\"cityname\" value=\"%s\"></td>"
    				+ "</tr>"
    				+ "<tr>"
    				+ "<td colspan=2 align=\"center\"><br><br><input type=\"submit\" value=\"Изменить\" formaction=\"CityUpdateAct\"></td>"
    				+ "</tr>";

				String footer = "</table></form></body></html>";

				response.getWriter().append(String.format(header));
				SessionHelper.typeSessionHeader(request, response);
				response.getWriter().append(String.format(body, cityId, city.getCityName()));
				response.getWriter().append(footer);
			}
		} else {
			response.getWriter().append("<h1>Доступ запрещён!</h1>");
		}
	}
}
