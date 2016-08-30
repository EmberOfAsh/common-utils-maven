package com.zltel.common.utils.junit;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 使用 ClassPathXmlApplicationContext创建
 * 
 * @author Wangch
 *
 */
public class BaseSpringClassPathXMLPathJunitTest {
	private static ApplicationContext ctx = null;

	static {
		initSprintAppcontext();
	}

	/**
	 * 加载XML
	 * 
	 * @param path
	 */
	public static void initSprintAppcontext(String path) {
		ctx = new ClassPathXmlApplicationContext(path);
	}

	/**
	 * 加载 Spring Context 文件
	 */
	private static void initSprintAppcontext() {
		initSprintAppcontext("spring/appContext-*.xml");
	}

	/**
	 * 根据 类型获取实例
	 * 
	 * @param c
	 *            类型
	 * @return
	 */
	public static <T> T getInstince(Class<T> c) {
		return (T) ctx.getBean(c);
	}

	/**
	 * 获取 Spring实现
	 * 
	 * @param <T>
	 * @param beanId
	 * @param c
	 *            获取 实现的接口或实现类 class
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getInstince(String beanId, Class<T> c) {
		return (T) ctx.getBean(beanId, c);
	}

	/**
	 * 获取 Spring实现
	 * 
	 * @param <T>
	 * @param beanId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getInstince(String beanId) {
		return (T) ctx.getBean(beanId);
	}

	public static ApplicationContext getCtx() {
		return ctx;
	}

}
