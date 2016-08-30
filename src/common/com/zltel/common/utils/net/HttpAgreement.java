package com.zltel.common.utils.net;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.impl.LogFactoryImpl;

/**
 * <pre>
 * http 协议
 * HttpURLConnection连接帮助类
 * 
 * 20150209重构过
 * 
 * </pre>
 * 
 * @author xiaomeng.zou
 * @since JDK1.5
 * @date 2011-8-25
 * @time 上午09:38:39
 */
public class HttpAgreement {
	/**
	 * Logger for this class
	 */
	private static final Log logger = LogFactoryImpl.getLog(HttpAgreement.class);
	public static final String GET = "GET";
	public static final String POST = "POST";

	/**
	 * http 协议 根据URL 发送消息数据,得到返回字符串
	 * 
	 * @author zouxiaomeng @creationDate. 2015-2-9 下午04:42:53
	 * @param urlStr
	 *            连接地址
	 * @param message
	 *            需要发送的消息串
	 * @param charSet
	 *            编码
	 * @param method
	 *            方式（GET/POST）
	 * @param connectTimeout
	 *            连接超时时长
	 * @param readTimeout
	 *            读取超时时长
	 * @return 字符串
	 * @throws Exception
	 */
	public static String sendMessage(String urlStr, String message, String charSet, String method, int connectTimeout,
			int readTimeout) throws Exception {
		Map<String, String> header = new HashMap<String, String>();
		header.put("Content-Type", "application/x-www-form-urlencoded; charset=" + charSet);
		return privateSendMessage(null, header, urlStr, message, charSet, method, connectTimeout, readTimeout);
	}

	/**
	 * http 协议 根据URL 发送消息数据,得到返回字符串
	 * 
	 * @param urlStr
	 *            url地址
	 * @param message
	 *            消息
	 * @param charSet
	 *            字符编码
	 * @param method
	 *            方式（GET/POST）
	 * @return
	 * @throws Exception
	 * @author xiaomeng.zou
	 * @since JDK1.5
	 * @date 2011-9-6
	 * @time 下午03:50:24
	 */
	public static String sendMessage(String urlStr, String message, String charSet, String method) throws Exception {
		return sendMessage(urlStr, message, charSet, method, -1, -1);
	}

	/**
	 * http 协议 根据URL 发送消息数据,与GET方式发送。得到返回结果
	 * 
	 * @param urlStr
	 *            URL地址
	 * @param message
	 *            消息数据
	 * @param charSet
	 *            字符编码
	 * @return
	 * @throws Exception
	 * @author xiaomeng.zou
	 * @since JDK1.5
	 * @date 2011-9-6
	 * @time 下午03:53:28
	 */
	public static String sendMessageByGet(String urlStr, String message, String charSet) throws Exception {
		return sendMessage(urlStr, message, charSet, GET);
	}

	/**
	 * http 协议 根据URL 发送消息数据,与GET方式发送。得到返回结果
	 * 
	 * @author zouxiaomeng @creationDate. 2015-2-9 下午04:45:49
	 * @param urlStr
	 *            URL地址
	 * @param message
	 *            消息数据
	 * @param charSet
	 *            字符编码
	 * @param connectTimeout
	 *            连接超时时长
	 * @param readTimeout
	 *            读取超时时长
	 * @return
	 * @throws Exception
	 */
	public static String sendMessageByGet(String urlStr, String message, String charSet, int connectTimeout,
			int readTimeout) throws Exception {
		return sendMessage(urlStr, message, charSet, GET, connectTimeout, readTimeout);
	}

	/**
	 * http 协议 根据URL 发送消息数据,与POST方式发送。得到返回结果
	 * 
	 * @param urlStr
	 *            URL地址
	 * @param message
	 *            消息数据
	 * @param charSet
	 *            字符编码
	 * @return
	 * @throws Exception
	 * @author xiaomeng.zou
	 * @since JDK1.5
	 * @date 2011-9-6
	 * @time 下午03:54:38
	 */
	public static String sendMessageByPost(String urlStr, String message, String charSet) throws Exception {
		return sendMessage(urlStr, message, charSet, POST);
	}

	/**
	 * http 协议 根据URL 发送消息数据,与POST方式发送。得到返回结果
	 * 
	 * @author zouxiaomeng @creationDate. 2015-2-9 下午04:46:29
	 * @param urlStr
	 *            URL地址
	 * @param message
	 *            消息数据
	 * @param charSet
	 *            字符编码
	 * @param connectTimeout
	 *            连接超时时长
	 * @param readTimeout
	 *            读取超时时长
	 * @return
	 * @throws Exception
	 */
	public static String sendMessageByPost(String urlStr, String message, String charSet, int connectTimeout,
			int readTimeout) throws Exception {
		return sendMessage(urlStr, message, charSet, POST, connectTimeout, readTimeout);
	}

