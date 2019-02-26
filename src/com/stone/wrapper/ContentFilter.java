package com.stone.wrapper;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.stone.javaweb.HttpFilter;

@WebFilter("/bbs.jsp")
public class ContentFilter extends HttpFilter {

	@Override
	public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		//1.获取请求参数content的值
		//String val = request.getParameter("content");
		
		//2.把其中fuck，shit等字符串替换为***
			//ServletRequest, HttpServletRequest中并没有提供诸如setParameter(paraName, paraValue)类似于这样的方法
			//目标：改变HttpServletRequest的getParameter(String)方法的行为：若该方法的返回值中包含"fuck"，则替换为"****"
			//1.若对于一个类的方法不满意，需要进行重写，最常见的方式是继承父类，重写方法。此处如果要重写setParameter(String)方法，
			//则需要继承org.apache.catalina.connector.RequestFacade@35480daa，而这仅是Tomcat服务器的实现，若更换服务器，则该方案无法使用
			//2.直接写一个HttpServletRequest接口的实现类：无法实现其中方法.
			//3.装饰目前的HttpServletRequest对象：装饰getParameter方法，而其他方法还和其实现相同
			//创建一个类，该类实现HttpServletRequest接口，把当前doFilter中的request传入到该类中，作为其成员变量，使用该成员变量去实现接口的全部方法
		MyHttpServletRequest req = new MyHttpServletRequest(request);
		
		//3.转到目标页面
		filterChain.doFilter(req, response);
	}
}