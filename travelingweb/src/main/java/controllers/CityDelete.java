package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbaccess.City;
import web.SessionHelper;

public class CityDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String city_id = request.getParameter("city_id");
		
		if (city_id == "" || city_id == null)
            request.getRequestDispatcher("/").forward(request, response);
        else {
        	int cityId = Integer.parseInt(city_id);
        	City ct = new City();
        	
        	SessionHelper.setMessage(request, ct.deleteCity(cityId));
        }
		response.sendRedirect("Info");
	}

}
