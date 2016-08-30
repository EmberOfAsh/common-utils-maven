package com.zltel.common.utils.csv;

import java.util.ArrayList;
import java.util.List;

/**
 * Csv文件数据
 * 
 * @author Wangch
 *
 */
public class CsvFileBean {
	/**
	 * 文件头
	 */
	private List<String> columns = new ArrayList<String>();
	/**
	 * 文件数据
	 */
	private List<List<String>> datas = new ArrayList<List<String>>();

	public List<String> getColumns() {
		return columns;
	}

	public void setColumns(List<String> columns) {
		this.columns = columns;
	}

	public List<List<String>> getDatas() {
		return datas;
	}

	public void setDatas(List<List<String>> datas) {
		this.datas = datas;
	}

	public void addCol(String colName) {
		this.columns.add(colName);
	}

	public void addData(List<String> data) {
		datas.add(data);
	}

}
