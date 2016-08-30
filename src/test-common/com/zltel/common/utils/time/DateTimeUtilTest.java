package com.zltel.common.utils.time;

import static org.junit.Assert.assertNotNull;

import java.text.ParseException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.zltel.common.utils.date.DateUtil;

public class DateTimeUtilTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testGetInstince() {
		assertNotNull(DateTimeUtil.getInstince());
	}

	@Test
	public final void testtoCurrentDateStr() {
		String str = DateTimeUtil.toCurrentDateStr();
		System.out.println("toCurrentDateStr:" + str);
		str = DateTimeUtil.toCurrent24DateTimeStr();
		System.out.println("toCurrent24DateTimeStr:" + str);
		str = DateTimeUtil.toCurrent12DateTimeStr();
		System.out.println("toCurrent12DateTimeStr:" + str);

	}

	@Test
	public final void testInstince() {
		DateTimeUtil dtu = DateTimeUtil.getInstince();
		String str = dtu.to24DateTimeStr();
		System.out.println("to24DateTimeStr:" + str);
		str = dtu.to12DateTimeStr();
		System.out.println("to12DateTimeStr:" + str);
	}

	@Test
	public final void testgetCurrentWeekOfMonth() throws ParseException {
		System.out.println("week of month:" + DateTimeUtil.getCurrentWeekOfMonth());
		DateTimeUtil dtu = DateTimeUtil.getInstince();
		dtu.setTime(DateUtil.sdf_day.parse("2016-04-03"));
		System.out.println("week of month:" + dtu.getWeekOfMonth());
	}
}
