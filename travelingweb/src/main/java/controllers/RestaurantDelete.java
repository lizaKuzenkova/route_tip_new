package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbaccess.Restaurant;
import web.SessionHelper;

public class RestaurantDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String restaurant_id = request.getParameter("restaurant_id");
		
		if (restaurant_id == "" || restaurant_id == null)
            request.getRequestDispatcher("/").forward(request, response);
        else {
        	int restaurantId = Integer.parseInt(restaurant_id);
        	Restaurant rest = new Restaurant();
        	SessionHelper.setMessage(request, rest.deleteRestaurant(restaurantId));
        }		
		response.sendRedirect("Info");
	}

}
