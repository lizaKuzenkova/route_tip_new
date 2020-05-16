package controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dbaccess.User;
import web.SessionHelper;

public class UserUpdateAct extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession httpsess = request.getSession(false);
		String user_id_session = (String) httpsess.getAttribute("user_id");
		String user_id_param = (String) request.getParameter("user_id");

		response.setContentType("text/html; charset=UTF-8");

		if (SessionHelper.IsAdmin(request) || user_id_param.equals(user_id_session)) {
			String user_id = null;
			String user_fn = null;
			String user_ln = null;
			String user_login = null;
			String user_password = null;
			String user_email = null;

			user_id = request.getParameter("user_id");
			user_fn = request.getParameter("fname");
			user_ln = request.getParameter("lname");
			user_login = request.getParameter("login");
			user_password = request.getParameter("password");
			user_email = request.getParameter("email");

			int userId = Integer.parseInt(user_id);
			User u = new User();
			
			SessionHelper.setMessage(request, u.updateUser(userId, user_login, user_password, user_fn, user_ln, user_email));
			
			if (user_id_param.equals(user_id_session)) {
				httpsess.setAttribute("user_fn", user_fn);
				httpsess.setAttribute("user_ln", user_ln);
			}
			
			response.sendRedirect(String.format("Info?user_id=%s", user_id_param));
		} else {
			response.getWriter().append("<h1>Доступ запрещён!</h1>");
		}
	}
}
