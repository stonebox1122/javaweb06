package com.stone.privilege;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.stone.javaweb.HttpFilter;

@WebFilter("/app/*")
public class AuthorityFilter extends HttpFilter {

	@Override
	public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		//获取 servletPath, 类似于 /app/article1.jsp
		String servletPath = request.getServletPath();
		
		//不需要被拦截的url列表
		List<String> uncheckServletPaths = Arrays.asList("/app/403.jsp","/app/articles.jsp","/app/authority-manager.jsp","/app/login.jsp","/app/logout.jsp");
		if (uncheckServletPaths.contains(servletPath)) {
			filterChain.doFilter(request, response);
			return;
		}
		
		//在用户已经登录(可使用 用户是否登录 的过滤器)的情况下, 获取用户信息. session.getAttribute("user")
		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			response.sendRedirect(request.getContextPath() + "/app/login.jsp");
			return;
		}
		
		//再获取用户所具有的权限的信息: List<Authority>
		List<Authority> authorities = user.getAuthorities();
		
		//检验用户是否有请求  servletPath 的权限: 可以思考除了遍历以外, 有没有更好的实现方式
		Authority authority = new Authority(null, servletPath);
		//若有权限则: 响应（此处需要重写Authority的hashCode()和equals()方法，只判断url即可）
		if (authorities.contains(authority)) {
			filterChain.doFilter(request, response);
			return;
		}
		
		//若没有权限: 重定向到 403.jsp 
		response.sendRedirect(request.getContextPath() + "/app/403.jsp");
		return;
	}
}