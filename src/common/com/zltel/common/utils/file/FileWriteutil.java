package com.zltel.common.utils.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import com.zltel.common.utils.charset.CharSet;
import com.zltel.common.utils.io.StreamUtil;

/**
 * 文件 写出 工具类
 * 
 * @author Wangch
 * @history
 *          <ul>
 *          <li>2016.8.18 为保存方法增加编码支持</li>
 *          </ul>
 */
public class FileWriteutil {

	/**
	 * 快速 保存 内容 到指定 位置
	 * 
	 * @param file
	 *            保存文件
	 * 
	 * @param msg
	 *            保存内容
	 * @param append
	 *            是否追加
	 * @param charset
	 *            编码格式
	 * @throws Exception
	 */
	public static void write(File file, String msg, boolean append, String charset) throws IOException {

		BufferedWriter out = null;
		try {
			out = getWriteOutStream(file, append, charset);
			out.write(msg);
			out.write("\r\n");
		} finally {
			try {
				StreamUtil.close(out);
			} catch (Exception e) {
			}
		}
	}

	/**
	 * 快速 保存 内容 到指定 位置
	 * 
	 * @param file
	 * @param msg
	 * @param append
	 * @throws IOException
	 */
	public static void write(File file, String msg, boolean append) throws IOException {
		write(file, msg, append, CharSet.UTF_8);
	}

	/**
	 * 向输出流输出信息
	 * 
	 * @param out
	 *            输出流
	 * @param data
	 *            数据
	 * @throws IOException
	 */
	public static void write(BufferedWriter out, String data) throws IOException {
		out.write(data);
	}

	/**
	 * 获取 文件输出流
	 * 
	 * @param file
	 *            文件
	 * @param append
	 *            是否追加
	 * @param charset
	 *            默认输出格式
	 * @return 文件输出流
	 * @throws IOException
	 * @warn 使用完成后一定要 关闭流！！！
	 */
	public static BufferedWriter getWriteOutStream(File file, boolean append, String charset) throws IOException {
		if (null == charset) {
			charset = CharSet.UTF_8;
		}
		BufferedWriter out = null;
		if (!file.exists()) {
			file.createNewFile();
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), charset));
		} else {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, append), charset));
		}
		return out;
	}

	public static void main(String[] args) throws IOException {
		String fp = "d:/testout/out.log";
		FileWriteutil.write(new File(fp), "this is test msg", true, "gbk");
	}
}
