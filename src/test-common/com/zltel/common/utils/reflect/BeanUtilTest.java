package com.zltel.common.utils.reflect;

import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BeanUtilTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testDefaultFieldName() {
		String mn = "get_b_abc";
		String fn = BeanUtil.defaultFieldName(mn);
		System.out.println(mn + "  ->  " + fn);
	}

	@Test
	public final void testConvertBeanToMap() {
		TestClass tc = new TestClass("ABC", "DEF");
		Map m1 = BeanUtil.ConvertBeanToMap(tc);
		Map m2 = BeanUtil.ConvertBeanToMap(tc, false);

		System.out.println("Object 装 Map keyupper = true " + m1);
		System.out.println("Object 装 Map keyupper = false " + m2);
	}

	public static class TestClass {
		public String a;
		public String b;

		/**
		 * @return the a
		 */
		public final String getA() {
			return a;
		}

		/**
		 * @return the b
		 */
		public final String getB() {
			return b;
		}

		/**
		 * @param a
		 *            the a to set
		 */
		public final void setA(String a) {
			this.a = a;
		}

		/**
		 * @param b
		 *            the b to set
		 */
		public final void setB(String b) {
			this.b = b;
		}

		public TestClass(String a, String b) {
			super();
			this.a = a;
			this.b = b;
		}

	}
}
