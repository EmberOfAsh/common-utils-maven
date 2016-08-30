package com.zltel.common.utils.io;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * IO 工具类
 * 
 * @warn 此工具类中所有流 都未主动关闭，请在外部关闭!
 * @author Wangch
 * @history 2016.8.20 增加 readAndWrite
 */
public class IOUtil {

	public static final String UTF_8 = "utf-8";

	/**
	 * 读取输入流,写入输出流
	 * 
	 * @param in
	 * @param out
	 * @throws IOException
	 */
	public static void readAndWrite(InputStream in, OutputStream out) throws IOException {
		byte[] buf = new byte[1024];
		int len = 0;
		while ((len = in.read(buf)) != -1) {
			out.write(buf, 0, len);
		}
	}

	/**
	 * 读取 输入流 为byte[]
	 * 
	 * @param in
	 *            输入流
	 * @return byte[]数组
	 * @throws IOException
	 */
	public static byte[] readBytes(InputStream in) throws IOException {
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		int len = 0;
		byte[] buf = new byte[1024];
		while ((len = in.read(buf)) != -1) {
			bo.write(buf, 0, len);
		}
		return bo.toByteArray();
	}

	/**
	 * 读取输入流 为 String
	 * 
	 * @param in
	 *            输入流
	 * @return String
	 * @throws IOException
	 */
	public static String readString(InputStream in) throws IOException {
		return new String(readBytes(in));
	}

	/**
	 * 按行读取 并直接处理
	 * 
	 * @param in
	 *            输入流
	 * @param charset
	 *            编码方式
	 * @param lineHandler
	 *            行处理接口
	 * @throws IOException
	 */
	public static void readByLine(InputStream in, String charset, LineHandler lineHandler) throws IOException {
		BufferedReader buffer = null;
		InputStreamReader isr = null;
		try {
			isr = new InputStreamReader(in, charset);
			buffer = new BufferedReader(isr);
			String line = null;
			while ((line = buffer.readLine()) != null) {
				lineHandler.handleLine(line);
			}
		} finally {
			// StreamUtil.close(buffer);
		}
	}

	/**
	 * 按行读取 并直接处理
	 * 
	 * @param in
	 *            输入流
	 * @param lineHandler
	 *            行处理接口
	 * @throws IOException
	 */
	public static void readByLine(InputStream in, LineHandler lineHandler) throws IOException {
		readByLine(in, UTF_8, lineHandler);
	}

	/**
	 * 单行处理回调 接口
	 * 
	 * @author Wangch
	 *
	 */
	public static interface LineHandler {
		/**
		 * 处理返回的行
		 * 
		 * @param line
		 */
		public void handleLine(String line);
	}

}
