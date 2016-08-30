package com.zltel.common.utils.spring;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class WebAppRotListener implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent sce) {
		AppContext.setContext(null);
	}

	public void contextInitialized(ServletContextEvent sce) {
		AppContext.setContext(sce.getServletContext());
	}

}
