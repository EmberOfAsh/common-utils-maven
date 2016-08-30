package com.zltel.common.utils.mybatis;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyBatisScriptMainTest {
	public static final Logger logout = LoggerFactory.getLogger(MyBatisScriptMainTest.class);

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testCreateCRUDScript() {
		StringBuffer sb = MyBatisScriptMain.createCRUDScript(TestBean.class);
		logout.info("\n" + sb.toString());
	}

	@Test
	public final void testCreateInterface() {
		StringBuffer sb = MyBatisScriptMain.createInterface(TestBean.class);
		logout.info("\n" + sb.toString());
	}

	@Test
	public final void testCreateInterfaceImpl() {
		StringBuffer sb = MyBatisScriptMain.createInterfaceImpl(TestBean.class);
		logout.info("\n" + sb.toString());
	}

}
