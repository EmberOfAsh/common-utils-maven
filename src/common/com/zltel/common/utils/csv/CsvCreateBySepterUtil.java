package com.zltel.common.utils.csv;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import com.zltel.common.utils.string.StringUtil;

/**
 * 创建标准CSV文件格式工具类
 * 
 * @author Wangch
 * 
 * @history
 *          <ul>
 *          <li>2016.8.18 完成创建标准格式,并支持自定义分隔符.</li>
 *          </ul>
 *
 */
public class CsvCreateBySepterUtil {
	public static final String START_STR = "\"";
	public static final String END_STR = "\"";
	public static final String SEPTER_STR = ",";
	public static final String TABLE_STR = "\t";

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

		return new ByteArrayInputStream(creatCsvFileStr(csv).toString().getBytes(charset));
	}

	/**
	 * 按给定条件创建 CSV 默认条件： 以引号("),逗号(,)分隔数据,对引号(")进行转义
	 * 
	 * @param csv数据来源
	 * 
	 * @return CSV字符串
	 */
	public static StringBuilder creatCsvFileStr(CsvFileBean csv) {
		return creatCsvFileStr(csv, START_STR, END_STR, SEPTER_STR, true);
	}

	/**
	 * 按给定条件创建 CSV 返回字符串
	 * 
	 * @param csv
	 *            数据来源
	 * @param startStr
	 *            单元格开始字符串
	 * @param endStr
	 *            单元格结束字符串
	 * @param septerStr
	 *            单元格分割字符串
	 * @param escape
	 *            是否对 (")字符进行转义
	 * @return CSV字符串
	 */
	public static StringBuilder creatCsvFileStr(CsvFileBean csv, String startStr, String endStr, String septerStr,
			boolean escape) {
		if (null == startStr) {
			startStr = START_STR;
		}
		if (null == endStr) {
			endStr = END_STR;
		}
		if (null == septerStr) {
			septerStr = SEPTER_STR;
		}

		StringBuilder sb = new StringBuilder();
		List<String> cols = csv.getColumns();
		if (cols != null && cols.size() > 0) {
			for (String _c : cols) {
				_append(sb, _c, startStr, endStr, septerStr, escape);
			}
			trimEndSepterStr(sb, septerStr);
			sb.append("\n");
		}
		List<List<String>> datas = csv.getDatas();
		if (datas != null && datas.size() > 0) {
			for (List<String> data : datas) {
				if (data != null && data.size() > 0) {
					for (String _c : data) {
						_append(sb, _c, startStr, endStr, septerStr, escape);
					}
					trimEndSepterStr(sb, septerStr);
					sb.append("\n");
				}
			}
		}
		// 去除多余换行符
		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb;
	}

	/**
	 * 删除掉最后的 结束分割字符串
	 * 
	 * @param sb
	 * @param septerStr
	 */
	private static void trimEndSepterStr(StringBuilder sb, String septerStr) {
		if (sb.length() > 0) {
			int len = sb.length();
			sb.delete(len - septerStr.length(), len);
		}
	}

	private static void _append(StringBuilder sb, String in, String startStr, String endStr, String septerStr,
			boolean escape) {
		sb.append(startStr).append((escape ? _escape(in) : in)).append(endStr).append(septerStr);
	}

	/**
	 * 转义字符串 对 "转义为""
	 * 
	 * @param in
	 * @return
	 */
	private static String _escape(String in) {
		if (StringUtil.isNullOrEmpty(in)) {
			return in;
		}
		return in.replace("\"", "\"\"");
	}

}
