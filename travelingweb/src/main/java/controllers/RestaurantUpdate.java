package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbaccess.Restaurant;
import dbmodel.Restaurants;
import web.SessionHelper;

public class RestaurantUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html; charset=UTF-8");

		if (SessionHelper.IsAdmin(request)) {
			String restaurant_id = request.getParameter("restaurant_id");

			if (restaurant_id == "" || restaurant_id == null)
				request.getRequestDispatcher("/").forward(request, response);
			else {
				int restaurantId = Integer.parseInt(restaurant_id);
				Restaurant rest = new Restaurant();
				Restaurants restaurant = rest.getRestaurantByRestaurantId(restaurantId);

				String header = "<html><head><title>RouteTips - Изменение ресторана</title></head>"
					+ "<body>";
    		
				String body = "<h1>Изменение ресторана</h1>"
    				+ "<form>"
    				+ "<table>"
    				+ "<tr>"
    				+ "<td><label for=\"restaurantId\">Идентификатор ресторана</label></td>"
    				+ "<td><input type=\"text\" id=\"restaurantId\" name=\"restaurantId\" value=\"%d\" readonly></td>"
    				+ "</tr>"
    				+ "<tr>"
    				+ "<td><label for=\"restaddr\">Название</label></td>"
    				+ "<td><input type=\"text\" id=\"restname\" name=\"restname\" value=\"%s\"></td>"
    				+ "</tr>"
    				+ "<tr>"
    				+ "<td><label for=\"restaddr\">Адрес</label></td>"
    				+ "<td><input type=\"text\" id=\"restaddr\" name=\"restaddr\" value=\"%s\"></td>"
    				+ "</tr>"
    				+ "<tr>"
    				+ "<td><label for=\"restrating\">Ретйинг</label></td>"
    				+ "<td><input type=\"text\" id=\"restrating\" name=\"restrating\" value=\"%s\"></td>"
    				+ "</tr>"
    				+ "<tr>"
    				+ "<td><label for=\"restwebsite\">Веб-сайт</label></td>"
    				+ "<td><input type=\"text\" id=\"restwebsite\" name=\"restwebsite\" value=\"%s\"></td>"
    				+ "</tr>"
    				+ "<tr>"
    				+ "<td colspan=2 align=\"center\"><br><br><input type=\"submit\" value=\"Изменить\" formaction=\"RestaurantUpdateAct\"></td>"
    				+ "</tr>";

				String footer = "</table></form></body></html>";

				response.getWriter().append(String.format(header));
				SessionHelper.typeSessionHeader(request, response);
				response.getWriter().append(String.format(body, restaurantId, restaurant.getRestName(), restaurant.getAddress(), restaurant.getRating(), restaurant.getWebsite()));
				response.getWriter().append(footer);
			}
		} else {
			response.getWriter().append("<h1>Доступ запрещён!</h1>");
		}
	}
}
