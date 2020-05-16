package controllers;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbaccess.Hotel;
import web.SessionHelper;

public class HotelUpdateAct extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html; charset=UTF-8");

		if (SessionHelper.IsAdmin(request)) {
			String hotel_id = null;
			String hotel_nm = null;
			String hotel_address = null;
			String hotel_rating = null;
			String hotel_stars = null;
			String hotel_website = null;


			hotel_id = request.getParameter("hotelId");
			hotel_nm = request.getParameter("hotelname");
			hotel_address = request.getParameter("hoteladdr");
			hotel_rating = request.getParameter("hotelrating");
			hotel_stars = request.getParameter("hotelstars");
			hotel_website = request.getParameter("hotelwebsite");

			int hotelId = Integer.parseInt(hotel_id);
			BigDecimal hotelRating = new BigDecimal(hotel_rating);
			int hotelStars = Integer.parseInt(hotel_stars);
			Hotel h = new Hotel();

			SessionHelper.setMessage(request, h.updateHotel(hotelId, hotel_nm, hotel_address, hotelRating, hotelStars, hotel_website));
			
			response.sendRedirect("Info");
		} else {
			response.getWriter().append("<h1>Доступ запрещён!</h1>");
		}
	}

}
