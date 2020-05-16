package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbaccess.Hotel;
import web.SessionHelper;

public class HotelDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String hotel_id = request.getParameter("hotel_id");
		
		if (hotel_id == "" || hotel_id == null)
            request.getRequestDispatcher("/").forward(request, response);
        else {
        	int hotelId = Integer.parseInt(hotel_id);
        	Hotel h = new Hotel();
        	
        	SessionHelper.setMessage(request, h.deleteHotel(hotelId));
        }		
		response.sendRedirect("Info");
	}

}
