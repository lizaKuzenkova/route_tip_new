package web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionHelper {

	public static void typeSessionHeader(HttpServletRequest request, HttpServletResponse response) throws IOException {
		typeSessionHeader(request, response, null);
	}
	
	public static void typeSessionHeader(HttpServletRequest request, HttpServletResponse response, String style) throws IOException {

		String session_user_fn = null;
		String session_user_ln = null;
		String session_user_id = null;

		HttpSession httpsess = request.getSession(false);

		if (httpsess != null) {
			session_user_fn = (String) httpsess.getAttribute("user_fn");
			session_user_ln = (String) httpsess.getAttribute("user_ln");
			session_user_id = (String) httpsess.getAttribute("user_id");
		}

		if (session_user_id == null)
			session_user_id = "";

		if (session_user_fn == null)
			session_user_fn = "";
		
		String enter_label = session_user_fn != "" ? session_user_fn + " " + session_user_ln : "Вход";
				
		if (style == null) {
			style = "nostyle";
		}

		String header = String.format(
				"<div align=\"right\"><a href=\"%s\" class=\"%s\">" + enter_label + "</a></div>"
				+ "<div align=\"right\"><a href=\"UserRegistration\" class=\"%s\">Регистрация</a></div>", (session_user_id == "" ? "Login" : "UserUpdate?user_id=" + session_user_id ), style, style);

		response.getWriter().append(header);
	}
	
	public static void setUserId(HttpServletRequest request, String user_id) {
		HttpSession httpsess = request.getSession(false);
		httpsess.setAttribute("user_id", user_id);
	}

	public static String getUserId(HttpServletRequest request) {

		HttpSession httpsess = request.getSession(false);

		String retval = null; 
		
		if (httpsess != null) {
			retval = (String) httpsess.getAttribute("user_id");
		} 
		
		if (retval == null || retval == "") {
			retval = "-1";
		}

		return retval;
	}

	public static boolean IsAdmin(HttpServletRequest request) {
		HttpSession httpsess = request.getSession(false);

		boolean retval = false;
		if (httpsess != null) {
			retval = httpsess.getAttribute("role").toString().equals("администратор") ? true : false;
		}
		return retval;
	}

	public static boolean IsUser(HttpServletRequest request) {
		HttpSession httpsess = request.getSession(false);

		boolean retval = false;
		if (httpsess != null) {
			retval = httpsess.getAttribute("role").toString().equals("пользователь") ? true : false;
		}
		return retval;
	}
	
	
	public static void setMessage (HttpServletRequest request, String message) {
		HttpSession httpsess = request.getSession();
		httpsess.setAttribute("info_message", message);
	}
	
	public static String getMessage (HttpServletRequest request) {
		HttpSession httpsess = request.getSession(false);
		return (String)httpsess.getAttribute("info_message");
	}
}
