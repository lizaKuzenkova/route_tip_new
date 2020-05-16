package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbaccess.City;
import web.SessionHelper;

public class CityUpdateAct extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html; charset=UTF-8");

		if (SessionHelper.IsAdmin(request)) {
			String city_id = null;
			String city_nm = null;


			city_id = request.getParameter("cityId");
			city_nm = request.getParameter("cityname");

			int cityId = Integer.parseInt(city_id);
			City ct = new City();
			
			SessionHelper.setMessage(request, ct.updateCity(cityId, city_nm));

			response.sendRedirect("Info");
		} else {
			response.getWriter().append("<h1>Доступ запрещён!</h1>");
		}
	}

}
