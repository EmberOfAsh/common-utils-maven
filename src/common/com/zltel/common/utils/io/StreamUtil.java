package com.zltel.common.utils.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Stream 流工具类
 * 
 * @author Wangch
 * 
 */
public class StreamUtil {
	/**
	 * 关闭 输入流
	 * 
	 * @param in
	 */
	public static void close(InputStream in) {
		if (in != null) {
			try {
				in.close();
			} catch (IOException e) {
			}
		}
	}

	/**
	 * 关闭输出流
	 * 
	 * @param out
	 */
	public static void close(OutputStream out) {
		if (out != null) {
			try {
				out.close();
			} catch (IOException e) {
			}
		}
	}

	public static void close(BufferedWriter out) {
		if (out != null) {
			try {
				out.close();
			} catch (IOException e) {
			}
		}
	}

	public static void close(BufferedReader out) {
		if (out != null) {
			try {
				out.close();
			} catch (IOException e) {
			}
		}
	}

}
