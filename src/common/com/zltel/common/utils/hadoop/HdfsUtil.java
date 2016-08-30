package com.zltel.common.utils.hadoop;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class HdfsUtil {

	/**
	 * 创建 HDFS 目录
	 * 
	 * @param conf
	 * @param path
	 *            路径
	 * @return
	 * @throws IOException
	 */
	public static boolean createDir(Configuration conf, String path) throws IOException {
		FileSystem hdfs = FileSystem.get(conf);
		Path dfs = new Path(path);
		hdfs.mkdirs(dfs);
		try {
			hdfs.close();
		} catch (Exception e) {
		}
		return true;
	}

	/**
	 * 创建 HDFS文件
	 * 
	 * @param conf
	 * @param filepath
	 * @return
	 * @throws IOException
	 */
	public static boolean createFile(Configuration conf, String filepath) throws IOException {
		FileSystem hdfs = FileSystem.get(conf);
		Path dfs = new Path(filepath);
		FSDataOutputStream outputStream = hdfs.create(dfs);
		outputStream.write("\n".getBytes());
		try {
			outputStream.close();
		} catch (Exception e) {
		}
		try {
			hdfs.close();
		} catch (Exception e) {
		}
		return true;
	}

}
