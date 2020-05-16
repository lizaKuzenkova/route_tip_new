package controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbaccess.User;
import dbmodel.Users;
import web.SessionHelper;

public class UserDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String user_id = request.getParameter("user_id");
    	User u = new User();
    	Users u1 = new Users();
		
		if (user_id == "" || user_id == null)
            request.getRequestDispatcher("/").forward(request, response);
        else {
        	int userId = Integer.parseInt(user_id);
        	u1 = u.getUserByUserId(userId);
        	
        	if (u1.getRoles().getRoleName().equals("администратор")) {
        		SessionHelper.setMessage(request, "Невозможно удалить администратора");
        	} else {
        		SessionHelper.setMessage(request, u.deleteUser(userId));
        	}
        }		
		response.sendRedirect("Info");
	}
}
