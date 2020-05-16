package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbaccess.Cuisenee;
import web.SessionHelper;

public class CuisineUpdateAct extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html; charset=UTF-8");

		if (SessionHelper.IsAdmin(request)) {
			String cuisine_id = null;
			String cuisine_t = null;

			cuisine_id = request.getParameter("cuisineId");
			cuisine_t = request.getParameter("cuistype");

			int cuisineId = Integer.parseInt(cuisine_id);
			Cuisenee cuis = new Cuisenee();
			
			SessionHelper.setMessage(request, cuis.updateCuisine(cuisineId, cuisine_t));

			response.sendRedirect("Info");
		} else {
			response.getWriter().append("<h1>Доступ запрещён!</h1>");
		}
	}

}
