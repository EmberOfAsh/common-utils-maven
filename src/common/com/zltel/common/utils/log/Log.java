package com.zltel.common.utils.log;

/**
 * 自定义 日志输出接口
 * 
 * @author Wangch
 * 
 */
public interface Log {

	public final static Integer DEBUG = 0;
	public final static Integer INFO = 1;
	public final static Integer NOTICE = 2;
	public final static Integer ERROR = 3;
	public final static Integer FATAL = 3;

	/**
	 * 调试信息
	 * 
	 * @param msg
	 *            消息
	 */
	public void debug(String msg);

	/**
	 * 消息信息
	 * 
	 * @param msg
	 *            消息
	 */
	public void info(String msg);

	/**
	 * 通知信息
	 * 
	 * @param msg
	 *            消息
	 */
	public void notice(String msg);

	/**
	 * 错误信息
	 * 
	 * @param msg
	 *            消息
	 */
	public void error(String msg);

	public void error(Exception e);

	/**
	 * 错误信息
	 * 
	 * @param msg
	 *            消息
	 * @param e
	 *            异常堆栈
	 */
	public void error(String msg, Exception e);

	/**
	 * 严重错误紧急信息
	 * 
	 * @param msg
	 *            消息
	 */
	public void fatal(String msg);

	public void fatal(Exception e);
	public void fatal(String msg, Exception e);

}
