package controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbaccess.City;
import dbaccess.Rolee;
import dbaccess.User;
import web.SessionHelper;
import web.Utility;

/**
 * Servlet implementation class UserAdd
 */
public class UserAdd extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String user_lname = null;
		String user_fname = null;
		String user_login = null;
		String user_password = null;
		String user_mail = null;
		String user_role = null;
		String headerSuffix = "Пользователи";
		String resp = null;

		String header = "<html><head><title>RouteTips - %s</title></head>"
				+ "<body>";
		
		String roles_list = Utility.BuildDropDownUserRoles(response);
		
		String body = "<h1>%s</h1>"
				+ "<form>"
				+ "<table>"
				+ "<tr>"
				+ "<td><label for=\"user_lname\">Фамилия</label></td>"
				+ "<td><input type=\"text\" id=\"user_lname\" name=\"user_lname\" value=\"%s\"></td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td><label for=\"user_fname\">Имя</label></td>"
				+ "<td><input type=\"text\" id=\"user_fname\" name=\"user_fname\" value=\"%s\"></td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td><label for=\"user_login\">Логин</label></td>"
				+ "<td><input type=\"text\" id=\"user_login\" name=\"user_login\" value=\"%s\"></td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td><label for=\"user_password\">Пароль</label></td>"
				+ "<td><input type=\"text\" id=\"user_password\" name=\"user_password\" value=\"%s\"></td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td><label for=\"user_mail\">Электронная почта</label></td>"
				+ "<td><input type=\"text\" id=\"user_mail\" name=\"user_mail\" value=\"%s\"></td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td><label for=\"user_role\">Роль в системе</label></td>"
				+ "<td>%s</td>"
				+ "<tr>"
				+ "<td colspan=2 align=\"center\"><input type=\"submit\" value=\"Подтвердить\"></td>"
				+ "</tr>";

		String footer = "</table></form></body></html>";
				
		response.setContentType("text/html; charset=UTF-8");

		response.getWriter().append(String.format(header, headerSuffix));
		SessionHelper.typeSessionHeader(request, response);
		response.getWriter().append(String.format(body, headerSuffix, "", roles_list));
		response.getWriter().append(footer);
		
		user_lname = request.getParameter("user_lname");
		user_fname = request.getParameter("user_fname");
		user_login = request.getParameter("user_login");
		user_password = request.getParameter("user_password");
		user_mail = request.getParameter("user_mail");
		user_role = request.getParameter("selected_role");
		
		if (user_lname == null)
			user_lname = "";
		

		if (user_lname != "") {
			User u = new User();
			resp = u.createUserNew(user_lname, user_fname, user_login, user_password, user_mail, user_role);
			response.getWriter().append("<br><br><h2>" + resp
					+ "</h2><br><br><form action=\"/travelingweb/UserAdministration\"><input type=\"submit\" value=\"Продолжить\">");
		} 
	}
}
