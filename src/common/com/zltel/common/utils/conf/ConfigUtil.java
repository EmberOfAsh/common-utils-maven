package com.zltel.common.utils.conf;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zltel.common.utils.file.FileUtil;
import com.zltel.common.utils.io.StreamUtil;
import com.zltel.common.utils.string.StringUtil;

/**
 * 公共的配置文件 管理配置文件
 * 
 * @author Wangch
 * 
 */
public class ConfigUtil {
	private static final Log logout = LogFactory.getLog(ConfigUtil.class);

	public static final String CONF_FILE_SYSTEM = "system.properties";
	public static final String CONF_FILE_LOG4J = "log4j.properties";

	// 发布的时候需要调整该路径
	public static final String CLASSPATH = ConfigUtil.class.getClassLoader().getResource("").getPath();

	public static final Map<String, Map<String, String>> _savedConfig = new Hashtable<String, Map<String, String>>();

	/**
	 * 解析配置文件 配置文件 默认3个位置
	 * <ul>
	 * <li>classpath:下</li>
	 * <li>classpath:/conf/下</li>
	 * <li>classpath:/../conf/下</li>
	 * <li>
	 * </ul>
	 * 如果没有继续找到，那么回去 jar的目录下寻找
	 * <ul>
	 * <li>jar-classpath:</li>
	 * <li>jar-classpath:conf/
	 * </ul>
	 * 
	 * @param fileName
	 *            配置文件名
	 * @param classLoader
	 *            指定寻找和加载资源加载器，没有则尝试使用默认的加载器
	 * @return
	 */
	public static Map<String, String> resolveConfigProFile(String fileName, ClassLoader classLoader) {
		if (StringUtil.isNullOrEmpty(fileName)) {
			return null;
		}

		fileName = fileName.trim();
		Map<String, String> _map = _savedConfig.get(fileName);
		if (_map == null) {
			synchronized (_savedConfig) {
				_map = _savedConfig.get(fileName);
				if (_map == null) {
					boolean find = false;
					InputStream is = null;

					String fp = null;
					try {
						for (String p : new String[] { "../conf/", "./", "./conf/" }) {
							fp = CLASSPATH + p + fileName;
							if (FileUtil.isFileAndExists(fp)) {
								find = true;
								is = new FileInputStream(fp);
								break;
							}
						}
						if (!find) {
							// 从 jar包中去找
							// 网上资料说 使用 ClassLoader加载资源时不能使用 / 开头 , 因此调整到最后面
							for (String p : new String[] { "conf/", "", "/conf/", "/", }) {
								fp = p + fileName;
								// 调用位置的加载器,线程加载器,本类的加载器
								List<ClassLoader> loaders = new ArrayList<ClassLoader>();
								loaders.add(classLoader);
								ClassLoader tl = _getThreadLoader();
								ClassLoader ll = _getLocalLoader();
								loaders.add(tl);
								if (tl != ll) {
									loaders.add(ll);
								}
								for (ClassLoader loader : loaders) {
									if (loader == null) {
										continue;
									}
									is = loader.getResourceAsStream(p + fileName);
									if (is != null) {
										find = true;
										break;
									}
								}
								if (find) {
									break;
								}
							}
						}
						if (find) {
							_map = _resolveConfigProFile(is, fp);
							_savedConfig.put(fileName, _map);
						} else {
							logout.warn("没有找到配置文件:" + fileName);
						}

					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						StreamUtil.close(is);
					}
				}
			}
		}
		return _map;
	}

	/**
	 * 解析配置文件 配置文件 默认3个位置
	 * <ul>
	 * <li>classpath:下</li>
	 * <li>classpath:/conf/下</li>
	 * <li>classpath:/../conf/下</li>
	 * <li>
	 * </ul>
	 * 如果没有继续找到，那么回去 jar的目录下寻找
	 * <ul>
	 * <li>jar-classpath:</li>
	 * <li>jar-classpath:conf/
	 * </ul>
	 * 
	 * @param calledClass
	 *            指定调用类，如果指定，则会优先使用调用类的类加载器来寻找和加载资源，没有则尝试使用默认的加载器
	 * @param fileName
	 *            配置文件 文件名
	 * @return
	 */
	public static Map<String, String> resolveConfigProFile(Class<?> calledClass, String fileName) {
		ClassLoader loader = null;
		if (calledClass != null) {
			loader = calledClass.getClassLoader();
		}
		return resolveConfigProFile(fileName, loader);
	}

	/**
	 * 解析配置文件 配置文件 默认3个位置
	 * <ul>
	 * <li>classpath:下</li>
	 * <li>classpath:/conf/下</li>
	 * <li>classpath:/../conf/下</li>
	 * <li>
	 * </ul>
	 * 如果没有继续找到，那么回去 jar的目录下寻找
	 * <ul>
	 * <li>jar-classpath:</li>
	 * <li>jar-classpath:conf/
	 * </ul>
	 * 此处使用 默认的加载器来加载资源
	 * 
	 * @param fileName
	 *            配置文件名
	 * @return 配置文件的键值对
	 */
	public static Map<String, String> resolveConfigProFile(String fileName) {
		return resolveConfigProFile(fileName, null);
	}

	/**
	 * 解析指定 文件绝对路径的配置文件, 使用默认的加载器加载
	 * 
	 * @param filePath
	 *            文件全路径,不会推测
	 * @return
	 */
	public static Map<String, String> resolveConfigFilePath(String filePath) {
		return resolveConfigFilePath(filePath, null);
	}

