package com.zltel.common.utils.excel;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.zltel.common.utils.string.StringUtil;

/**
 * excel 读取工具类
 * 
 * @author Wangch *
 * @deprecated 只支持2003版本,2007不支持,建议使用 {@link ReadExcelSupport}
 * @see ReadExcelSupport
 */
public class ReadExcelUtil {
	private static final Log logger = LogFactory.getLog(ReadExcelUtil.class);

	/**
	 * 读取 Excel 成MAP
	 * 
	 * @param in
	 *            文件所在读取流
	 * 
	 * @example 示例
	 * 
	 *          <pre>
	 *          String basePath = &quot;C:/Users/Sharped/Desktop/2014.6.21_HP服务器告警标准化列表 .xls&quot;;
	 *          Map&lt;String, List&lt;Map&lt;String, String&gt;&gt;&gt; ms = readExcelToMap(new FileInputStream(basePath));
	 *          </pre>
	 * 
	 * @return key为sheet名称，value为sheet数据一行一个Map，map的key为所在列的第一行属性
	 * @throws IOException
	 */
	public static Map<String, List<Map<String, String>>> readExcelToMap(InputStream in) throws IOException {
		return readExcelToMap(in, 0, 1);
	}

	/**
	 * 读取 Excel成Map
	 * 
	 * @param in
	 * @param titleLineNum
	 *            读取Map key值得所在行，如果 titleLineNum<0, 则key将会使用所在位置的cell索引坐标值
	 * @param contentStartLineNum
	 *            读取正文内容开始的行数
	 * @param readSheetNames
	 *            要读取的表格名称，即传入需要读取的sheet名称，如果不传入值，则读取所有表
	 * @return
	 * @throws IOException
	 */
	public static Map<String, List<Map<String, String>>> readExcelToMap(InputStream in, int titleLineNum,
			int contentStartLineNum, String... readSheetNames) throws IOException {
		Map<String, List<Map<String, String>>> ret = new HashMap<String, List<Map<String, String>>>();
		try {
			HSSFWorkbook workbook = new HSSFWorkbook(in);

			for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
				HSSFSheet stemp = workbook.getSheetAt(i);
				String tableName = workbook.getSheetName(i);
				if (isSheetNeedRead(tableName, readSheetNames)) {
					ret.put(tableName, readSheetToMap(stemp, tableName, titleLineNum, contentStartLineNum));
				}
			}
		} finally {
			in.close();
		}

		return ret;
	}

	/**
	 * sheet是否需要被读取
	 * 
	 * @param sn
	 *            将要被读取的名称
	 * @param sns
	 *            定义的需要被读取的名称
	 * @return 是否需要读取
	 */
	private static boolean isSheetNeedRead(String sn, String... sns) {
		boolean ret = false;
		if (sns.length == 0) {
			ret = true;
		} else {
			for (String s : sns) {
				if (StringUtil.isNotNullAndEmpty(sn, s)) {
					if (sn.trim().equals(s.trim())) {
						ret = true;
						break;
					}
				}
			}
		}
		return ret;
	}

	/**
	 * 读取Sheet 成 List
	 * 
	 * @param sheet
	 * @param table
	 * @param titleLineNum
	 *            读取Map key值得所在行，如果 titleLineNum<0, 则key将会使用所在位置的cell索引坐标值
	 * @param contentStartLineNum
	 *            读取正文内容开始的行数
	 * @return
	 */
	public static List<Map<String, String>> readSheetToMap(HSSFSheet sheet, String table, int titleLineNum,
			int contentStartLineNum) {
		List<Map<String, String>> ret = new ArrayList<Map<String, String>>();

		HSSFRow rows = null;
		String[] titles = null;
		boolean end = false;
		long time = System.currentTimeMillis();

		// 读取表头信息
		if (titleLineNum < 0) {
			// rows = sheet.getRow(0);
		} else {
			rows = sheet.getRow(titleLineNum);
			titles = new String[rows.getLastCellNum()];
			for (int j = 0; j < rows.getLastCellNum(); j++) {
				HSSFCell cell = rows.getCell((short) j);
				if (cell == null) {
					continue;
				}
				String title = cell.toString().trim();
				titles[j] = title;
			}
		}
		int _LAST_ROW_NUM = sheet.getLastRowNum();
		if (contentStartLineNum < 0) {
			contentStartLineNum = 0;
		}
		if (contentStartLineNum > _LAST_ROW_NUM) {
			return ret;
		}

		for (int i = contentStartLineNum; !end && i <= _LAST_ROW_NUM; i++) {
			rows = sheet.getRow(i);
			if (rows == null) {
				continue;
			}
			if ((System.currentTimeMillis() - time) > 5000) {
				logger.info(table + " -->读取Sheet数据，完成：" + (i * 100 / sheet.getLastRowNum()) + "," + i + "/"
						+ sheet.getLastRowNum());
				time = System.currentTimeMillis();
			}
			ret.add(readRowToMap(titles, rows));
		}

		return ret;

	}

	/**
	 * 读取 ROW 数据成Map
	 * 
	 * @param titles
	 * @param row
	 * @return
	 */
	public static Map<String, String> readRowToMap(String[] titles, HSSFRow row) {
		Map<String, String> ret = new LinkedHashMap<String, String>();

		HSSFCell cell = null;
		for (int i = 0; i < row.getLastCellNum(); i++) {
			cell = row.getCell((short) i);
			if (cell == null) {
				continue;
			}

			String value = readCell(cell);

			ret.put(analyseKeyName(titles, i), value);
		}

		return ret;
	}

	private static String analyseKeyName(String[] titles, int index) {
		if (titles == null || titles.length <= index) {
			return String.valueOf(index);
		} else {
			if (StringUtil.isNullOrEmpty(titles[index])) {
				return String.valueOf(index);
			} else {
				return titles[index];
			}
		}
	}

	public static String readCell(HSSFCell cell) {
		String ret = "";
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_NUMERIC: // 数字
			if (HSSFDateUtil.isCellDateFormatted(cell)) {
				double d = cell.getNumericCellValue();
				Date date = HSSFDateUtil.getJavaDate(d);
				SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				ret = dformat.format(date);
			} else {
				NumberFormat nf = NumberFormat.getInstance();
				nf.setGroupingUsed(false);// true时的格式：1,234,567,890
				ret = nf.format(cell.getNumericCellValue());// 数值类型的数据为double，所以需要转换一下
			}
			break;
		case HSSFCell.CELL_TYPE_STRING: // 字符串

			ret = cell.toString();// System.out.print(cell.toString());
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
			ret = cell.getBooleanCellValue() + "";
			break;
		case HSSFCell.CELL_TYPE_FORMULA: // 公式
			ret = cell.getCellFormula() + "";
			break;
		case HSSFCell.CELL_TYPE_BLANK: // 空值
			ret = "";
			break;
		case HSSFCell.CELL_TYPE_ERROR: // 故障

			break;
		default:
			break;
		}

		return ret;
	}

	/**
	 * 读取 列数据
	 * 
	 * @param row
	 * @return
	 */
	public static List<String> readRow(HSSFRow rows) {
		List<String> list = new ArrayList<String>();
		for (int j = 0; j < rows.getLastCellNum(); j++) {
			HSSFCell cell = rows.getCell((short) j);
			if (cell == null) {
				continue;
			}
			list.add(cell.toString());
		}
		return list;
	}

	public static void main(String[] args0) throws IOException {
		String basePath = "d:/test.xls";
		Map<String, List<Map<String, String>>> ms = readExcelToMap(new FileInputStream(basePath));

		System.out.println(ms);
	}
}
