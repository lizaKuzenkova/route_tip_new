package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbaccess.Attraction;
import web.SessionHelper;

public class AttractionDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String attraction_id = request.getParameter("attraction_id");
		
		if (attraction_id == "" || attraction_id == null)
            request.getRequestDispatcher("/").forward(request, response);
        else {
        	int attractionId = Integer.parseInt(attraction_id);
        	Attraction att = new Attraction();
        	
        	SessionHelper.setMessage(request, att.deleteAttraction(attractionId));
        }
		response.sendRedirect("Info");
	}
}
