package com.zltel.common.utils.file;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

import com.zltel.common.utils.io.StreamUtil;

/**
 * 文件工具类
 * 
 * @author Wangch
 * 
 */
public class FileUtil {
	/**
	 * 文件路径 是文件并且存在
	 * 
	 * @param filePath
	 * @return
	 */
	public static boolean isFileAndExists(String filePath) {
		boolean ret = false;
		File _f = new File(filePath);
		if (_f.exists() && _f.isFile()) {
			ret = true;
		}
		return ret;
	}

	/**
	 * 文件夹路径 是否是路径并且存在
	 * 
	 * @param dirPath
	 * @return
	 */
	public static boolean isDirAndExists(String dirPath) {
		boolean ret = false;
		File _f = new File(dirPath);
		if (_f.exists() && _f.isDirectory()) {
			ret = true;
		}
		return ret;
	}

	/**
	 * 删除 文件或文件夹
	 * 
	 * @param f
	 * @return
	 */
	public static final boolean delete(File f) {
		boolean ret = true;
		if (f.isFile()) {
			deleteFile(f);
		} else {
			deleteDir(f);
		}
		return ret;
	}

	/**
	 * 删除文件夹
	 * 
	 * @param f
	 * @return
	 */
	public static final boolean deleteDir(File f) {
		for (File _f : f.listFiles()) {
			if (_f.isFile()) {
				delete(_f);
			} else {
				deleteDir(_f);
			}
		}
		return f.delete();
	}

	/**
	 * 删除 文件
	 * 
	 * @param f
	 *            文件
	 * @return 是否成功
	 */
	public static final boolean deleteFile(File f) {
		return f.delete();
	}

	/**
	 * 获取指定路径下的所有目录
	 * 
	 * @param path
	 * @return
	 */
	public static List<File> getDirs(File path) {
		List<File> fs = new ArrayList<File>();
		if (path.isDirectory()) {
			for (File _f : path.listFiles()) {
				fs.addAll(getDirs(_f));
			}
			fs.add(path);
		}
		return fs;
	}

	/**
	 * 获取 指定路径下的所有文件
	 * 
	 * @param path
	 * @return
	 */
	public static List<File> getFiles(File baseDir) {
		List<File> fs = new ArrayList<File>();
		if (baseDir.isFile()) {
			fs.add(baseDir);
		} else {
			for (File _f : baseDir.listFiles()) {
				fs.addAll(getFiles(_f));
			}
		}
		return fs;
	}

	/**
	 * 快速计算文件大小
	 * 
	 * @param file
	 * @return
	 */
	public static long getFileSize(File file) {
		return file.length();
	}

	public static long getFileLineCount(File file) {
		int lines = 0;
		long fileLength = file.length();
		LineNumberReader rf = null;
		try {
			rf = new LineNumberReader(new FileReader(file));
			if (rf != null) {
				rf.skip(fileLength);
				lines = rf.getLineNumber();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			StreamUtil.close(rf);
		}
		return lines;
	}

	public static void main(String[] args) {
		String path = "E:/eclipse/user_workspace/zjmonitor_DAQ_2013_12_23/webapp";
		delete(new File(path));
	}

}
