package com.zltel.common.utils.csv;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import com.zltel.common.utils.string.StringUtil;

public class CsvFileUtil {
	/**
	 * 创建 Csv文件输入流
	 * 
	 * @param csv
	 * @return
	 * @throws Exception
	 */
	public static InputStream creatCsvFile(CsvFileBean csv) throws Exception {

		return creatCsvFile(csv, null);
	}

	/**
	 * 创建 Csv文件输入流
	 * 
	 * @param csv
	 * @param charset
	 *            文件内容编码方式,默认GBK编码
	 * @return
	 * @throws Exception
	 */
	public static InputStream creatCsvFile(CsvFileBean csv, String charset) throws Exception {
		if (StringUtil.isNullOrEmpty(charset))
			charset = "GBK";

		return new ByteArrayInputStream(creatCsvFileStr(csv).getBytes(charset));
	}

	/**
	 * 转化 csv 数据为字符串
	 * 
	 * @param csv
	 * @return
	 */
	public static String creatCsvFileStr(CsvFileBean csv) {
		StringBuilder sb = new StringBuilder();
		List<String> cols = csv.getColumns();
		if (cols != null && cols.size() > 0) {
			for (int i = 0; i < cols.size() - 1; i++) {
				sb.append("=\"").append(cols.get(i)).append("\"").append(",");
			}
			sb.append("=\"").append(cols.get(cols.size() - 1)).append("\"").append("\n");
		}
		List<List<String>> datas = csv.getDatas();
		if (datas != null && datas.size() > 0) {
			for (List<String> data : datas) {
				if (data != null && data.size() > 0) {
					for (int j = 0; j < data.size() - 1; j++) {
						sb.append("=\"").append(data.get(j)).append("\"").append(",");
					}
					sb.append("=\"").append(data.get(data.size() - 1)).append("\"").append("\n");
				}
			}
		}
		return sb.toString();
	}
}
