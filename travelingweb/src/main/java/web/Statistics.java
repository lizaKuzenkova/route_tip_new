package web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbaccess.Stats;
import dbmodel.AttractionStats;
import dbmodel.HotelStats;
import dbmodel.RestaurantStats;

public class Statistics extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html; charset=UTF-8");
		SessionHelper.typeSessionHeader(request, response);

//		if (SessionHelper.IsAdmin(request)) {
			response.getWriter()
					.append("<html><head><title>RouteTips - Статистика</title></head><body><form>" + "<body>"
							+ "<link href=\"css/style.css\" rel=\"stylesheet\" type=\"text/css\">"
							+ "<body>"
							+ "<form>"
							+ "<div><nav>"
							+ "<ul class=\"topmenu\">"						
								+ "<li><class=\"dropdown\">Администрирование</a>"
									+ "<ul class=\"submenu\">"
										+ "<li><a href=\"UserAdministration#users\">Пользователи</a></li>"
										+ "<li><a href=\"UserAdministration#attractions\">Достопримечательности</a></li>"
										+ "<li><a href=\"UserAdministration#hotels\">Отели</a></li>"
										+ "<li><a href=\"UserAdministration#restaurants\">Рестораны</a></li>"
									+ "</ul>"
								+ "</li>"
								+ "<li><class=\"dropdown\">Справочники</a>"
									+ "<ul class=\"submenu\">"
										+ "<li><a href=\"UserAdministration#continents\">Континенты</a></li>"
										+ "<li><a href=\"UserAdministration#countries\">Страны</a></li>"
										+ "<li><a href=\"UserAdministration#cities\">Города</a></li>"
										+ "<li><a href=\"UserAdministration#cuisine\">Виды кухни</a></li>"							
										+ "<li><a href=\"UserAdministration#roles\">Роли пользователей</a></li>"
									+ "</ul>"
								+ "</li>"	
								+ "<li><class=\"dropdown\">Сервис</a>"
									+ "<ul class=\"submenu\">"
										+ "<li><a href=\"Statistics\">Статистика</a></li>"
										+ "<li><a href=\"HomePage\">Составление маршрута</a></li>"
									+ "</ul>"
								+ "</li>"
							+ "</ul></nav></div>");
			response.getWriter()
					.append("<h2>Статистика популярности</h2>");
			
			this.BuildAttractionStats(response);
			this.BuildHotelStats(response);
			this.BuildRestaurantStats(response);

			response.getWriter().append("</body></html>");

//		} else {
//			response.getWriter().append("<h1>Доступ запрещён!</h1>");
//		}
	}

	private void BuildAttractionStats(HttpServletResponse response) throws IOException {
		
		String attraction_name = null;
		String city_name = null;
		int num_of_searches = -1;
		String TR_tag = null;
		
		response.getWriter()
		.append("<h2 id=\"attractions\">Достопримечательности</h2>"
				+ "<table border=\"0\" cellspacing=\"0\" cellpadding=\"10\">"
				+ "<tr bgcolor=\"#EBEBEB\">"
				+ "<th>Название достопримечательности</th>"
				+ "<th>Город</th>"
				+ "<th>Выбрали для маршрута раз</th>"
				+ "</tr>");

		List<AttractionStats> attr_stats = Stats.getAttractionStats();
		for (AttractionStats attr_stat : attr_stats) {
			System.out.println(attr_stat.getAttractionName() + " - " + attr_stat.getNumOfSearches());
			
			attraction_name = attr_stat.getAttractionName();
			city_name = attr_stat.getCityName();
			num_of_searches = attr_stat.getNumOfSearches();

			TR_tag = String.format(
					"<tr>"
						+ "<td>%s</td>"
						+ "<td>%s</td>"
						+ "<td align=\"center\">%s</td>"
						+ "</tr>",
						attraction_name, city_name, num_of_searches);

			response.getWriter().append(TR_tag);
		}
		response.getWriter().append("</table>");
	}

	private void BuildHotelStats(HttpServletResponse response) throws IOException {
		String hotel_name = null;
		String city_name = null;
		int num_of_searches = -1;
		String TR_tag = null;
		
		response.getWriter()
		.append("<h2 id=\"attractions\">Отели</h2>"
				+ "<table border=\"0\" cellspacing=\"0\" cellpadding=\"10\">"
				+ "<tr bgcolor=\"#EBEBEB\">"
				+ "<th>Название отеля</th>"
				+ "<th>Город</th>"
				+ "<th>Выбрали для маршрута раз</th>"
				+ "</tr>");
		
		List<HotelStats> hotel_stats = Stats.getHotelStats();
		for (HotelStats hotel_stat : hotel_stats) {
			System.out.println(hotel_stat.getHotelName() + " - " + hotel_stat.getNumOfSearches());
			
			hotel_name = hotel_stat.getHotelName();
			city_name = hotel_stat.getCityName();
			num_of_searches = hotel_stat.getNumOfSearches();

			TR_tag = String.format(
					"<tr>"
						+ "<td>%s</td>"
						+ "<td>%s</td>"
						+ "<td align=\"center\">%s</td>"
						+ "</tr>",
						hotel_name, city_name, num_of_searches);

			response.getWriter().append(TR_tag);
		}
		response.getWriter().append("</table>");
	}
	
	private void BuildRestaurantStats(HttpServletResponse response) throws IOException {
		String restaurant_name = null;
		String city_name = null;
		int num_of_searches = -1;
		String TR_tag = null;
		
		response.getWriter()
		.append("<h2 id=\"attractions\">Рестораны</h2>"
				+ "<table border=\"0\" cellspacing=\"0\" cellpadding=\"10\">"
				+ "<tr bgcolor=\"#EBEBEB\">"
				+ "<th>Название ресторана</th>"
				+ "<th>Город</th>"
				+ "<th>Выбрали для маршрута раз</th>"
				+ "</tr>");
		
		List<RestaurantStats> restaurant_stats = Stats.getRestaurantStats();
		for (RestaurantStats restaurant_stat : restaurant_stats) {
			System.out.println(restaurant_stat.getRestaurantName()+ " - " + restaurant_stat.getNumOfSearches());
			
			restaurant_name = restaurant_stat.getRestaurantName();
			city_name = restaurant_stat.getCityName();
			num_of_searches = restaurant_stat.getNumOfSearches();

			TR_tag = String.format(
					"<tr>"
						+ "<td>%s</td>"
						+ "<td>%s</td>"
						+ "<td align=\"center\">%s</td>"
						+ "</tr>",
						restaurant_name, city_name, num_of_searches);

			response.getWriter().append(TR_tag);
		}
		response.getWriter().append("</table>");
	}


}
