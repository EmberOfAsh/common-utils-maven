package com.zltel.common.utils.string;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class StringUtilTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	public final void testToNumFixIntIntString() {
		int i = 1;
		String r = StringUtil.toNumFix(i, 10);
		// System.out.println("定长： " + r);
	}

	@Test
	public final void testToFix() {
		System.out.println("长度一致时" + StringUtil.toFix(Long.valueOf("460000802503270"), 3));
	}

	@Test
	public final void testReverse() {
		System.out.println(" 反转字符串: " + StringUtil.reverse("abcdefg"));
	}

}
