package com.zltel.common.utils.excel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.zltel.common.utils.string.StringUtil;

/**
 * Excel 输出工具
 * 
 * <pre>
 * HSSFWorkbook workbook = WriteExcelUtil.createWorkbook();
 * HSSFSheet sheet = WriteExcelUtil.createSheet(workbook, &quot;mySheet&quot;);
 * String[] titles = { &quot;测试行1&quot;, &quot;测试行2&quot;, &quot;测试行3&quot; };
 * List&lt;Map&lt;String, String&gt;&gt; data = new ArrayList&lt;Map&lt;String, String&gt;&gt;();
 * for (int i = 0; i &lt; 1000; i++) {
 * 	Map&lt;String, String&gt; d = new HashMap&lt;String, String&gt;();
 * 	for (String t : titles) {
 * 		d.put(t, &quot;&quot; + System.currentTimeMillis());
 * 	}
 * 	data.add(d);
 * }
 * 
 * WriteExcelUtil.writeSheet(sheet, titles, data);
 * 
 * WriteExcelUtil.writeToFile(workbook, &quot;d:/test.xls&quot;);
 * </pre>
 * 
 * @author Sharped
 * @deprecated 只支持2003版本,2007不支持,建议使用 {@link WriteExcelSupport}
 * @see WriteExcelSupport
 */
public class WriteExcelUtil {

	/**
	 * 创建 表格 空间
	 * 
	 * @return
	 */
	public static HSSFWorkbook createWorkbook() {
		HSSFWorkbook wb = new HSSFWorkbook();
		return wb;
	}

	/**
	 * 创建 sheet
	 * 
	 * @param work
	 * @param name
	 * @return
	 */
	public static HSSFSheet createSheet(HSSFWorkbook work, String name) {
		HSSFSheet sheet = work.createSheet(name);
		return sheet;
	}

