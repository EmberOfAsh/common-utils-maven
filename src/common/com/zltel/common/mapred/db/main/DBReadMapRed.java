package com.zltel.common.mapred.db.main;

import org.apache.hadoop.mapred.lib.db.DBInputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.db.DBWritable;

public class DBReadMapRed extends DBWriteMapRed {

	/**
	 * 设置 数据库 输入信息
	 * 
	 * @param job
	 * @param inputClass
	 *            数据库读取类
	 * @param tableName
	 *            读取表名
	 * @param fields
	 *            读取字段
	 */
	public static void setDBIn(Job job, Class<? extends DBWritable> inputClass, String tableName, String[] fields) {
		DBInputFormat.setInput(job, inputClass, tableName, null, null, fields);
	}
}
