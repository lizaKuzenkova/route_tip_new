package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbaccess.Continent;
import dbmodel.Continents;
import web.SessionHelper;

public class ContinentUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html; charset=UTF-8");

		if (SessionHelper.IsAdmin(request)) {
			String continent_id = request.getParameter("continent_id");

			if (continent_id == "" || continent_id == null)
				request.getRequestDispatcher("/").forward(request, response);
			else {
				int continentId = Integer.parseInt(continent_id);
				Continent cont = new Continent();
				Continents continent = cont.getContinentByContinentId(continentId);

				String header = "<html><head><title>RouteTips - Изменение континента</title></head>"
					+ "<body>";
    		
				String body = "<h1>Изменение континента</h1>"
    				+ "<form>"
    				+ "<table>"
    				+ "<tr>"
    				+ "<td><label for=\"continentId\">Идентификатор континента</label></td>"
    				+ "<td><input type=\"text\" id=\"continentId\" name=\"continentId\" value=\"%d\" readonly></td>"
    				+ "</tr>"
    				+ "<tr>"
    				+ "<td><label for=\"contname\">Название</label></td>"
    				+ "<td><input type=\"text\" id=\"contname\" name=\"contname\" value=\"%s\"></td>"
    				+ "</tr>"
    				+ "<tr>"
    				+ "<td colspan=2 align=\"center\"><br><br><input type=\"submit\" value=\"Изменить\" formaction=\"ContinentUpdateAct\"></td>"
    				+ "</tr>";

				String footer = "</table></form></body></html>";

				response.getWriter().append(String.format(header));
				SessionHelper.typeSessionHeader(request, response);
				response.getWriter().append(String.format(body, continentId, continent.getContinentName()));
				response.getWriter().append(footer);
			}
		} else {
			response.getWriter().append("<h1>Доступ запрещён!</h1>");
		}
	}

}
