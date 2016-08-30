package com.zltel.common.mapred.db.dbwriter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.hadoop.mapred.lib.db.DBWritable;

/**
 * 数据库 读写支持类，用于 MapReduce从关系型数据库 保存和读取数据
 * 
 * <pre>
 * // 使用方法：
 * // 继承本类，实现write 和 read 两个方法，并设置数据库读写类为本类
 * DBInputFormat.setInput(job, DBWriterSupport.class, table_name, null, null, table_fields);
 * </pre>
 * 
 * @author Wangch
 *
 * @param <T>
 */
public abstract class DBWriterSupport<T> implements DBWritable {

	public DBWriterSupport() {

	}

	public void write(PreparedStatement statement) throws SQLException {
		write(statement, getValue());
	}

	public void readFields(ResultSet resultSet) throws SQLException {
		read(resultSet);
	}

	/**
	 * 保存到数据库的书写方法
	 * 
	 * @param statement
	 * @param v
	 */
	public abstract void write(PreparedStatement statement, T v) throws SQLException;

	/**
	 * 读取数据库数据方法
	 * 
	 * @param resultSet
	 * @return
	 */
	public abstract void read(ResultSet resultSet) throws SQLException;

	/**
	 * 返回 结果对象
	 * 
	 * @return
	 */
	public abstract T getValue();
}
