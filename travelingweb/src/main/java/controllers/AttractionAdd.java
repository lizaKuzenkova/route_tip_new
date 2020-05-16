package controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbaccess.Attraction;
import web.SessionHelper;
import web.Utility;

/**
 * Servlet implementation class AttractionAdd
 */
public class AttractionAdd extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String attraction_name = null;
		String attraction_descr = null;
		String attraction_city = null;
		String headerSuffix = "Достопримечаельности";
		String resp = null;

		String header = "<html><head><title>RouteTips - %s</title></head>"
				+ "<body>";
		
		String cities_list = Utility.BuildDropDownCities(response);
		
		String body = "<h1>%s</h1>"
				+ "<form>"
				+ "<table>"
				+ "<tr>"
				+ "<td><label for=\"attraction_name\">Достопримечательность</label></td>"
				+ "<td><input type=\"text\" id=\"attraction_name\" name=\"attraction_name\" value=\"%s\"></td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td><label for=\"attraction_descr\">Описание</label></td>"
				+ "<td><input type=\"text\" id=\"attraction_descr\" name=\"attraction_descr\" value=\"%s\"></td>"
				+ "</tr>"
				+ "<td><label for=\"attraction_city\">Город</label></td>"
				+ "<td>%s</td>"
				+ "<tr>"
				+ "<td colspan=2 align=\"center\"><input type=\"submit\" value=\"Подтвердить\"></td>"
				+ "</tr>";

		String footer = "</table></form></body></html>";
				
		response.setContentType("text/html; charset=UTF-8");

		response.getWriter().append(String.format(header, headerSuffix));
		SessionHelper.typeSessionHeader(request, response);
		response.getWriter().append(String.format(body, headerSuffix, "", "", cities_list));
		response.getWriter().append(footer);
		
		attraction_name = request.getParameter("attraction_name");
		attraction_descr = request.getParameter("attraction_descr");
		attraction_city = request.getParameter("selected_city");
		
		if (attraction_name == null)
			attraction_name = "";
		

		if (attraction_name != "") {
			Attraction att = new Attraction();
			resp = att.createAttraction(attraction_name, attraction_descr, attraction_city);
			response.getWriter().append("<br><br><h2>" + resp
					+ "</h2><br><br><form action=\"/travelingweb/UserAdministration\"><input type=\"submit\" value=\"Продолжить\">");
		} 
	}
}
