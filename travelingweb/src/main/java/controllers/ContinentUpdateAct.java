package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbaccess.Continent;
import web.SessionHelper;

public class ContinentUpdateAct extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html; charset=UTF-8");

		if (SessionHelper.IsAdmin(request)) {
			String continent_id = null;
			String continent_nm = null;

			continent_id = request.getParameter("continentId");
			continent_nm = request.getParameter("contname");

			int continentId = Integer.parseInt(continent_id);
			Continent cont = new Continent();

			SessionHelper.setMessage(request, cont.updateContinent(continentId, continent_nm));

			response.sendRedirect("Info");
		} else {
			response.getWriter().append("<h1>Доступ запрещён!</h1>");
		}
	}

}
