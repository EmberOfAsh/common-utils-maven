package com.zltel.common.utils.excel;

import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.zltel.common.utils.string.StringUtil;

/**
 * 读取 Excel工具支持类，支持Excel2003和Excel2007两种文件格式
 * 
 * 
 * <h3>第一种方式</h3>
 * 
 * <pre>
 * <code>
 * // 自行 创建 WorkBook实现
 * Workbook workbook = ReadExcelSupport.readWith2007(getIn_2007());
 * // 根据文件类型创建WorkBook实现
 * Workbook workbook = ReadExcelSupport.readWith(getIn_2007(), ReadExcelSupport.FILE_TYPE_XLSX);
 * // 读取workbook内容成list
 * List<List<List<String>>> datas = ReadExcelSupport.readExcelToList(workbook);
 * </code>
 * </pre>
 * 
 * <h3>第二种方式</h3>
 * 
 * <pre>
 * <code>
 * // 直接读取
 * List<List<List<String>>> datas = ReadExcelSupport.readToList(getIn_2007(), ReadExcelSupport.FILE_TYPE_XLSX);
 * </code>
 * </pre>
 * 
 * <h3>注意： 一定要选择正确的读取 Excel的方式，不能使用2007的方式去读取2003的文件，反之也不行。</h3>
 * 
 * @author Wangch
 */
public class ReadExcelSupport {

	public static final String FILE_TYPE_XLS = "xls";
	public static final String FILE_TYPE_XLSX = "xlsx";

	/**
	 * 读取excel成list
	 * 
	 * @param in
	 * @param fileType
	 *            文件类型
	 * @param sheetNames
	 *            要过来的sheet名
	 * @return list
	 * @throws IOException
	 */
	public static List<List<List<String>>> readToList(InputStream in, String fileType, String... sheetNames)
			throws IOException {
		Workbook workbook = readWith(in, fileType);
		return readExcelToList(workbook, sheetNames);
	}

	/**
	 * 创建 WorkBook
	 * 
	 * @param in
	 * @param fileType
	 *            文件类型(xls,xlsx);
	 * @return
	 * @throws IOException
	 */
	public static Workbook readWith(InputStream in, String fileType) throws IOException {
		Workbook workbook = null;
		if (StringUtil.isNullOrEmpty(fileType)) {
			fileType = FILE_TYPE_XLSX;
		}
		if (FILE_TYPE_XLSX.equals(fileType.trim().toLowerCase())) {
			workbook = readWith2007(in);
		} else {
			workbook = readWith2003(in);
		}
		if (workbook == null) {
			throw new IOException("无法读取excel,excel格式有问题?");
		}
		return workbook;
	}

	/**
	 * 按 2007格式创建 WorkBook
	 * 
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public static Workbook readWith2007(InputStream in) throws IOException {
		XSSFWorkbook workbook = new XSSFWorkbook(in);
		return workbook;
	}

	/**
	 * 按 2003格式创建 WorkBook
	 * 
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public static Workbook readWith2003(InputStream in) throws IOException {
		HSSFWorkbook workbook = new HSSFWorkbook(in);
		return workbook;
	}

	/**
	 * 读取 excel为List
	 * 
	 * @param workbook
	 * @param sheetNames
	 *            要读取的 sheet名
	 * @return [所属sheet[行[列单元格,...],...],...]
	 */
	public static List<List<List<String>>> readExcelToList(Workbook workbook, String... sheetNames) {
		List<List<List<String>>> rets = new ArrayList<List<List<String>>>();
		for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
			Sheet sheet = workbook.getSheetAt(i);
			String sn = getSheetName(workbook, i);
			if (isSheetNeedRead(sn, sheetNames)) {
				rets.add(readSheet(sheet));
			}
		}
		return rets;
	}

	/**
	 * 读取 Sheet数据成List
	 * 
	 * @param sheet
	 * @return
	 */
	public static List<List<String>> readSheet(Sheet sheet) {
		List<List<String>> rets = new ArrayList<List<String>>();
		int end = sheet.getLastRowNum();
		for (int ri = 0; ri <= end; ri++) {
			Row row = sheet.getRow(ri);
			List<String> cols = new ArrayList<String>();
			int rcend = row.getLastCellNum();
			for (int ci = 0; ci < rcend; ci++) {
				Cell cell = row.getCell(ci);
				cols.add(readCell(cell));
			}
			rets.add(cols);
		}
		return rets;
	}

	/**
	 * 获取 Sheet所在 WorkBook的索引
	 * 
	 * @param workbook
	 * @param sheetName
	 *            sheet名
	 * @return 索引序列
	 */
	public static int getSheetIndex(Workbook workbook, String sheetName) {
		return workbook.getSheetIndex(sheetName);
	}

	/**
	 * 根据sheet索引号查找sheet名
	 * 
	 * @param workbook
	 * @param sheetIndex
	 *            sheet索引
	 * @return sheet名
	 */
	public static String getSheetName(Workbook workbook, int sheetIndex) {
		return workbook.getSheetName(sheetIndex);
	}

	/**
	 * 包含sheet总数
	 * 
	 * @param workbook
	 * @return
	 */
	public static int getNumberOfSheets(Workbook workbook) {
		return workbook.getNumberOfSheets();

	}

	public static String readCell(Cell cell) {
		String ret = "";
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_NUMERIC: // 数字
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
		case Cell.CELL_TYPE_STRING: // 字符串

			ret = cell.toString();// System.out.print(cell.toString());
			break;
		case Cell.CELL_TYPE_BOOLEAN: // Boolean
			ret = cell.getBooleanCellValue() + "";
			break;
		case Cell.CELL_TYPE_FORMULA: // 公式
			ret = cell.getCellFormula() + "";
			break;
		case Cell.CELL_TYPE_BLANK: // 空值
			ret = "";
			break;
		case Cell.CELL_TYPE_ERROR: // 故障
			break;
		default:
			break;
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
}
