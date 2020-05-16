package web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbaccess.*;
import dbmodel.Users;

/**
 * Servlet implementation class test01
 */
public class UserRegistration extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		User u = new User();

		String user_fn = null;
		String user_ln = null;
		String user_login = null;
		String user_password = null;
		String user_email = null;

		String headerSuffix = null;
		String resp = null;
		
		String header = "<html><head><title>RouteTips - %s</title>"
				+ "<link href=\"css/registration.css\" rel=\"stylesheet\" type=\"text/css\">"
				+ "</head>"
				+ "<body style=\"background-image:url(images/registration.jpg); background-size:cover\">";
		
		String body = "<form class=\"regform\" style=\"background-color: #EED7C6\">"
				+ "<div style=\"padding-left:55px; padding-top:2px\"><h1>%s</h1></div>"
				+ "<form>"
				+ "<table>"
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
				+ "<tr>"
				+ "<td colspan=2 align=\"center\"><br><br><div style=\"margin-bottom:15px\"><input type=\"submit\" value=\"Подтвердить\"></div></td>"
				+ "</tr>";

		String footer = "</table></form></body></html>";
	
		response.setContentType("text/html; charset=UTF-8");

		headerSuffix = "Регистрация";
		response.getWriter().append(String.format(header, headerSuffix));
		SessionHelper.typeSessionHeader(request, response, "header-link-white");
		response.getWriter().append(String.format(body, headerSuffix, "", "", "", "", ""));
		response.getWriter().append(footer);

		user_fn = request.getParameter("fname");
		user_ln = request.getParameter("lname");
		user_login = request.getParameter("login");
		user_password = request.getParameter("password");
		user_email = request.getParameter("email");

		if (user_fn == null)
			user_fn = "";

		if (user_fn != "") {
			resp = u.createUser(user_login, user_password, user_fn, user_ln, user_email);
			response.getWriter().append("<div style=\"margin-left:750px; background:white; width:450px\"><h2>" + resp
					+ "</h2></div><div style=\"margin-left:900px\"><form action=\"/travelingweb/HomePage\" style=\"margin-top:10px\">  <style type=\"text/css\">\r\n" + 
					"  </style><input style=\"background:#14374A; width:110px; height:50px; color:#EBEBEB; font-size:16px\" type=\"submit\" value=\"Продолжить\">");
		}
	}
}
