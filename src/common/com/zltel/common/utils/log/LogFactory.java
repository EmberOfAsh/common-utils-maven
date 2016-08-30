package com.zltel.common.utils.log;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;

import com.zltel.common.utils.conf.ConfigUtil;
import com.zltel.common.utils.log.impl.ComFileLogOut;
import com.zltel.common.utils.log.impl.HdfsLogOut;
import com.zltel.common.utils.string.StringUtil;

/**
 * Log 实现工厂类
 * 
 * @author Wangch
 * 
 */
public class LogFactory {
	public static final Map<String, Log> _logsMap = new Hashtable<String, Log>();
	public static Integer currentLevel = Log.INFO;
	static {
		// 从配置文件中加载 默认的 告警等级
		String level = ConfigUtil.getConfValue(ConfigUtil.CONF_FILE_LOG4J, "my.log.level", String.valueOf(Log.INFO));
		if (StringUtil.isNullOrEmpty(level) && StringUtil.isNum(level)) {
			currentLevel = Integer.valueOf(level);
		}
	}

	/**
	 * 获取日志输出
	 * 
	 * @param logPath
	 *            日志输出文件
	 * @param logName
	 *            日志输出名字
	 * @return 日志输出对象
	 */
	public static final Log getLog(String logPath, String logName) {
		Log _log = null;
		File _f = new File(logPath);
		if (!_f.exists()) {
			_f.mkdirs();
		}
		if (_f.isDirectory()) {
			String k = logPath + logName;
			synchronized (_logsMap) {
				_log = _logsMap.get(k);
				if (_log == null) {
					_log = new ComFileLogOut(logPath, logName);
					_logsMap.put(k, _log);
				}
			}

		}
		return _log;
	}

	/**
	 * 获取 Hdfs 输出的日志
	 * 
	 * @param config
	 *            job Config对象
	 * @param jobName
	 *            job名称 (唯一的job名称) 建议使用 job名称-时间戳
	 * @return Log对象
	 * @throws IOException
	 */
	@Deprecated
	public static Log getLog(Configuration config, String jobName) throws IOException {
		HdfsLogOut hlo = new HdfsLogOut(config, jobName);
		return hlo;
	}

	public static void main(String[] args) {
		getLog("d:/log_1/b/", "a");
	}

}
