package com.zltel.common.utils.ssh;

import com.zltel.common.utils.ssh.bean.UserBean;

/**
 * 通用 SSH 采集接口
 * 
 * @author Wangch
 * 
 * @history
 * 
 *          2014.5.5 增加 是否存活检测函数 ;
 * 
 * 
 */
public interface RemoteShellScript {
	// 最大返回时间
	public final static int MAX_RETURN_TIME = 200000;
	// 默认返回时间
	public final static int DEFAULT_RETURN_TIME = 20000;

	/**
	 * 登录
	 * 
	 * @param handlerMonitor
	 * @param monitorScript
	 * @return
	 * @throws Exception
	 */
	public boolean login(UserBean userbean) throws Exception;

	/**
	 * 判断是否登录
	 * 
	 * @return
	 */
	public boolean isLoginSuccess();

	/**
	 * 关闭连接
	 */
	public void disconnect();

	/**
	 * 执行命令
	 * 
	 * @param cmd
	 * @return
	 * @throws Exception
	 */
	public String execute(String cmd) throws Exception;

	/**
	 * 执行命令
	 * 
	 * @param MaxReturnTime
	 *            设置最大返回时间
	 * @param cmd
	 * @return
	 * @throws Exception
	 */
	public String execute(int MaxReturnTime, String cmd) throws Exception;

	/**
	 * 执行命令
	 * 
	 * @param cmd
	 * @param timeout
	 *            最大超时返回时间
	 * @return
	 * @throws Exception
	 */
	public String execute(String cmd, int timeout) throws Exception;

	public String doSimgleCommand(String cmd) throws Exception;

	/**
	 * 执行命令 不需要返回
	 * 
	 * @param cmd
	 * @throws Exception
	 */
	public void executeWithoutReturn(String cmd) throws Exception;

	/**
	 * 获取返回信息
	 * 
	 * @return
	 */
	public String getResultMsg();

	public String getResultMsgAndClear();

	/**
	 * 2014.5.5 新增 ，过程 是在执行一遍 help 指令，看有无返回结果 检测 连接 是否 可用
	 * 
	 * @return true 可用， false 不可用
	 * @throws Exception
	 */
	public boolean checkIsAlive() throws Exception;

	public boolean stopRunning() throws Exception;

}
