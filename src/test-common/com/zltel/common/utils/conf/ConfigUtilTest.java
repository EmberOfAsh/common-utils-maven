package com.zltel.common.utils.conf;

import org.junit.Before;
import org.junit.Test;

public class ConfigUtilTest {

	@Before
	public void setUp() throws Exception {
		ConfigUtil.resolveConfigProFile("elasticsearch.properties");
	}

	@Test
	public void testGetConfValue() {

	}

}
