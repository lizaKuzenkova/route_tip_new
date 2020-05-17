package web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dbaccess.User;
import dbmodel.Users;

public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		HttpSession httpsess = request.getSession();
		httpsess.invalidate();
		
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter()
				.append("<html>"
						+ "<head><title>RouteTips - Авторизация</title></head>"
						+ "<link href=\"css/login.css\" rel=\"stylesheet\" type=\"text/css\">"
						+ "<body style=\"background-image:url(images/login.jpg); background-size:cover\">"
						+ "<form method=\"post\" class=\"loginform\">"
						+ "<div style=\"padding-left:45px; padding-top:2px\"><h1>Авторизация</h1></div>"
						+ "<table border=\"0\" cellspacing=\"0\" cellpadding=\"5\" width=\"100%\">"
						+ "<tr>"
						+ "<td><div class=\"labelfield\">Логин</div></td></tr>"
						+ "<tr>"
							+ "<td><div class=\"inputfield\">"
						+ "<input type=\"text\" id=\"login\" name=\"login\"></div></td></tr>"
						+ "</tr>"
						+ "<tr>"
						+ "<td><div class=\"labelfield\">Пароль</div></td></tr>"
						+ "<tr><td><div style=\"margin-left:15px\"><input type=\"password\" id=\"password\" name=\"password\"></div></td>"
						+ "</tr>"
						+ "<tr>"
							+ "<td><div class=\"btn\">"
						+ "<input type=\"submit\" value=\"Вход\"></div></td>"
						+ "</tr>"
						+ "</table>"
						+ "</form>"
						);

		response.getWriter().append("</body>");
		response.getWriter().append("</html>");

		String login = request.getParameter("login");
		String password = request.getParameter("password");
		int user_id = -1;

		if (login == null)
			login = "";
		if (password == null)
			password = "";

		if (login != "" && password != "") {
			
			Users user = new Users();
			User u = new User();
			user_id = u.getUserIdByLoginPassword(login, password);
			
			
			if (user_id > 0) {
				user = u.getUserByUserId(user_id);
				httpsess = request.getSession();
				httpsess.setAttribute("user_fn", user.getFirstName());
				httpsess.setAttribute("user_ln", user.getLastName());
				httpsess.setAttribute("user_id", user.getIdUser());
				httpsess.setAttribute("role", user.getRoles().getRoleName());

				response.getWriter().append("<div style=\"color:#1D1517; margin-top:30px; margin-left:730px; font-family: Helvetica;\"><h2>Вход успешно выполнен, " + user.getFirstName() + "!</h2></div>");
				response.getWriter().append("<div style=\"margin-top:10px; margin-left: 810px; font-family: Helvetica;\"><br><h2 style=\"padding:5px\"><a href=\"HomePage\" style=\"color:#1D1517\">Составление маршрута</a></h2></div>");
				
				String role_name = user.getRoles().getRoleName(); 

				SessionHelper.setUserId(request, Integer.toString(user_id));
				
				if ( role_name.equals("администратор") || role_name.equals("пользователь") ) {
					response.getWriter().append( "<div style=\"margin-left: 830px; font-family: Helvetica\"><br><h2 style=\"padding:5px\">"
							+ "<style type=\"text/css\">a:link{color:#1D1517} a:visited{color:#1D1517}</style>"
							+ "<a href=\"UserAdministration\">Администрирование</a></h2></div>");
				}

			} else {
				response.getWriter().append("<div style=\"color:#DC143C; background:#ebebeb; width:370px; margin-top:30px; margin-left: 770px; font-family: Helvetica;\"><h2>Неверный логин и/или пароль</h2></div>");
			}
		}
	}		


	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
		
	}
}
