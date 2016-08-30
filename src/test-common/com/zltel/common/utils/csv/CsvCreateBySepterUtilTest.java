package com.zltel.common.utils.csv;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CsvCreateBySepterUtilTest {
	private static final Log logout = LogFactory.getLog(CsvCreateBySepterUtilTest.class);
	CsvFileBean csvFileBean;

	@Before
	public void setUp() throws Exception {
		List<List<String>> datas = new ArrayList<List<String>>();
		List<String> colums = new ArrayList<String>();
		csvFileBean = new CsvFileBean();
		csvFileBean.setColumns(colums);
		csvFileBean.setDatas(datas);
		for (int i = 0; i < 5; i++) {
			List<String> data = new ArrayList<String>();
			for (int j = 0; j < 5; j++) {
				if (i == 0) {
					colums.add("column" + j);
				} else {
					data.add(randomStr());
				}
			}
			if (data.size() > 0) {
				datas.add(data);
			}
		}
	}

	public String randomStr() {
		StringBuffer sb = new StringBuffer();
		while (sb.length() < 10) {
			int c = (int) (System.nanoTime() % 200);
			if (c > (int) ('A') && c < (int) ('z')) {
				sb.append((char) (c));
			}
		}
		return sb.toString() + "\"\"\"\"\"'";
	}

	@After
	public void tearDown() throws Exception {
		logout.info((int) ('A'));
	}

	@Test
	public final void testCreatCsvFileStr() {
		StringBuilder sb = CsvCreateBySepterUtil.creatCsvFileStr(csvFileBean, "'", "'", ",", true);
		logout.info("1测试CSV文件生成：\n" + sb.toString());
		logout.info("end");
	}

	@Test
	public final void testDefault() {
		StringBuilder sb = CsvCreateBySepterUtil.creatCsvFileStr(csvFileBean);
		logout.info("默认CSV文件生成：\n" + sb.toString());
		logout.info("end");
	}

}
