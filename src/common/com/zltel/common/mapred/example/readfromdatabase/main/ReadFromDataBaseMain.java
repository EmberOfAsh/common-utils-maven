package com.zltel.common.mapred.example.readfromdatabase.main;

import java.io.IOException;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.lib.db.DBConfiguration;
import org.apache.hadoop.mapred.lib.db.DBInputFormat;
import org.apache.hadoop.mapred.lib.db.DBOutputFormat;
import org.apache.hadoop.mapreduce.Job;

import com.zltel.common.mapred.example.readfromdatabase.map.ReadFromDataBaseMap;
import com.zltel.common.mapred.example.readfromdatabase.reduce.ReadFromDataBaseReduce;
import com.zltel.common.mapred.example.readfromdatabase.support.DbRWSupport;
import com.zltel.common.utils.conf.ConfigUtil;

public class ReadFromDataBaseMain {
	private static final String table_name = "Default_Threshold";
	private static final String[] table_fields = { "username", "password" };

	static Map<String, String> _map = ConfigUtil.resolveConfigProFile("jdbc.properties");

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
	public static void setDBOut(Job job, String tableName, String[] fields) throws Exception {
		DBOutputFormat.setOutput(job, tableName, fields);
	}

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		// 设置 jdbc 连接属性
		initDBConf(conf, _map);

		Job job = Job.getInstance(conf, "从数据库读取数据");
		job.setJarByClass(ReadFromDataBaseMain.class);
		// 此句 在发布的时候删除
		job.setMapperClass(ReadFromDataBaseMap.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);

		job.setReducerClass(ReadFromDataBaseReduce.class);
		job.setOutputKeyClass(DbRWSupport.class);
		job.setOutputValueClass(DbRWSupport.class);
		// 设置 数据库输入
		DBInputFormat.setInput(job, DbRWSupport.class, table_name, null, null, table_fields);
		// 设置 数据库输出的表名 和 字段列
		DBOutputFormat.setOutput(job, table_name, table_fields);

		job.setNumReduceTasks(30);
		job.waitForCompletion(true);
	}
}
