package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbaccess.Cuisenee;
import dbmodel.Cuisine;
import web.SessionHelper;

public class CuisineUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html; charset=UTF-8");

		if (SessionHelper.IsAdmin(request)) {
			String cuisine_id = request.getParameter("cuisine_id");

			if (cuisine_id == "" || cuisine_id == null)
				request.getRequestDispatcher("/").forward(request, response);
			else {
				int cuisineId = Integer.parseInt(cuisine_id);
				Cuisenee cuis = new Cuisenee();
				Cuisine cuisine = cuis.getCuisineByACuisineId(cuisineId);

				String header = "<html><head><title>RouteTips - Изменение кухни</title></head>"
					+ "<body>";
    		
				String body = "<h1>Изменение кухни</h1>"
    				+ "<form>"
    				+ "<table>"
    				+ "<tr>"
    				+ "<td><label for=\"cuisineId\">Идентификатор кухни</label></td>"
    				+ "<td><input type=\"text\" id=\"cuisineId\" name=\"cuisineId\" value=\"%d\" readonly></td>"
    				+ "</tr>"
    				+ "<tr>"
    				+ "<td><label for=\"cuistype\">Тип</label></td>"
    				+ "<td><input type=\"text\" id=\"cuistype\" name=\"cuistype\" value=\"%s\"></td>"
    				+ "</tr>"
    				+ "<tr>"
    				+ "<td colspan=2 align=\"center\"><br><br><input type=\"submit\" value=\"Изменить\" formaction=\"CuisineUpdateAct\"></td>"
    				+ "</tr>";

				String footer = "</table></form></body></html>";

				response.getWriter().append(String.format(header));
				SessionHelper.typeSessionHeader(request, response);
				response.getWriter().append(String.format(body, cuisineId, cuisine.getCuisineType()));
				response.getWriter().append(footer);
			}
		} else {
			response.getWriter().append("<h1>Доступ запрещён!</h1>");
		}
	}

}
