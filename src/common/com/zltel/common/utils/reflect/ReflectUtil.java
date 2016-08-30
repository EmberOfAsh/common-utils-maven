package com.zltel.common.utils.reflect;

import java.lang.reflect.Method;

import com.zltel.common.utils.string.StringUtil;

/**
 * 反射工具 提供快速 获取目标方法的目的
 * 
 * <h2>注意:此工具类只负责获取 目标本class所定义的class</h2>
 * 
 * @author Wangch
 *
 */
public class ReflectUtil {

	/**
	 * 根据方法名获取方法类型
	 * 
	 * @param methodname
	 *            方法名称
	 * @param ignoreCase
	 *            是否忽略大小写
	 * @param c
	 *            class对象
	 * @param paramTypes
	 *            方法参数类型
	 * @return 方法对象 如果有，没有返回 null
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 */
	public static Method getMethodByName(String methodname, boolean ignoreCase, Class<?> c, Class<?>... paramTypes)
			throws NoSuchMethodException, SecurityException {
		check(methodname, c);

		if (!ignoreCase) {
			// 直接返回
			return c.getMethod(methodname, paramTypes);
		}
		Method[] methods = c.getDeclaredMethods();
		for (Method method : methods) {
			String mn = method.getName();
			if (mn.toLowerCase().equals(methodname.toLowerCase().trim())) {
				return method;
			}
		}

		return null;
	}

	/**
	 * 获取 字段的 getter方法
	 * 
	 * @param field
	 *            字段名称
	 * @param c
	 *            类型
	 * @param paramTypes
	 *            参数
	 * @return 方法对象
	 * @throws SecurityException
	 */
	public static Method getFieldGetterMethod(String field, Class<?> c, Class<?>... paramTypes)
			throws SecurityException {
		String methodName = "get".concat(field);
		try {
			return getMethodByName(methodName, true, c, paramTypes);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取 字段的 setter 方法
	 * 
	 * @param field
	 *            字段名称
	 * @param c
	 *            类型
	 * @param paramTypes
	 *            参数
	 * @return 方法对象
	 * @throws SecurityException
	 */
	public static Method getFieldSetterMethod(String field, Class<?> c, Class<?>... paramTypes)
			throws SecurityException {
		String methodName = "set".concat(field);
		try {
			return getMethodByName(methodName, true, c, paramTypes);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 检查 必须参数
	 * 
	 * @param methodname
	 * @param c
	 */
	private static void check(String methodname, Class<?> c) {
		if (StringUtil.isNullOrEmpty(methodname)) {
			throw new RuntimeException("获取方法名不能为空!");
		}
		if (null == c) {
			throw new RuntimeException("Class 对象不能为空!");
		}
	}

}
