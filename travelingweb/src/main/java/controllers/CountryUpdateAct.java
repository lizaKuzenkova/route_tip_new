package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbaccess.Attraction;
import dbaccess.Country;
import web.SessionHelper;

public class CountryUpdateAct extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html; charset=UTF-8");

		if (SessionHelper.IsAdmin(request)) {
			String country_id = null;
			String country_nm = null;
			String country_capt = null;
			String country_pop = null;
			String country_ar = null;


			country_id = request.getParameter("countryId");
			country_nm = request.getParameter("cntrname");
			country_capt = request.getParameter("cntrcapt");
			country_pop = request.getParameter("cntrpop");
			country_ar = request.getParameter("cntrar");

			int countryId = Integer.parseInt(country_id);
			int cntrpop = Integer.parseInt(country_pop);
			int cntrar = Integer.parseInt(country_ar);
			Country cntr = new Country();
			
			SessionHelper.setMessage(request, cntr.updateCountry(countryId, country_nm, country_capt, cntrpop, cntrar));
			
			response.sendRedirect("Info");
		} else {
			response.getWriter().append("<h1>Доступ запрещён!</h1>");
		}
	}

}
