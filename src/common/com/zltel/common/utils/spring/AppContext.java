package com.zltel.common.utils.spring;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class AppContext {
	private static final Log logger = LogFactory.getLog(AppContext.class);
	private static ApplicationContext ctx = null;
	private static ServletContext context = null;

	public static ApplicationContext getApplicationContext() {
		if (ctx == null) {
			synchronized (AppContext.class) {
				if (ctx == null) {
					ctx = WebApplicationContextUtils.getWebApplicationContext(getContext());
					logger.info("com.zltel.common.utils.spring.AppContext Spring 容器加载成功-----------------");
				}
			}
		}
		return ctx;
	}

	/**
	 * @return the context
	 */
	public static final ServletContext getContext() {
		return context;
	}

	/**
	 * @param context
	 *            the context to set
	 */
	public static final void setContext(ServletContext context) {
		AppContext.context = context;
	}

	/**
	 * 根据spring beanid获取管理的实例
	 * 
	 * @param beanID
	 * @return 实例
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanID) {
		return (T) getApplicationContext().getBean(beanID);
	}

	/**
	 * 根据spring 返回类型 获取管理的实例
	 * 
	 * @param c
	 *            返回实例类型
	 * @return 实例
	 */
	public static <T> T getBean(Class<T> c) {
		return (T) getApplicationContext().getBean(c);
	}

	/**
	 * 根据spring beanid ,返回类型 获取管理的实例
	 * 
	 * @param beanID
	 * @param c
	 *            返回实例类型
	 * @return 实例
	 */
	public static <T> T getBean(String beanID, Class<T> c) {
		return (T) getApplicationContext().getBean(beanID, c);
	}

	/**
	 * @param ctx
	 *            the ctx to set
	 */
	public static final void setCtx(ApplicationContext ctx) {
		AppContext.ctx = ctx;
	}

	/**
	 * @return the ctx
	 */
	public static final ApplicationContext getCtx() {
		return ctx;
	}
}
