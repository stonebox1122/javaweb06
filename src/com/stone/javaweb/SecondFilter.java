package com.stone.javaweb;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class SecondFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("second init...");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		System.out.println("3. Before SecondFilter's chain.doFilter ..."); //3
		
		//放行
		chain.doFilter(request, response);
		
		System.out.println("4. After SecondFilter's chain.doFilter ..."); //4
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		System.out.println("second destroy");
	}
}