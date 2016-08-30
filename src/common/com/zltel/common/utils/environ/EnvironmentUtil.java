package com.zltel.common.utils.environ;

/**
 * 运行 环境检测工具类
 * 
 * @author Wangch
 * 
 */
public class EnvironmentUtil {
	private static final String _classpath = EnvironmentUtil.class.getClassLoader().getResource("").getPath();

	/**
	 * 获取项目 class path 路径
	 * 
	 * @return
	 */
	public static String project_classpath() {
		return _classpath;
	}

	/**
	 * 获取 运行操作系统
	 * 
	 * @return
	 */
	public static String runtime_system() {
		String osn = System.getProperty("os.name");
		return osn;
	}

	public static void main(String[] args) {
		System.out.println(runtime_system());
	}
}
