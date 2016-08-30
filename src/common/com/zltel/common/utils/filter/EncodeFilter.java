package com.zltel.common.utils.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EncodeFilter implements Filter {

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		req.setCharacterEncoding("utf-8");// 设置编码
		HttpServletResponse rp = (HttpServletResponse) response;// 添加 头信息
		/*
		 * 给您的网站添加X-Frame-Options响应头，赋值有如下三种， 1、DENY：无论如何不在框架中显示；
		 * 2、SAMEORIGIN：仅在同源域名下的框架中显示 3、ALLOW-FROM
		 * uri：仅在指定域名下的框架中显示。如Apache修改配置文件添加“Header always append
		 * X-Frame-Options SAMEORIGIN”；Nginx修改配置文件“add_header X-Frame-Options
		 * SAMEORIGIN;”。
		 */
		rp.setHeader("X-Frame-Options", "SAMEORIGIN");
		chain.doFilter(request, response);
	}

	public void init(FilterConfig arg0) throws ServletException {
	}

}
