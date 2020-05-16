package controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dbaccess.User;
import dbmodel.Users;
import web.SessionHelper;

public class UserUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String header = "<html><head><title>RouteTips - Пользователь</title></head>"
				+ "<link href=\"css/registration.css\" rel=\"stylesheet\" type=\"text/css\">"
				+ "<body%s>";
	
		String body_style = "<form class=\"regform\" style=\"background-color: #B6BFC6\">"
			+ "<div style=\"padding-left:55px; padding-top:2px\"><h2>%s</h2></div>"
			+ "<form>"
			+ "<table>"
			+ "<tr>"
			+ "<td><input type=\"hidden\" id=\"user_id\" name=\"user_id\" value=\"%d\" readonly></td>"
			+ "</tr>"
			+ "<tr><td><div class=\"labelfield\"><label for=\"fname\">Имя</label></div></td></tr>"
			+ "<tr><td>"
				+ "<div class=\"inputfield\">"
				+ "<input type=\"text\" id=\"fname\" name=\"fname\" value=\"%s\">"
			+ "</td></tr>"
			+ "<tr><td><div class=\"labelfield\"><label for=\"lname\">Фамилия</label></div></td></tr>"
			+ "<tr><td>"
				+ "<div class=\"inputfield\">"
				+ "<input type=\"text\" id=\"lname\" name=\"lname\" value=\"%s\"></div>"
			+ "</td></tr>"
			+ "<tr><td><div class=\"labelfield\"><label for=\"login\">Логин</label></div></td></tr>"
			+ "<tr><td>"
				+ "<div class=\"inputfield\">"
				+ "<input type=\"text\" id=\"login\" name=\"login\" value=\"%s\"></div>"
			+ "</td></tr>"
			+ "<tr><td><div class=\"labelfield\"><label for=\"password\">Пароль</label></div></td></tr>"
			+ "<tr><td>"
				+ "<div class=\"inputfield\">"
				+ "<input type=\"password\" id=\"password\" name=\"password\" value=\"%s\"></div>"
			+ "</td></tr>"
			+ "<tr><td><div class=\"labelfield\"><label for=\"email\">Email</label></div></td></tr>"
			+ "<tr><td>"
				+ "<div class=\"inputfield\">"
				+ "<input type=\"email\" id=\"email\" name=\"email\" value=\"%s\"></div>"
			+ "</td></tr>"
			+ "<tr><td colspan=2 align=\"center\"><br><br><input type=\"submit\" value=\"Изменить\" formaction=\"UserUpdateAct\"></td></tr>"
			+ "<tr>"
			+ "<td colspan=2 align=\"center\"><br><br>%s</br></br></td>"
			+ "</tr>";
		
		String body_nostyle = "<h1>%s</h1>"
				+ "<form>"
				+ "<table>"
				+ "<tr>"
				+ "<td><label for=\"user_id\">Идентификатор пользователя</label></td>"
				+ "<td><input type=\"text\" id=\"user_id\" name=\"user_id\" value=\"%d\" readonly></td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td><label for=\"fname\">Имя</label></td>"
				+ "<td><input type=\"text\" id=\"fname\" name=\"fname\" value=\"%s\"></td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td><label for=\"lname\">Фамилия</label></td>"
				+ "<td><input type=\"text\" id=\"lname\" name=\"lname\" value=\"%s\"></td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td><label for=\"login\">Логин</label></td>"
				+ "<td><input type=\"text\" id=\"login\" name=\"login\" value=\"%s\"></td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td><label for=\"password\">Пароль</label></td>"
				+ "<td><input type=\"password\" id=\"password\" name=\"password\" value=\"%s\"></td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td><label for=\"email\">Email</label></td>"
				+ "<td><input type=\"email\" id=\"email\" name=\"email\" value=\"%s\"></td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td colspan=2 align=\"center\"><br><br><input type=\"submit\" value=\"Изменить\" formaction=\"UserUpdateAct\"></td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td colspan=2 align=\"center\"><br><br>%s</td>"
				+ "</tr>";

		String footer = "</table></form></body></html>";
		
		HttpSession httpsess = request.getSession(false);
		
		response.setContentType("text/html; charset=UTF-8");

		String user_id_session = (String) httpsess.getAttribute("user_id");
		String user_id_param = (String) request.getParameter("user_id");
		String h2_message = "Изменение пользователя";
		String exit_tag = "";
		String body_image = "";
		String body = body_nostyle;
				
		if (SessionHelper.IsAdmin(request) || user_id_param.equals(user_id_session)) {
			String user_id = user_id_param;

			if (user_id == "" || user_id == null)
				request.getRequestDispatcher("/").forward(request, response);
			else {
				int userId = Integer.parseInt(user_id);
				User u = new User();
				Users user = u.getUserByUserId(userId);
				
				if (user_id_param.equals(user_id_session)) {
					h2_message = "Личный кабинет";
					exit_tag = "<a href=\"Login\" style=\"color:#242528\">Выход</a>";
					body_image = " style=\"background-image:url(images/home_page.jpg); background-size:cover;\"";
					body = body_style;
				}

				response.getWriter().append(String.format(header, body_image));
				SessionHelper.typeSessionHeader(request, response, "header-link-white");
				response.getWriter().append(String.format(body, h2_message, userId, user.getFirstName(), user.getLastName(),
						user.getLogin(), user.getUPassword(), user.getMail(), exit_tag));
				response.getWriter().append(footer);
			}
		} else {
			response.getWriter().append("<h1>Доступ запрещён!</h1>");
		}
	}
}