	/**
	 * 解析指定 文件绝对路径的配置文件
	 * 
	 * @warn 本方法不适用缓存，每次都会重新加载文件
	 * @param filePath
	 *            文件全路径,不会推测
	 * @param classLoader
	 *            加载器，如果不指定，则使用默认的
	 * @return
	 */
	public static Map<String, String> resolveConfigFilePath(String filePath, ClassLoader classLoader) {
		if (StringUtil.isNullOrEmpty(filePath)) {
			return null;
		}
		String fp = null;
		Map<String, String> _map = null;
		filePath = filePath.trim();
		boolean find = false;
		InputStream is = null;
		try {
			fp = filePath;
			if (FileUtil.isFileAndExists(fp)) {
				find = true;
				is = new FileInputStream(fp);
			}
			if (!find) {
				fp = CLASSPATH + filePath;
				if (FileUtil.isFileAndExists(fp)) {
					find = true;
					is = new FileInputStream(fp);
				}
			}
			if (!find) {
				// 调用位置的加载器,线程加载器,本类的加载器
				List<ClassLoader> loaders = new ArrayList<ClassLoader>();
				loaders.add(classLoader);
				ClassLoader tl = _getThreadLoader();
				ClassLoader ll = _getLocalLoader();
				loaders.add(tl);
				if (tl != ll) {
					loaders.add(ll);
				}
				for (ClassLoader loader : loaders) {
					if (loader == null) {
						continue;
					}
					fp = filePath;
					is = loader.getResourceAsStream(fp);
					if (is != null) {
						find = true;
						break;
					}
					// 增加 或 删除 /
					if (filePath.startsWith("/")) {
						fp = filePath.substring(1);
					} else {
						fp = "/" + filePath;
					}
					is = loader.getResourceAsStream(fp);
					if (is != null) {
						find = true;
						break;
					}
				}
			}
			if (find) {
				_map = _resolveConfigProFile(is, filePath);
				logout.info("加载配置文件:" + filePath);
			} else {
				logout.warn("没有找到配置文件:" + filePath);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			StreamUtil.close(is);
		}
		return _map;
	}

	/**
	 * 解析配置文件
	 * 
	 * @param is
	 *            配置文件输入流
	 * @param fileNamePath
	 *            配置文件名
	 * @return 解析后的配置文件键值对
	 */
	public static Map<String, String> _resolveConfigProFile(InputStream is, String fileNamePath) {
		if (is == null) {
			return null;
		}
		Map<String, String> map = new HashMap<String, String>();
		try {
			Properties pro = new Properties();
			pro.load(is);
			Enumeration<?> keys = pro.propertyNames();
			while (keys.hasMoreElements()) {
				String elem = (String) keys.nextElement();
				map.put(new String(elem.getBytes("iso-8859-1"), "utf-8").trim(),
						new String(pro.getProperty(elem).getBytes("iso-8859-1"), "utf-8").trim());
			}
			logout.info("加载系统配置文件: " + fileNamePath);
		} catch (IOException e) {
			throw new RuntimeException("加载配置文件：" + fileNamePath + "出错", e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					throw new RuntimeException("配置文件：" + fileNamePath + "不能关闭", e);
				}
			}
		}
		return map;
	}

	/**
	 * 获取 当前类的 加载器
	 * 
	 * @return
	 */
	public static ClassLoader _getLocalLoader() {

		return ConfigUtil.class.getClassLoader();
	}

	/**
	 * 获取默认 线程提供的类加载器
	 * 
	 * @return
	 */
	public static ClassLoader _getThreadLoader() {

		return Thread.currentThread().getContextClassLoader();
	}

	/**
	 * 获取配置文件中 指定目标项的值
	 * 
	 * @param fn
	 *            文件名称
	 * @param key
	 *            指标项名称
	 * @param defaultValue
	 *            如果没有找到所使用的默认值
	 * @return 配置文件中的值或默认值
	 */
	public static String getConfValue(String fn, String key, String defaultValue) {
		String ret = defaultValue;
		Map<String, String> _map = resolveConfigProFile(fn);
		if (_map != null) {
			String v = _map.get(key);
			ret = StringUtil.isNotNullAndEmpty(v) ? v.trim() : ret;
		}
		return ret;
	}

	/**
	 * 获取 配置文件中 指定配置项的值
	 * 
	 * @param map
	 *            配置Map
	 * @param key
	 *            配置项值
	 * @param defaultValue
	 *            默认值，如果没有该配置项这 返回该默认值
	 * @return 配置文件中的值或默认值
	 */
	public static String getConfigValue(Map<?, ?> map, String key, String defaultValue) {
		String ret = defaultValue;
		if (map != null) {
			String v = (String) map.get(key);
			ret = StringUtil.isNullOrEmpty(v) ? defaultValue : v.toString().trim();
		}
		return ret;
	}

	public static void main(String[] args) {
		String v = ConfigUtil.getConfValue(ConfigUtil.CONF_FILE_SYSTEM, "DEBUG.OID", "defaultValue");

		System.out.println(v);
		v = ConfigUtil.getConfValue(ConfigUtil.CONF_FILE_SYSTEM, "DEBUG.OID", "defaultValue");

		System.out.println(ConfigUtil._savedConfig);
	}

}
