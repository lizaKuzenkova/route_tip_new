package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbaccess.Attraction;
import dbaccess.User;
import web.SessionHelper;

public class AttractionUpdateAct extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html; charset=UTF-8");

		if (SessionHelper.IsAdmin(request)) {
			String attraction_id = null;
			String attraction_nm = null;
			String attraction_descr = null;


			attraction_id = request.getParameter("attractionId");
			attraction_nm = request.getParameter("attname");
			attraction_descr = request.getParameter("attdescr");

			int attractionId = Integer.parseInt(attraction_id);
			Attraction att = new Attraction();
			
			SessionHelper.setMessage(request, att.updateAttraction(attractionId, attraction_nm, attraction_descr));

			response.sendRedirect("Info");
		} else {
			response.getWriter().append("<h1>Доступ запрещён!</h1>");
		}
	}
}
