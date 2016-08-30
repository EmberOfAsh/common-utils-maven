package com.zltel.common.utils.reflect;

import java.lang.reflect.Method;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReflectUtilTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testGetMethodByName() throws Exception {
		String methodname = "setAge";
		Method method = ReflectUtil.getMethodByName(methodname, true, TestBean.class);
		Assert.assertNotNull(method);
		System.out.println(method);
	}

	@Test
	public final void testGetFieldGetterMethod() {
		String field = "name";
		Method method = ReflectUtil.getFieldGetterMethod(field, TestBean.class);
		Assert.assertNotNull(method);
		System.out.println(method);
	}

	@Test
	public final void testGetFieldSetterMethod() {
		String field = "name";
		Method method = ReflectUtil.getFieldSetterMethod(field, TestBean.class);
		Assert.assertNotNull(method);
		System.out.println(method);
	}

	public static class TestBean {
		private String name;
		private String age;

		/**
		 * @return the name
		 */
		public final String getName() {
			return name;
		}

		/**
		 * @return the age
		 */
		public final String getAge() {
			return age;
		}

		/**
		 * @param name
		 *            the name to set
		 */
		public final void setName(String name) {
			this.name = name;
		}

		/**
		 * @param age
		 *            the age to set
		 */
		public final void setAge(String age) {
			this.age = age;
		}

	}
}
