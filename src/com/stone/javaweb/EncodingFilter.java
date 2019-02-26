package com.stone.javaweb;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter(urlPatterns="/*")
public class EncodingFilter extends HttpFilter{

	private String encoding;
	
	@Override
	protected void init() {
		encoding = getFilterConfig().getServletContext().getInitParameter("encoding");
	}
	
	@Override
	public void doFilter(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		System.out.println(encoding); 
		request.setCharacterEncoding(encoding);
		filterChain.doFilter(request, response);
	}
}