	/**
	 * http 协议 根据URL 发送消息数据,得到返回字符串
	 * 
	 * @author zouxiaomeng @creationDate. 2015-2-9 下午04:48:47
	 * @param proxy
	 *            代理
	 * @param header
	 *            头属性
	 * @param urlStr
	 *            URL地址
	 * @param message
	 *            消息数据
	 * @param charSet
	 *            字符编码
	 * @param method
	 *            方式（GET/POST）
	 * @param connectTimeout
	 *            连接超时时长
	 * @param readTimeout
	 *            读取超时时长
	 * @return
	 * @throws Exception
	 */
	public static String sendMessage(Proxy proxy, Map<String, String> header, String urlStr, String message,
			String charSet, String method, int connectTimeout, int readTimeout) throws Exception {
		return privateSendMessage(proxy, header, urlStr, message, charSet, method, connectTimeout, readTimeout);
	}

	/**
	 * http 协议 根据URL 发送消息数据,得到返回字符串
	 * 
	 * @author zouxiaomeng @creationDate. 2015-2-9 下午04:48:47
	 * @param header
	 *            头属性
	 * @param urlStr
	 *            URL地址
	 * @param message
	 *            消息数据
	 * @param charSet
	 *            字符编码
	 * @param method
	 *            方式（GET/POST）
	 * @param connectTimeout
	 *            连接超时时长
	 * @param readTimeout
	 *            读取超时时长
	 * @return
	 * @throws Exception
	 */
	public static String sendMessage(Map<String, String> header, String urlStr, String message, String charSet,
			String method, int connectTimeout, int readTimeout) throws Exception {
		return privateSendMessage(null, header, urlStr, message, charSet, method, connectTimeout, readTimeout);
	}

	private static String privateSendMessage(Proxy proxy, Map<String, String> header, String urlStr, String message,
			String charSet, String method, int connectTimeout, int readTimeout) throws Exception {
		BufferedOutputStream bos = null;
		StringBuffer sb = new StringBuffer();
		BufferedReader reader = null;
		char[] c = new char[1024];
		HttpURLConnection urlConnect = null;
		try {
			if (StringUtils.isNotBlank(charSet)) {
				charSet = "UTF-8";
			}
			URL url = new URL(urlStr);
			if (proxy == null) {
				urlConnect = (HttpURLConnection) url.openConnection();
			} else {
				// 设置代理
				urlConnect = (HttpURLConnection) url.openConnection(proxy);
			}

			if (connectTimeout > 0) {
				urlConnect.setConnectTimeout(connectTimeout);
			} else {
				try {// GlobalConfig.getIntegerProperty("sys.core",
						// "connectTimeout")
					urlConnect.setConnectTimeout(5000);
				} catch (Exception e) {
					logger.warn("Init connect time out and read time out error" + e);
				}
			}
			if (readTimeout > 0) {
				urlConnect.setReadTimeout(readTimeout);
			} else {
				try {
					urlConnect.setReadTimeout(5000);
				} catch (Exception e) {
					logger.warn("Init connect time out and read time out error " + e);
				}
			}
			if (header != null && (!header.isEmpty())) {
				// 设置头属性
				Set<Map.Entry<String, String>> set = header.entrySet();
				for (Map.Entry<String, String> entry : set) {
					urlConnect.setRequestProperty(entry.getKey(), entry.getValue());
				}
			}
			logger.info("connectTimeout=" + urlConnect.getConnectTimeout());
			logger.info("readTimeout=" + urlConnect.getReadTimeout());
			urlConnect.setRequestMethod(method);
			if (StringUtils.isNotBlank(message)) {
				if (!urlConnect.getDoOutput()) {
					urlConnect.setDoOutput(true);
				}
				bos = new BufferedOutputStream(urlConnect.getOutputStream());
				bos.write(message.getBytes(charSet));
				bos.flush();
			}
			if (!urlConnect.getDoInput()) {
				urlConnect.setDoInput(true);
			}
			reader = new BufferedReader(
					new InputStreamReader(new BufferedInputStream(urlConnect.getInputStream()), charSet));
			int i = 0;
			while ((i = reader.read(c)) != -1) {
				sb.append(c, 0, i);
			}
			String returnStr = sb.substring(0, sb.length());
			logger.info("Receve Message is :" + returnStr);
			return returnStr;
		} catch (Exception e) {
			logger.info(e);
			try {
				reader = new BufferedReader(
						new InputStreamReader(new BufferedInputStream(urlConnect.getErrorStream()), charSet));
				int i = 0;
				while ((i = reader.read(c)) != -1) {
					sb.append(c, 0, i);
				}
				logger.error(sb.toString());
			} catch (Exception e2) {
				logger.error(e2.getMessage());
			}
			throw e;
		} finally {
			if (reader != null) {
				reader.close();
			}
			if (bos != null) {
				bos.close();
			}
		}
	}
}