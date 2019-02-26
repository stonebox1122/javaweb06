package com.stone.privilege;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AuthorityServlet")
public class AuthorityServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String methodName = request.getParameter("method");
		try {
			Method method = getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
			method.invoke(this, request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private UserDao userDao = new UserDao();

	public void getAuthorities(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		User user = userDao.get(username);
		request.setAttribute("user", user);
		request.setAttribute("authorities", UserDao.getAuthorities());
		request.getRequestDispatcher("/app/authority-manager.jsp").forward(request, response);
	}
	
	public void updateAuthorities(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String[] urls = request.getParameterValues("authority");
		List<Authority> authorityList = userDao.getAuthorities(urls);
		userDao.update(username, authorityList);
		response.sendRedirect(request.getContextPath() + "/app/authority-manager.jsp");
	}
}
