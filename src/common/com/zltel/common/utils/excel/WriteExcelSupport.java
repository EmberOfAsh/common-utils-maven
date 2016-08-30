package com.zltel.common.utils.excel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.zltel.common.utils.string.StringUtil;

/**
 * 保存Excel工具支持类，支持Excel2003和Excel2007两种文件格式
 * 
 * <pre>
 * <code>
 * //创建 2003 excel文档
 * Workbook workbook = WriteExcelSupport.createWith2003();
 * //创建2007 excel文档
 * Workbook workbook = WriteExcelSupport.createWith2007();
 * 
 * //创建输出的sheet
 * Sheet sheet = workbook.createSheet();
 * List&lt;List&lt;String>> datas = new ArrayList&lt;List&lt;String>>();
 *	for (int i = 0; i < 10; i++) {
 *	datas.add(Arrays.asList("a", "b", "c", "d"));
 * }
 * //将数据保存入sheet
 * WriteExcelSupport.writeSheet(sheet, datas);
 * </code>
 * </pre>
 * 
 * @author Wangch
 *
 */
public class WriteExcelSupport {
	public static final String FILE_TYPE_XLS = "xls";
	public static final String FILE_TYPE_XLSX = "xlsx";

	/**
	 * 指定文件类型创建 excel 文档
	 * 
	 * @param fileType
	 * @return
	 * @throws IOException
	 */
	public static Workbook createWith(String fileType) throws IOException {
		Workbook workbook = null;
		if (StringUtil.isNullOrEmpty(fileType)) {
			fileType = FILE_TYPE_XLSX;
		}
		if (FILE_TYPE_XLSX.equals(fileType.trim().toLowerCase())) {
			workbook = createWith2007();
		} else {
			workbook = createWith2003();
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
	public static Workbook createWith2007() throws IOException {
		XSSFWorkbook workbook = new XSSFWorkbook();
		return workbook;
	}

	/**
	 * 按 2003格式创建 WorkBook
	 * 
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public static Workbook createWith2003() throws IOException {
		HSSFWorkbook workbook = new HSSFWorkbook();
		return workbook;
	}

	/**
	 * 创建一个 Sheet
	 * 
	 * @param workbook
	 * @param sheetname
	 *            sheet名
	 * @return
	 */
	public static Sheet createSheet(Workbook workbook, String sheetname) {
		Sheet sheet = StringUtil.isNotNullAndEmpty(sheetname) ? workbook.createSheet(sheetname)
				: workbook.createSheet();
		return sheet;

	}

	public static void writeSheet(Sheet sheet, List<List<String>> datas) {
		int size = datas.size();
		for (int ridx = 0; ridx < size; ridx++) {
			List<String> data = datas.get(ridx);
			Row row = sheet.createRow(ridx);
			for (int cidx = 0; cidx < data.size(); cidx++) {
				Cell cell = row.createCell(cidx, Cell.CELL_TYPE_STRING);// 默认String
				cell.setCellValue(data.get(cidx));
			}
		}
	}

	/**
	 * 将excel 保存到 硬盘上
	 * 
	 * @param workbook
	 * @param path
	 * @throws IOException
	 */
	public static void writeToFile(Workbook workbook, String path) throws IOException {
		File file = new File(path);
		FileOutputStream out = null;
		try {
			if (!file.exists()) {
				if (!file.getParentFile().exists()) {
					file.getParentFile().mkdirs();
				}
			}
			file.createNewFile();
			out = new FileOutputStream(file);
			workbook.write(out);
		} finally {
			try {
				if (null != out) {
					out.close();
				}
			} catch (IOException e) {
			}
		}
	}

	/**
	 * 保存 excel 到 输入流
	 * 
	 * 
	 * @param workbook
	 * @return
	 * @throws IOException
	 */
	public static InputStream writeToStream(Workbook workbook) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		workbook.write(out);
		ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
		return in;
	}
}
