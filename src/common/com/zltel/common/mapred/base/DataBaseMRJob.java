package com.zltel.common.mapred.base;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapred.lib.db.DBConfiguration;
import org.apache.hadoop.mapred.lib.db.DBInputFormat;
import org.apache.hadoop.mapred.lib.db.DBOutputFormat;
import org.apache.hadoop.mapred.lib.db.DBWritable;
import org.apache.hadoop.mapreduce.Job;

/**
 * 关系型数据库读写配置项设置
 * 
 * @author Wangch
 *
 */
public class DataBaseMRJob extends HBaseMRJob {
	/**
	 * 配置关系型数据库连接 信息
	 * 
	 * @param conf
	 * @param driveName
	 *            数据库驱动
	 * @param url
	 *            数据库url
	 * @param un
	 *            数据库用户名
	 * @param pwd
	 *            数据库密码
	 */
	public static void configureDB(Configuration conf, String driveName, String url, String un, String pwd) {
		DBConfiguration.configureDB(conf, driveName, url, un, pwd);
	}

	/**
	 * 设置关系型数据库 输入
	 * 
	 * @param job
	 * @param cls
	 *            数据库读取数据处理类
	 * @param tn
	 *            读取表名
	 * @param conditions
	 *            查询条件，like age>9 and age<100
	 * @param table_fields
	 *            要读取的字段
	 */
	public static void setInput(Job job, Class<? extends DBWritable> cls, String tn, String conditions,
			String[] table_fields) {
		DBInputFormat.setInput(job, cls, tn, conditions, null, table_fields);
	}

	/**
	 * 设置关系型数据库 写入
	 * 
	 * @param job
	 * @param tn
	 *            写入表名
	 * @param table_fields
	 *            写入字段
	 * @throws IOException
	 */
	public static void setOutput(Job job, String tn, String[] table_fields) throws IOException {
		DBOutputFormat.setOutput(job, tn, table_fields);
	}

}
