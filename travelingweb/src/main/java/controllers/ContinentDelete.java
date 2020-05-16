package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbaccess.Continent;
import web.SessionHelper;

public class ContinentDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String continent_id = request.getParameter("continent_id");
		
		if (continent_id == "" || continent_id == null)
            request.getRequestDispatcher("/").forward(request, response);
        else {
        	int attractionId = Integer.parseInt(continent_id);
        	Continent cont = new Continent();
        	
        	SessionHelper.setMessage(request, cont.deleteContinent(attractionId));
        }
		response.sendRedirect("Info");
	}

}
