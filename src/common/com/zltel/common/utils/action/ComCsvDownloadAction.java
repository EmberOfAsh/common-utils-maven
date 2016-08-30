package com.zltel.common.utils.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.impl.LogFactoryImpl;

import com.zltel.common.utils.csv.CsvFileBean;
import com.zltel.common.utils.csv.CsvFileUtil;

/**
 * 公共 csv 文件下载 Action
 * 
 * @author Wangch
 *
 */
public class ComCsvDownloadAction extends SuperAction {
	private static final Log logout = LogFactoryImpl.getLog(ComCsvDownloadAction.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<String> columns;
	private List<List<String>> datas;

	private String filename = "download.csv";
	private String contentType = "text/plain";

	private InputStream inputStream;

	public String download() {
		try {
			CsvFileBean csvFileBean = new CsvFileBean();
			csvFileBean.setColumns(columns);
			csvFileBean.setDatas(datas);
			this.inputStream = CsvFileUtil.creatCsvFile(csvFileBean);
		} catch (Exception e) {
			logout.error(e.getMessage(), e);
		}
		return SUCCESS;
	}

	/**
	 * @return the columns
	 */
	public final List<String> getColumns() {
		return columns;
	}

	/**
	 * @return the datas
	 */
	public final List<List<String>> getDatas() {
		return datas;
	}

	/**
	 * @return the filename
	 * @throws UnsupportedEncodingException
	 */
	public final String getFilename() throws UnsupportedEncodingException {
		return new String(this.filename.getBytes(), "ISO-8859-1");
	}

	/**
	 * @return the contentType
	 */
	public final String getContentType() {
		return contentType;
	}

	/**
	 * @param columns
	 *            the columns to set
	 */
	public final void setColumns(List<String> columns) {
		this.columns = columns;
	}

	/**
	 * @param datas
	 *            the datas to set
	 */
	public final void setDatas(List<List<String>> datas) {
		this.datas = datas;
	}

	/**
	 * @param filename
	 *            the filename to set
	 */
	public final void setFilename(String filename) {
		this.filename = filename;
	}

	/**
	 * @param contentType
	 *            the contentType to set
	 */
	public final void setContentType(String contentType) {
		this.contentType = contentType;
	}

	/**
	 * @return the serialversionuid
	 */
	public static final long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the inputStream
	 */
	public final InputStream getInputStream() {
		return inputStream;
	}

	/**
	 * @param inputStream
	 *            the inputStream to set
	 */
	public final void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

}
