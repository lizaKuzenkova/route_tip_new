package controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbaccess.Country;
import dbaccess.Cuisenee;
import web.SessionHelper;
import web.Utility;

/**
 * Servlet implementation class CuisineAdd
 */
public class CuisineAdd extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String cuisine_type = null;
		String headerSuffix = "Виды кухни";
		String resp = null;

		String header = "<html><head><title>RouteTips - %s</title></head>"
				+ "<body>";
		
		String body = "<h1>%s</h1>"
				+ "<form>"
				+ "<table>"
				+ "<tr>"
				+ "<td><label for=\"cuisine_type\">Кухня</label></td>"
				+ "<td><input type=\"text\" id=\"cuisine_type\" name=\"cuisine_type\" value=\"%s\"></td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td colspan=2 align=\"center\"><input type=\"submit\" value=\"Подтвердить\"></td>"
				+ "</tr>";

		String footer = "</table></form></body></html>";
				
		response.setContentType("text/html; charset=UTF-8");

		response.getWriter().append(String.format(header, headerSuffix));
		SessionHelper.typeSessionHeader(request, response);
		response.getWriter().append(String.format(body, headerSuffix, ""));
		response.getWriter().append(footer);
		
		cuisine_type = request.getParameter("cuisine_type");
		
		if (cuisine_type == null)
			cuisine_type = "";
		

		if (cuisine_type != "") {
			Cuisenee c = new Cuisenee();
			resp = c.createCuisine(cuisine_type);
			response.getWriter().append("<br><br><h2>" + resp
					+ "</h2><br><br><form action=\"/travelingweb/UserAdministration\"><input type=\"submit\" value=\"Продолжить\">");
		} 
	}
}
