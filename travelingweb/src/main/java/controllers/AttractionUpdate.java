package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbaccess.Attraction;
import dbmodel.Attractions;
import web.SessionHelper;

public class AttractionUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html; charset=UTF-8");

		if (SessionHelper.IsAdmin(request)) {
			String attraction_id = request.getParameter("attraction_id");

			if (attraction_id == "" || attraction_id == null)
				request.getRequestDispatcher("/").forward(request, response);
			else {
				int attractionId = Integer.parseInt(attraction_id);
				Attraction att = new Attraction();
				Attractions attraction = att.getAttractionByAttractionId(attractionId);

				String header = "<html><head><title>RouteTips - Изменение достопримечательности</title></head>"
					+ "<body>";
    		
				String body = "<h1>Изменение достопримечательности</h1>"
    				+ "<form>"
    				+ "<table>"
    				+ "<tr>"
    				+ "<td><label for=\"attractionId\">Идентификатор достопримечательности</label></td>"
    				+ "<td><input type=\"text\" id=\"attractionId\" name=\"attractionId\" value=\"%d\" readonly></td>"
    				+ "</tr>"
    				+ "<tr>"
    				+ "<td><label for=\"attname\">Название</label></td>"
    				+ "<td><input type=\"text\" id=\"attname\" name=\"attname\" value=\"%s\"></td>"
    				+ "</tr>"
    				+ "<tr>"
    				+ "<td><label for=\"attdescr\">Описание</label></td>"
    				+ "<td><input type=\"text\" id=\"attdescr\" name=\"attdescr\" value=\"%s\"></td>"
    				+ "</tr>"
    				+ "<tr>"
    				+ "<td colspan=2 align=\"center\"><br><br><input type=\"submit\" value=\"Изменить\" formaction=\"AttractionUpdateAct\"></td>"
    				+ "</tr>";

				String footer = "</table></form></body></html>";

				response.getWriter().append(String.format(header));
				SessionHelper.typeSessionHeader(request, response);
				response.getWriter().append(String.format(body, attractionId, attraction.getAttractionName(), attraction.getAttDescription()));
				response.getWriter().append(footer);
			}
		} else {
			response.getWriter().append("<h1>Доступ запрещён!</h1>");
		}
	}
}
