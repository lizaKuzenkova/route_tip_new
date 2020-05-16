package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbaccess.Cuisenee;
import web.SessionHelper;


public class CuisineDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String cuisine_id = request.getParameter("cuisine_id");
		
		if (cuisine_id == "" || cuisine_id == null)
            request.getRequestDispatcher("/").forward(request, response);
        else {
        	int cuisineId = Integer.parseInt(cuisine_id);
        	Cuisenee cuis = new Cuisenee();
        	
        	SessionHelper.setMessage(request, cuis.deleteCuisine(cuisineId));
        }
		response.sendRedirect("Info");
	}

}
