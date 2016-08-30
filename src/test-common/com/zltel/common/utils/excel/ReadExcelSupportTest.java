package com.zltel.common.utils.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ReadExcelSupportTest {
	private static final Log logout = LogFactory.getLog(ReadExcelSupportTest.class);

	private String excel_csv = "file/csv.csv";
	private String excel_2003 = "file/2003excel.xls";
	private String excel_2007 = "file/2007excel.xlsx";

	private InputStream in_csv;
	private InputStream in_2003;
	private InputStream in_2007;

	private static String CLASSPATH = ReadExcelSupportTest.class.getClassLoader().getResource("").getPath();

	@Before
	public void setUp() throws Exception {

		// in_csv =
		// ReadExcelSupportTest.class.getClassLoader().getResourceAsStream(excel_csv);
		// in_2003 =
		// ReadExcelSupportTest.class.getClassLoader().getResourceAsStream(excel_2003);
		// in_2007 =
		// ReadExcelSupportTest.class.getClassLoader().getResourceAsStream(excel_2007);
	}

	private InputStream getIn_csv() throws FileNotFoundException {
		return new FileInputStream(CLASSPATH + File.separator + excel_csv);
	}

	private InputStream getIn_2003() throws FileNotFoundException {
		return new FileInputStream(CLASSPATH + File.separator + excel_2003);
	}

	private InputStream getIn_2007() throws FileNotFoundException {
		return new FileInputStream(CLASSPATH + File.separator + excel_2007);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testReadWith() throws IOException {
		logout.info("测试读取 excel 2003");
		ReadExcelSupport.readWith(getIn_2003(), ReadExcelSupport.FILE_TYPE_XLS);
		logout.info("测试读取 excel 2007");
		ReadExcelSupport.readWith(getIn_2007(), ReadExcelSupport.FILE_TYPE_XLSX);

		logout.info("测试使用2007 读取 2003");
		try {
			ReadExcelSupport.readWith(getIn_2003(), ReadExcelSupport.FILE_TYPE_XLSX);
		} catch (Exception e) {
			logout.error(e.getMessage());
		}
		logout.info("测试使用2003 读取 2007");
		try {
			ReadExcelSupport.readWith(getIn_2007(), ReadExcelSupport.FILE_TYPE_XLS);
		} catch (Exception e) {
			logout.error(e.getMessage());
		}
	}

	@Test
	public final void testRead2003() throws IOException {
		logout.info("测试读取2003 excel");
		Workbook workbook = ReadExcelSupport.readWith2003(getIn_2003());
		List<List<List<String>>> datas = ReadExcelSupport.readExcelToList(workbook);
		logout.info(datas);
		logout.info("测试读取2003 excel  完成");

	}

	@Test
	public final void testRead2007() throws IOException {
		logout.info("测试读取2007 excel");
		Workbook workbook = ReadExcelSupport.readWith2007(getIn_2007());
		List<List<List<String>>> datas = ReadExcelSupport.readExcelToList(workbook);
	}
}