	/**
	 * 将excel 保存到 硬盘上
	 * 
	 * @param workbook
	 * @param path
	 */
	public static void writeToFile(HSSFWorkbook workbook, String path) {
		File file = new File(path);
		FileOutputStream out = null;
		try {
			if (!file.exists()) {
				if (!file.getParentFile().exists()) {
					file.getParentFile().mkdir();
				}
			}
			file.createNewFile();
			out = new FileOutputStream(file);
			workbook.write(out);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != out) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 保存 excel 到 输出流
	 * 
	 * 
	 * @param workbook
	 * @return
	 * @throws IOException
	 */
	public static InputStream writeToStream(HSSFWorkbook workbook) throws IOException {
		ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
		workbook.write(arrayOutputStream);
		ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(arrayOutputStream.toByteArray());
		return arrayInputStream;
	}

	/**
	 * 给单元格 添加注释
	 */
	private static void setCellComment(HSSFPatriarch patr, HSSFCell cell, String commentTxt, String author) {
		// 定义注释的大小和位置,详见文档
		// new HSSFClientAnchor(int dx1,int dy1,int dx2, int dy2, short col1,
		// int row1, short col2,int row2);
		// dx1 第一个单元内x坐标，col1 第一个单元格的列
		// col1 图片的左上角放在第几个列cell
		HSSFComment comment = patr.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 8, 4, (short) 10, 10));
		// 设置注释内容
		comment.setString(new HSSFRichTextString(commentTxt));
		// 设置注释作者. 当鼠标移动到单元格上是可以在状态栏中看到该内容.
		comment.setAuthor(author);
		cell.setCellComment(comment);
	}

	/**
	 * 向指定 sheet保存数据
	 * 
	 * @param sheet
	 * @param titles
	 *            第一列头部
	 * @param data
	 *            第一列头部对应的数据 即 列1->列1值，列2->列2值
	 */
	public static void writeSheet(HSSFSheet sheet, String[] titles, List<Map<String, String>> data) {

		HSSFRow row = null;
		HSSFCell cell = null;
		String value = null;

		Map<String, String> currentMap = null;
		// 处理当没有表头时
		if (titles == null || titles.length == 0) {
			if (data.size() > 0) {
				Map<String, String> temp = data.get(0);
				titles = new String[temp.size()];
				temp.keySet().toArray(titles);
			}
		}

		for (int i = -1; i < data.size(); i++) {
			if (i == -1) {
				row = sheet.createRow(0);
			} else {
				currentMap = data.get(i);
				row = sheet.createRow(i + 1);
			}

			for (int j = 0; j < titles.length; j++) {
				if (i == -1) {
					value = titles[j];
				} else {
					value = currentMap.get(titles[j]);
				}
				cell = row.createCell((short) j, HSSFCell.CELL_TYPE_STRING);
				if (StringUtil.isNullOrEmpty(value)) {
					value = "";
				} else {
					sheet.setColumnWidth((short) j, (short) (value.getBytes().length * 500));
				}
				cell.setCellValue(new HSSFRichTextString(value));
			}

		}

	}

	/**
	 * 
	 * @param sheet
	 * @param titles
	 * @param rowNum
	 *            从第几行开始创建
	 * @param data
	 * @param count
	 *            用来判断否需要创建第一行,count值只能为0和-1,为0时不创建第一行 为-1时表示要要创建第一行
	 */
	public static void writeSheet(HSSFWorkbook wb, HSSFSheet sheet, String[] titles, int count, int rowNum,
			List<Map<String, String>> data) {
		boolean isNumOne = false;
		HSSFRow row = null;
		HSSFCell cell = null;
		String value = null;

		Map<String, String> currentMap = null;
		for (int i = count; i < data.size(); i++) {
			if (i == -1) {
				row = sheet.createRow(rowNum);
			} else {
				currentMap = data.get(i);
				row = sheet.createRow(i + rowNum + 1);
			}
			for (int j = 0; j < titles.length; j++) {
				if (i == -1) {
					value = titles[j];
				} else {
					if (!StringUtil.isNullOrEmpty(titles[j])) {
						value = currentMap.get(titles[j]).toString();
					}
				}
				cell = row.createCell((short) j, HSSFCell.CELL_TYPE_STRING);
				// sheet.autoSizeColumn((short)j);
				if (StringUtil.isNullOrEmpty(value)) {
					value = "";
				} else {
					sheet.setColumnWidth((short) j, (short) ((50 * 8) / ((double) 1 / 20)));
				}
				// AlarmUtil.setCellFont(wb, cell, isNumOne);
				cell.setCellValue(new HSSFRichTextString(value));
			}

		}

	}

	/**
	 * 向 sheet 输出图片
	 * 
	 * @param sheet
	 * @param wb
	 * @param patriarch
	 * @param image
	 * @param dx1
	 * @param dy1
	 * @param dx2
	 * @param dy2
	 * @param col1
	 *            从第几列开始
	 * @param row1
	 *            从 第几行开始
	 * @param col2
	 *            持续 列数
	 * @param row2
	 *            持续行数
	 */
	public static void writeImage(HSSFSheet sheet, HSSFWorkbook wb, HSSFPatriarch patriarch, byte[] image, int dx1,
			int dy1, int dx2, int dy2, short col1, int row1, short col2, int row2) {
		//
		HSSFClientAnchor anchor = new HSSFClientAnchor(dx1, dy1, dx2, dy2, col1, row1, col2, row2);
		anchor.setAnchorType(3);
		patriarch.createPicture(anchor, wb.addPicture(image, HSSFWorkbook.PICTURE_TYPE_JPEG));
	}

	public static void writeImage(HSSFSheet sheet, HSSFWorkbook wb, byte[] image, int dx1, int dy1, int dx2, int dy2,
			short col1, int row1, short col2, int row2) {
		HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
		writeImage(sheet, wb, patriarch, image, dx1, dy1, dx2, dy2, col1, row1, col2, row2);
	}

	public static List<Map<String, String>> convertData(List<Map<String, Object>> data) {
		List<Map<String, String>> ret = new ArrayList<Map<String, String>>();

		Map<String, String> m = null;
		for (Map<String, Object> d : data) {
			m = new HashMap<String, String>();
			for (Map.Entry<String, Object> entry : d.entrySet()) {
				m.put(entry.getKey(), entry.getValue() == null ? "" : entry.getValue().toString());
			}
			ret.add(m);
		}

		return ret;
	}

	public static void main(String[] args) {

	}
}
