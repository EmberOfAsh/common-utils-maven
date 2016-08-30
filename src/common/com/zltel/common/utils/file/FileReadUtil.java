package com.zltel.common.utils.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import com.zltel.common.utils.io.StreamUtil;

/**
 * 文件读取工具类
 * 
 * @author Wangch
 * 
 */
public class FileReadUtil {
	/** 字符集 gb2312 **/
	public static final String CHARSET_GB2312 = "GB2312";
	/** 字符集utf-8 **/
	public static final String CHARSET_UTF8 = "UTF-8";

	/**
	 * 获取文件读入流
	 * 
	 * @param _f
	 * @param charset
	 * @return
	 * @throws IOException
	 */
	public static BufferedReader getFileReader(File _f, String charset) throws IOException {
		FileInputStream f = null;
		BufferedReader buffer = null;
		InputStreamReader isr = null;

		f = new FileInputStream(_f);
		isr = new InputStreamReader(f, charset);
		buffer = new BufferedReader(isr);

		return buffer;
	}

	/**
	 * 读取 文件内容到StringBuffer
	 * 
	 * @param _f
	 * @param charset
	 * @return
	 * @throws IOException
	 */
	public static StringBuffer readFileContent(File _f, String charset) throws IOException {
		BufferedReader buffer = null;
		StringBuffer sb = new StringBuffer();
		String s = null;
		try {
			buffer = getFileReader(_f, charset);
			while ((s = buffer.readLine()) != null) {
				sb.append(s).append("\n");
			}
		} finally {
			StreamUtil.close(buffer);
		}
		return sb;

	}

	/**
	 * 按行读取文件，并回调处理
	 * 
	 * @param in
	 *            读取流
	 * @param handler
	 *            处理函数
	 * @throws IOException
	 */
	public static void readFileAndHandle(BufferedReader in, FileReadHandle handler) throws IOException {
		String s = null;
		while ((s = in.readLine()) != null) {
			handler.handleLine(s);
		}
	}

	public static void main(String[] args) throws IOException {
		StringBuffer sb = readFileContent(new File("d:/testout/out.log"), FileReadUtil.CHARSET_UTF8);
		System.out.println(sb);
	}
}
