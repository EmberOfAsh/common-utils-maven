package com.zltel.common.utils.io;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.zltel.common.utils.io.IOUtil.LineHandler;

public class IOUtilTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testReadBytes() throws IOException {
		String str = "this is a test msg";
		ByteArrayInputStream bi = new ByteArrayInputStream(str.getBytes());
		byte[] bts = IOUtil.readBytes(bi);

		System.out.println(new String(bts));
	}

	@Test
	public final void testreadByLine() throws IOException {
		String str = "this\nis\na\ntest\nmsg";
		ByteArrayInputStream bi = new ByteArrayInputStream(str.getBytes());
		IOUtil.LineHandler lh = new LineHandler() {

			public void handleLine(String line) {
				System.out.println("处理行:" + line);
			}
		};
		IOUtil.readByLine(bi, lh);
	}

}
