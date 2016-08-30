package com.zltel.common.utils.mybatis;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyBatisScriptCreateUtilTest {
	public static final Logger logout = LoggerFactory.getLogger(MyBatisScriptCreateUtilTest.class);

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testCreateSelectScript() {
		logout.info("测试 select 脚本");
		StringBuffer sb = MyBatisScriptCreateUtil.createSelectScript(TestBean.class);
		logout.info("\n" + sb.toString());
	}

	@Test
	public final void testCreateUpdateScript() {
		logout.info("测试 update 脚本");
		StringBuffer sb = MyBatisScriptCreateUtil.createUpdateScript(TestBean.class);
		logout.info("\n" + sb.toString());
	}

	@Test
	public final void testCreateDeleteScript() {
		logout.info("测试 delete 脚本");
		StringBuffer sb = MyBatisScriptCreateUtil.createDeleteScript(TestBean.class);
		logout.info("\n" + sb.toString());
	}

	@Test
	public final void testCreateInsertScript() {
		logout.info("测试 insert 脚本");
		StringBuffer sb = MyBatisScriptCreateUtil.createInsertScript(TestBean.class);
		logout.info("\n" + sb.toString());
	}

	@Test
	public final void testCreateInterface() {
		logout.info("测试 接口 脚本");
		StringBuffer sb = MyBatisScriptCreateUtil.createInterface(TestBean.class);
		logout.info("\n" + sb.toString());
	}

}
