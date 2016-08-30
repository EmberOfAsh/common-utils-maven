package com.zltel.common.utils.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.zltel.common.utils.string.StringUtil;

/**
 * Java Bean 转换工具类
 * 
 * 
 * 
 * @author Wangch
 * @version 2015.10.10 新增 转换工具类
 * @version 2015.10.23 增加转换时 对 Map类型的识别
 * 
 */
public class BeanUtil {
	/**
	 * 转换 JavaBean 位 Map类型 key 为大写
	 * 
	 * @param t
	 *            要转换javabean 实例，Map类型不会转换
	 * @return
	 */
	public static <T> Map<String, Object> ConvertBeanToMap(T t) {

		return ConvertBeanToMap(t, true);
	}

	/**
	 * 转换 JavaBean 位 Map类型 key 为大写
	 * 
	 * @param t
	 * @param keyUpper
	 *            Map key是否转换为大写
	 * @return
	 */
	public static <T> Map<String, Object> ConvertBeanToMap(T t, boolean keyUpper) {
		if (null == t) {
			return null;
		}
		if (t instanceof Map) {// Map类型直接返回
			return (Map<String, Object>) t;
		}
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> ret = new HashMap<String, Object>();
		_convertoMap(t, t.getClass(), list);
		if (!list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				for (Map.Entry<String, Object> entry : list.get(i).entrySet()) {
					ret.put(keyUpper ? entry.getKey().toUpperCase() : entry.getKey(), entry.getValue());
				}
			}
		}
		return ret;
	}

	/**
	 * 转换 多个Map为JavaBean
	 * 
	 * @param configs
	 *            要转换的Map List
	 * @param c
	 *            转换后的JavaBean Class，Map 直接返回
	 * @return
	 */
	public static <T> List<T> ConvertMapToObjects(List<Map<String, Object>> configs, Class<T> c) {
		List<T> rets = new ArrayList<T>();
		for (Map<String, Object> map : configs) {
			rets.add(ConvertMapToObject(map, c));
		}
		return rets;
	}

	/**
	 * 转换 Map 为 Object
	 * 
	 * @param map
	 * @param c
	 *            转换后的JavaBean Class，Map 直接返回
	 * @return
	 */
	public static <T> T ConvertMapToObject(Map<String, Object> map, Class<T> c) {
		if (null == map || c == null) {
			return null;
		}

		T ret = null;
		try {
			ret = c.newInstance();
			_convertoObject(map, c, ret);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ret;
	}

	/**
	 * 内部方法，递归 遍历 JavaBean 类及其父类定义set方法，并注入属性值
	 * 
	 * @param map
	 * @param c
	 * @param t
	 */
	private static <T> void _convertoObject(Map<String, Object> map, Class<?> c, T t) {
		if (null == t) {
			return;
		}
		if (t instanceof Map) {// map 类型
			Map<String, Object> _t = (Map<String, Object>) t;
			_t.putAll(map);
			return;
		}
		Class<?> cs = c.getSuperclass();
		if (!(cs == Object.class)) {
			// 没有到 object ，
			_convertoObject(map, cs, t);
		}
		Method[] methods = c.getDeclaredMethods();
		String mn;
		String fn;
		Object v;
		for (Method method : methods) {
			mn = method.getName();
			Class[] pcs = method.getParameterTypes();
			if (mn.startsWith("set") && Modifier.isPublic(method.getModifiers()) && pcs.length == 1) {
				fn = mn.substring(3);
				v = map.get(fn.toUpperCase());
				if (v != null) {
					String vn = v.getClass().getName();
					String pn = pcs[0].getName();
					if (vn.equals(pn)) {
						try {
							method.invoke(t, v);
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						if (pn.equals(Date.class.getName()) && v instanceof Timestamp) {
							try {
								v = new Date(((Timestamp) v).getTime());
								method.invoke(t, v);
							} catch (Exception e) {
								e.printStackTrace();
							}
							continue;
						}

						StringBuffer traceMsg = new StringBuffer();
						StackTraceElement[] stes = Thread.currentThread().getStackTrace();
						for (StackTraceElement ste : stes) {
							traceMsg.append("\t at " + ste.getClassName() + "." + ste.getMethodName() + "("
									+ ste.getLineNumber() + ")\n");
						}
						throw new RuntimeException(
								" 参数类型不匹配： bean[" + fn + ":" + pn + "]," + "value:" + vn + "\n详细堆栈：" + traceMsg);

					}

				}

			}
		}
	}

	/**
	 * 内部方法，递归 遍历 JavaBean 类及其父类定义Get方法，并读取属性值
	 * 
	 * @param t
	 * @param _class
	 * @param list
	 */
	private static <T> void _convertoMap(T t, Class<?> _class, List<Map<String, Object>> list) {
		if (null == t) {
			return;
		}
		Class<?> c = _class;
		Class<?> cs = _class.getSuperclass();
		if (!(cs == Object.class)) {
			// 没有到 object ，
			_convertoMap(t, cs, list);
		}
		Map<String, Object> ret = new HashMap<String, Object>();
		Method[] methods = c.getDeclaredMethods();
		String mn;
		String fn;
		Object v;
		for (Method method : methods) {
			mn = method.getName();
			if (mn.startsWith("get") && Modifier.isPublic(method.getModifiers())) {
				fn = defaultFieldName(mn);
				try {
					v = method.invoke(t);
					if (v != null) {
						ret.put(fn, v);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		list.add(ret);
	}

	/**
	 * 获取 get/set方法 说关联的字段名
	 * 
	 * @param gsMethodName
	 *            get/set 方法名
	 * @return 字段名
	 */
	public static String defaultFieldName(String gsMethodName) {
		String mn = gsMethodName;
		if (StringUtil.isNullOrEmpty(gsMethodName)) {
			return null;
		}
		String _field = mn.substring(3);
		// 默认
		if (_field.length() <= 1) {
			return _field.toLowerCase();
		}
		char _c = _field.charAt(1);
		boolean isl = Character.isLowerCase(_c);
		String nmn = _field.substring(0, 2);
		nmn = isl ? nmn.toLowerCase() : nmn.toUpperCase();
		return nmn + _field.substring(2);
	}

	/**
	 * 获取所有 实例变量
	 * 
	 * @param c
	 * @return
	 */
	public static <T> Set<String> fields(Class<T> c) {
		Field[] fields = c.getDeclaredFields();
		Set<String> fs = new HashSet<String>();
		for (Field f : fields) {
			boolean isStatic = Modifier.isStatic(f.getModifiers());
			if (!isStatic)
				fs.add(f.getName());
		}
		return fs;
	}

	public static void main(String[] args) {

	}

}
