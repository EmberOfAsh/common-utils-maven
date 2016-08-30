package com.zltel.common.utils.date;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DateUtilTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testDateSplit() throws Exception {
		java.util.Date _sdt = DateUtil.sdf.parse("2016-04-01 00:00:00");
		java.util.Date _edt = DateUtil.sdf_day.parse("2016-04-10 00:00:00");
		Date sdt = new Date(_sdt.getTime());
		Date edt = new Date(_edt.getTime());

		List<Date> dts = DateUtil.dateSplit(sdt, edt, DateUtil.RANGE_DAY);
		for (Date dt : dts) {
			System.out.println(dt.toDateTime());
		}

		List<java.util.Date> _dts = DateUtil.dateSplit(_sdt, _edt, DateUtil.RANGE_DAY);
		for (java.util.Date _dt : _dts) {
			System.out.println(_dt);
		}
	}

}
