package com.zltel.common.utils.excel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class WriteExcelSupportTest {
	List<List<String>> datas = null;

	@Before
	public void setUp() throws Exception {
		datas = new ArrayList<List<String>>();
		for (int i = 0; i < 10; i++) {
			datas.add(Arrays.asList("a", "b", "c", "d"));
		}
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCreate2003() throws IOException {
		Workbook workbook = WriteExcelSupport.createWith2003();
		Sheet sheet = workbook.createSheet();
		WriteExcelSupport.writeSheet(sheet, datas);
		// WriteExcelSupport.writeToFile(workbook, "d:/2003.xls");
		// InputStream in = WriteExcelSupport.writeToStream(workbook);
		// OutputStream out = new FileOutputStream("d:/2003.xls");
		// IOUtil.readAndWrite(in, out);
	}

	@Test
	public void testCreate2007() throws IOException {
		Workbook workbook = WriteExcelSupport.createWith2007();
		Sheet sheet = workbook.createSheet();
		WriteExcelSupport.writeSheet(sheet, datas);
		// WriteExcelSupport.writeToFile(workbook, "d:/2007.xlsx");
		// InputStream in = WriteExcelSupport.writeToStream(workbook);
		// OutputStream out = new FileOutputStream("d:/2007.xlsx");
		// IOUtil.readAndWrite(in, out);
	}

}
