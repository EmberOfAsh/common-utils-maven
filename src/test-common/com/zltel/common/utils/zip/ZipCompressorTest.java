package com.zltel.common.utils.zip;

import org.junit.After;
import org.junit.Before;

public class ZipCompressorTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	public final void testZipCompress() {
		String src = "D:/dataExchange/bak/";
		String out = "d:/zipTest.zip";
		ZipCompressor compressor = new ZipCompressor();
		compressor.zipCompress(src, out);
	}

}
