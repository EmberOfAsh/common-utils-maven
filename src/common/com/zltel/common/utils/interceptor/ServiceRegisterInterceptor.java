package com.zltel.common.utils.interceptor;

import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * @author create 主要提供spring服务自动注入action
 */
public class ServiceRegisterInterceptor extends AbstractInterceptor {
	private static final Log logger = LogFactory.getLog(ServiceRegisterInterceptor.class);

	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {
		logger.debug("action开始进入服务注册拦截器");
		// 获取action的class类
		Class c = actionInvocation.getAction().getClass();
		Method[] methods = c.getDeclaredMethods();
		for (Method method : methods) {
			method.setAccessible(true);
			if (method.getName().startsWith("set")) {
				String fieldName = method.getName().trim().substring(3);
				fieldName = fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1);
				// 是不是spring bean属性的时候，会抛异常
				try {
					Object obj = com.zltel.common.utils.spring.AppContext.getBean(fieldName);
					if (obj != null) {
						method.invoke(actionInvocation.getAction(), obj);
					}
				} catch (Exception e) {
				}
			}
		}
		// Field[] fields = c.getDeclaredFields();
		// for (Field field : fields) {
		// field.setAccessible(true);
		// String fieldName = field.getName();
		// //是不是spring bean属性的时候，会抛异常
		// try{
		// Object obj = AppContext.getBean(fieldName);
		// if(obj != null){
		// field.set(actionInvocation.getAction(), obj);
		// }
		// }catch(Exception e){}
		// }
		logger.debug("action服务注册完成");
		return actionInvocation.invoke();
	}

}
