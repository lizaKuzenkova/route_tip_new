package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbaccess.Country;
import web.SessionHelper;

public class CountryDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String country_id = request.getParameter("country_id");
		
		if (country_id == "" || country_id == null)
            request.getRequestDispatcher("/").forward(request, response);
        else {
        	int countryId = Integer.parseInt(country_id);
        	Country cntr = new Country();
        	
        	SessionHelper.setMessage(request, cntr.deleteCountry(countryId));
        }
		response.sendRedirect("Info");
	}

}
