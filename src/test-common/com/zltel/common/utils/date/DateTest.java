package com.zltel.common.utils.date;

import org.junit.Test;

public class DateTest {

	@Test
	public void test() {
		Date dt = new Date();
	}

	@Test
	public void testConstruct() {
		Date dt1 = new Date();
		System.out.println("默认构造" + dt1.toDate());
		Date dt2 = new Date(System.currentTimeMillis());
		System.out.println("有参数构造" + dt2.toDate());

	}

}
