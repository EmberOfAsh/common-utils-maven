package com.zltel.common.utils.log.impl;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.zltel.common.utils.log.Log;
import com.zltel.common.utils.log.LogFactory;

public class ComFileLogOut implements Log {

	private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private final static SimpleDateFormat sdf_date = new SimpleDateFormat("yyyy-MM-dd");

	private String logPath;
	private String logName;

	private int currentLevel = Log.INFO;
	private boolean LOCALDebug = false;

	public ComFileLogOut(String logPath, String logName) {
		this.logPath = logPath;
		this.logName = logName;
		this.setCurrentLevel(LogFactory.currentLevel);
	}

	public void debug(String msg) {
		if (DEBUG >= currentLevel) {

			String m = getBaseStr() + " DEBUG   " + msg;
			if (LOCALDebug) {
				System.out.println(m);
			} else {
				try {
					writeFile(logPath, m);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}

	public String getLogFileName() {

		return sdf_date.format(new Date()) + "_" + this.logName;
	}

	public void info(String msg) {
		if (INFO >= currentLevel) {
			String m = getBaseStr() + " INFO   " + msg;
			if (LOCALDebug) {
				System.out.println(m);
			} else {
				try {
					writeFile(logPath, m);
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
					writeFile(logPath, m);
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
					writeFile(logPath, m);
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
					writeFile(logPath, m);
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

	private synchronized void writeFile(String path, String msg) throws Exception {
		File f = new File(path);
		if (!f.exists()) {
			f.mkdirs();
		}
		File file = new File(path + getLogFileName());
		BufferedWriter out = null;
		try {
			if (!file.exists()) {
				file.createNewFile();
				out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
			} else {
				out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));
			}
			out.write(msg);
			out.write("\r\n");

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			out.close();
		}
	}

	private static String getBaseStr() {
		StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
		StackTraceElement s = stacks[3];
		return sdf.format(new Date()) + " (" + s.getFileName() + ":" + s.getLineNumber() + ") ";
	}



	/**
	 * 获取currentLevel
	 * 
	 * @return currentLevel currentLevel
	 */
	public final int getCurrentLevel() {
		return currentLevel;
	}

	/**
	 * 设置currentLevel
	 * 
	 * @param currentLevel
	 *            currentLevel
	 */
	public final void setCurrentLevel(int currentLevel) {
		this.currentLevel = currentLevel;
	}

	public static void main(String[] args) {
		Log logout = LogFactory.getLog("d:/log_1/", "TestLog.txt");
		logout.error("123");
	}


}
