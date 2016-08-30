package com.zltel.common.mapred.db.main;

import java.io.IOException;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapred.lib.db.DBConfiguration;
import org.apache.hadoop.mapred.lib.db.DBOutputFormat;
import org.apache.hadoop.mapreduce.Job;

import com.zltel.common.utils.conf.ConfigUtil;

/**
 * 将结果输入到关系型数据库的 MapReduce main定义支持类
 * 
 * @author Wangch
 *
 */
public class DBWriteMapRed {

	/**
	 * 加载 连接到数据库的基础信息配置
	 * 
	 * @param conf
	 * @param _map
	 *            包含数据库连接配置信息的map
	 */
	public static void initDBConf(Configuration conf, Map<String, String> _map) {
		String driveName = ConfigUtil.getConfigValue(_map, "jdbc.mysql.driver", null);
		String url = ConfigUtil.getConfigValue(_map, "jdbc.mysql.url", null);
		String un = ConfigUtil.getConfigValue(_map, "jdbc.mysql.username", null);
		String pwd = ConfigUtil.getConfigValue(_map, "jdbc.mysql.password", null);

		String ip = ConfigUtil.getConfigValue(_map, "jdbc.mysql.ip", null);
		String port = ConfigUtil.getConfigValue(_map, "jdbc.mysql.port", null);
		String ins = ConfigUtil.getConfigValue(_map, "jdbc.mysql.instance", null);

		String nurl = url.replace("${jdbc.mysql.ip}", ip).replace("${jdbc.mysql.port}", port)
				.replace("${jdbc.mysql.instance}", ins);
		DBConfiguration.configureDB(conf, driveName, nurl, un, pwd);
	}

	/**
	 * 设置 数据库输出的表信息
	 * 
	 * @param job
	 * @param tableName
	 *            输出数据库表名
	 * @param fields
	 *            输出数据库字段值
	 * @throws IOException
	 */
	public static void setDBOut(Job job, String tableName, String[] fields) throws IOException {
		DBOutputFormat.setOutput(job, tableName, fields);
	}
}
