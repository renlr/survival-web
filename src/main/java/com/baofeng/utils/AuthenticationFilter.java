package com.baofeng.utils;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 功能：系统认证过滤器
 * 
 * @author RENLIANGRONG
 * */
public class AuthenticationFilter implements Filter {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		String uri = httpRequest.getRequestURI();
		String root = httpRequest.getContextPath() + "/";
		String details = httpRequest.getContextPath() + "/details.jsp";
		String home = httpRequest.getContextPath() + "/home.do";
		String logout = httpRequest.getContextPath() + "/user/logout.do";
		String login = httpRequest.getContextPath() + "/user/login.do";
		String init = httpRequest.getContextPath() + "/menu/init.do";

		String method = httpRequest.getMethod();
		if ("GET".equals(method) && uri.equals(login)) {
			httpResponse.sendRedirect(home + "?backurl=" + uri);
			return;
		}
		HttpSession session = httpRequest.getSession();
		if (session.getAttribute(Constants.CURRENT_USER) == null && uri.equals(login)) {
			httpResponse.sendRedirect(home + "?backurl=" + uri);
		}
		if (session.getAttribute(Constants.CURRENT_USER) != null || uri.equals(home) || uri.equals(login) || uri.equals(init) || uri.equals(logout) || uri.endsWith(".js")
				|| uri.endsWith(".css") || uri.contains("images") || uri.equals(root) || uri.equals(details)) {
			chain.doFilter(request, response);
		} else {
			httpResponse.sendRedirect(home + "?backurl=" + uri);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}
}
