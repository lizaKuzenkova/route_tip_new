package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbaccess.Hotel;
import dbaccess.Restaurant;
import dbmodel.Hotels;
import dbmodel.Restaurants;
import web.SessionHelper;

public class HotelUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html; charset=UTF-8");

		if (SessionHelper.IsAdmin(request)) {
			String hotel_id = request.getParameter("hotel_id");

			if (hotel_id == "" || hotel_id == null)
				request.getRequestDispatcher("/").forward(request, response);
			else {
				int hotelId = Integer.parseInt(hotel_id);
				Hotel h = new Hotel();
				Hotels hotel = h.getHotelByHotelId(hotelId);

				String header = "<html><head><title>RouteTips - Изменение отеля</title></head>"
					+ "<body>";
    		
				String body = "<h1>Изменение отеля</h1>"
    				+ "<form>"
    				+ "<table>"
    				+ "<tr>"
    				+ "<td><label for=\"hotelId\">Идентификатор ресторана</label></td>"
    				+ "<td><input type=\"text\" id=\"hotelId\" name=\"hotelId\" value=\"%d\" readonly></td>"
    				+ "</tr>"
    				+ "<tr>"
    				+ "<td><label for=\"restaddr\">Название</label></td>"
    				+ "<td><input type=\"text\" id=\"hotelname\" name=\"hotelname\" value=\"%s\"></td>"
    				+ "</tr>"
    				+ "<tr>"
    				+ "<td><label for=\"restaddr\">Адрес</label></td>"
    				+ "<td><input type=\"text\" id=\"hoteladdr\" name=\"hoteladdr\" value=\"%s\"></td>"
    				+ "</tr>"
    				+ "<tr>"
    				+ "<td><label for=\"restrating\">Ретйинг</label></td>"
    				+ "<td><input type=\"text\" id=\"hotelrating\" name=\"hotelrating\" value=\"%s\"></td>"
    				+ "</tr>"
    				+ "<tr>"
    				+ "<td><label for=\"restrating\">Количество звезд</label></td>"
    				+ "<td><input type=\"text\" id=\"hotelstars\" name=\"hotelstars\" value=\"%s\"></td>"
    				+ "</tr>"
    				+ "<tr>"
    				+ "<td><label for=\"restwebsite\">Веб-сайт</label></td>"
    				+ "<td><input type=\"text\" id=\"hotelwebsite\" name=\"hotelwebsite\" value=\"%s\"></td>"
    				+ "</tr>"
    				+ "<tr>"
    				+ "<td colspan=2 align=\"center\"><br><br><input type=\"submit\" value=\"Изменить\" formaction=\"HotelUpdateAct\"></td>"
    				+ "</tr>";

				String footer = "</table></form></body></html>";

				response.getWriter().append(String.format(header));
				SessionHelper.typeSessionHeader(request, response);
				response.getWriter().append(String.format(body, hotelId, hotel.getHotelName(), hotel.getAddress(), hotel.getRating(), hotel.getAmountOfStars(), hotel.getWebsite()));
				response.getWriter().append(footer);
			}
		} else {
			response.getWriter().append("<h1>Доступ запрещён!</h1>");
		}
	}


}
