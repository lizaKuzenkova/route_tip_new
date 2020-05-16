package controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import web.SessionHelper;

public class Info extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession httpsess = request.getSession(false);
		String user_id_session = (String) httpsess.getAttribute("user_id");
		String user_id_param = (String) request.getParameter("user_id");
		if (user_id_param == null) user_id_param = "";
		
		String title = "Сообщение";
		String header = "Системное сообщение";
		String personal_room_msg = " личного кабинета";
		String body_style = "";
		String body_container = "";
		String redirect_page = "UserAdministration";
		
		if (user_id_param.equals(user_id_session)) {
			title = title + personal_room_msg;
			header = header + personal_room_msg;
			body_style = " style=\"background-image:url(images/home_page.jpg); background-size:cover\"";
			body_container = "<form class=\"regform\" style=\"background-color: #EA9744; padding-top: 15px\">";
			redirect_page = "HomePage";
		}
		
		SessionHelper.getMessage(request);
		
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().append(String.format("<html><head><title>RouteTips - %s</title>"												
													+ "<link href=\"css/registration.css\" rel=\"stylesheet\" type=\"text/css\"></head><body%s><div align=\"center\">", title, body_style));
		SessionHelper.typeSessionHeader(request, response, "header-link-white");
		response.getWriter().append(String.format("<h2>%s</h2>", header));
		response.getWriter().append(String.format(body_container));
		response.getWriter().append(SessionHelper.getMessage(request));
		response.getWriter().append(String.format("<br><br><div align=\"center\"><a href=\"%s\" style=\"color:#242528;\">Продолжить</a></br></br>", redirect_page));
		response.getWriter().append("</body></html>");
	}
}
