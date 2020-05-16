package controllers;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbaccess.Restaurant;
import web.SessionHelper;

public class RestaurantUpdateAct extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html; charset=UTF-8");

		if (SessionHelper.IsAdmin(request)) {
			String restaurant_id = null;
			String restaurant_nm = null;
			String restaurant_address = null;
			String restaurant_rating = null;
			String restaurant_website = null;


			restaurant_id = request.getParameter("restaurantId");
			restaurant_nm = request.getParameter("restname");
			restaurant_address = request.getParameter("restaddr");
			restaurant_rating = request.getParameter("restrating");
			restaurant_website = request.getParameter("restwebsite");

			int restaurantId = Integer.parseInt(restaurant_id);
			BigDecimal restaurantRating = new BigDecimal(restaurant_rating);
			Restaurant rest = new Restaurant();
			
        	SessionHelper.setMessage(request, rest.updateRestaurant(restaurantId, restaurant_nm, restaurant_address, restaurantRating, restaurant_website));

			response.sendRedirect("Info");
		} else {
			response.getWriter().append("<h1>Доступ запрещён!</h1>");
		}
	}

}
