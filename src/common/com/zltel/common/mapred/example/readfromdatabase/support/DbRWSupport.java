package com.zltel.common.mapred.example.readfromdatabase.support;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.hadoop.mapred.lib.db.DBWritable;

import com.zltel.common.mapred.example.readfromdatabase.part.SaveDataBean;

/**
 * 数据库 读写支持类
 * 
 * @author Wangch
 *
 */
public class DbRWSupport implements DBWritable {
	SaveDataBean saveDataBean;

	/**
	 * 默认构造函数，读取数据时用
	 */
	public DbRWSupport() {
		saveDataBean = new SaveDataBean();
	}

	/**
	 * 重载构造函数，保存数据时用
	 * 
	 * @param _saveDataBean
	 */
	public DbRWSupport(SaveDataBean _saveDataBean) {
		this.saveDataBean = _saveDataBean;
	}

	/**
	 * 将数据保存到数据库的处理
	 */
	public void write(PreparedStatement statement) throws SQLException {
		statement.setObject(1, saveDataBean.getUsername());
		statement.setObject(2, saveDataBean.getPassword());
	}

	/**
	 * 从数据库读取数据的处理
	 */
	public void readFields(ResultSet resultSet) throws SQLException {
		saveDataBean.setUsername(resultSet.getString(1));
		saveDataBean.setPassword(resultSet.getString(2));
	}

	/**
	 * @return the saveDataBean
	 */
	public SaveDataBean getSaveDataBean() {
		return saveDataBean;
	}

	/**
	 * @param saveDataBean
	 *            the saveDataBean to set
	 */
	public void setSaveDataBean(SaveDataBean saveDataBean) {
		this.saveDataBean = saveDataBean;
	}

}
