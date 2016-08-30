package com.zltel.common.utils.log.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zltel.common.utils.hadoop.HdfsUtil;
import com.zltel.common.utils.log.Log;

@Deprecated
public class HdfsLogOut implements Log {
	private static Logger logout = LoggerFactory.getLogger(HdfsLogOut.class);
	private int currentLevel = Log.INFO;
	private boolean LOCALDebug = false;

	public final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public final static SimpleDateFormat sdf_date = new SimpleDateFormat("yyyy-MM-dd");

	private static final String BASE_HDFS_PATH = "hdfs://dn1:9000/yarn/my-logs/";

	private Configuration config;
	private String jobName;
	/** 日志基础路径 **/
	private String baseLogPath;
	/** 日子路径 **/
	private String logPath;

	public HdfsLogOut(Configuration conf, String jb) throws IOException {
		this.config = conf;
		this.jobName = jb;
		config.setBoolean("dfs.support.append", true);
		logout.info(" config : " + config);

		baseLogPath = this.BASE_HDFS_PATH.concat(this.jobName.concat("/"));

		System.out.println("base log path : " + baseLogPath);
		HdfsUtil.createDir(this.config, this.baseLogPath);

		calcLogPath();
	}

	public void debug(String msg) {
		if (DEBUG >= currentLevel) {

			String m = getBaseStr() + " DEBUG   " + msg;
			if (LOCALDebug) {
				System.out.println(m);
			} else {
				try {
					write(m);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}

	public void info(String msg) {
		if (INFO >= currentLevel) {
			String m = getBaseStr() + " INFO   " + msg;
			if (LOCALDebug) {
				System.out.println(m);
			} else {
				try {
					write(m);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}

	public void notice(String msg) {
		if (NOTICE >= currentLevel) {
			String m = getBaseStr() + " NOTICE  " + msg;
			if (LOCALDebug) {
				System.out.println(m);
			} else {
				try {
					write(m);
				} catch (Exception e) {

					e.printStackTrace();
				}
			}
		}

	}

	public void error(String msg) {
		if (ERROR >= currentLevel) {
			String m = getBaseStr() + " ERROR  " + msg;
			if (LOCALDebug) {
				System.err.println(m);
			} else {
				try {
					write(m);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		//

	}

	public void error(String msg, Exception e) {
		this.error(msg + readException(e));
	}

	public void error(Exception e) {

		this.error(readException(e));
	}

	public void fatal(String msg) {
		if (FATAL >= currentLevel) {
			String m = getBaseStr() + " ERROR  " + msg;
			if (LOCALDebug) {
				System.err.println(m);
			} else {
				try {
					write(m);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void fatal(Exception e) {
		this.fatal(readException(e));
	}

	public void fatal(String msg, Exception e) {
		this.fatal(msg + readException(e));
	}

	public static String readException(Throwable e) {
		if (e == null)
			return null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			e.printStackTrace(new PrintStream(baos));
		} catch (Exception e2) {
			e2.printStackTrace();
		} finally {
			try {
				baos.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return baos.toString();
	}

	/**
	 * 检测路径是否存在,如果不存在则创建
	 * 
	 * @param path
	 * @throws IOException
	 */
	private boolean checkPath(String path) throws IOException {
		Path pout = new Path(path);
		FileSystem fs = FileSystem.get(this.config);
		if (!fs.exists(pout)) {
			fs.create(pout).close();
			return false;
		}
		fs.close();
		return true;
	}

	private void calcLogPath() throws UnknownHostException {
		InetAddress netAddress = InetAddress.getLocalHost();
		String ip = netAddress.getHostAddress();
		String hostname = netAddress.getHostName();
		String fn = hostname.concat("_").concat(ip).concat(".log");
		this.logPath = this.baseLogPath.concat(fn);
		System.out.println(" 创建日志文件目录： " + this.logPath);
	}

	private String getBaseStr() {
		StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
		StackTraceElement s = stacks[3];
		return sdf.format(new Date()) + " (" + s.getFileName() + ":" + s.getLineNumber() + ") ";
	}

	private void write(String msg) throws IllegalArgumentException, IOException {
		FileSystem fs = null;
		OutputStream out = null;
		try {
			if (checkPath(this.logPath)) {
				// 旧文件

			} else {
				// 新文件

			}
			fs = FileSystem.get(URI.create(this.logPath), this.config);

			out = fs.append(new Path(logPath));
			out.write(msg.getBytes());
		} finally {
			try {
				if (null != out) {
					out.close();
				}
			} catch (Exception e) {
				// e.printStackTrace();
			}
			try {
				if (null != fs) {
					fs.close();
				}
			} catch (Exception e) {
				// e.printStackTrace();
			}
		}
	}

	/**
	 * @return the config
	 */
	public final Configuration getConfig() {
		return config;
	}

	/**
	 * @return the jobName
	 */
	public final String getJobName() {
		return jobName;
	}

	/**
	 * @param config
	 *            the config to set
	 */
	public final void setConfig(Configuration config) {
		this.config = config;
	}

	/**
	 * @param jobName
	 *            the jobName to set
	 */
	public final void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public static void main(String[] args) throws UnknownHostException {
		InetAddress netAddress = InetAddress.getLocalHost();

		System.out.println(netAddress.getHostName());
		System.out.print(netAddress.getHostAddress());
	}
}
