package com.zltel.common.utils.ssh.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.zltel.common.utils.ssh.RemoteShellScript;
import com.zltel.common.utils.ssh.bean.UserBean;
import com.zltel.common.utils.ssh.util.GetEndStrUtil;

public class JschImpl implements RemoteShellScript {
	/** 信息显示行数 **/
	public static final Integer maxLineHeight = 20000;

	private static Integer MaxReturnTime = 20000;// 最大返回事件 120S
	private static final Integer waitTime = 500;
	private static final Boolean donClose = true;// 不要关闭
	private boolean islogin = false;// 是否登录

	private Date cmdStartTime;

	private String currentCommand;// 当前命令
	private String defaultEndStr;// 默认结束字符串
	private String deviceType = "";// 设备型号

	private StringBuffer resultMsg = new StringBuffer();

	private static final String charset = "UTF-8";

	// 会话 session
	private Session session = null;
	// Channel
	private Channel channel = null;
	// 结果输出流
	private OutputStream resultOut = null;
	//
	final PipedInputStream stdin = new PipedInputStream();
	// 命令输出流
	PipedOutputStream stdout = null;// new PipedOutputStream(stdin);

	private void init() {
		resultMsg = new StringBuffer();
		resultMsg.append(this.defaultEndStr);
		resultMsg.append("\n");
		currentCommand = "";
	}

	/**
	 * 登录用户
	 * 
	 * @param server
	 * @param user
	 * @param password
	 * @return
	 * @throws JSchException
	 */
	public boolean doLogin(String server, String user, String password, String port) throws JSchException {

		JSch jsch = new JSch();
		session = jsch.getSession(user, server, (port != null && port.trim().length() != 0) ? Integer.valueOf(port) : 22);
		session.setPassword(password);
		java.util.Properties config = new java.util.Properties();
		config.put("StrictHostKeyChecking", "no");
		session.setConfig(config);
		//
		// session.setOutputStream(System.err);
		session.connect(20000);

		try {
			Thread.sleep(waitTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * 用Stream方式输出命令，需要调用此设置
	 * 
	 * @throws IOException
	 * @throws JSchException
	 */
	public void initStream() throws IOException, JSchException {
		resultOut = new MyOutStream();
		stdout = new PipedOutputStream(stdin);

		channel = session.openChannel("shell");
		channel.setInputStream(stdin, donClose);
		channel.setOutputStream(resultOut, donClose);
		channel.connect(10 * 1000);
		((ChannelShell) channel).setPtySize(500, maxLineHeight, 500, 800);// 设置屏幕大小
		try {
			Thread.sleep(waitTime);
		} catch (InterruptedException e) {
		}
	}

	/**
	 * 用流执行命令
	 * 
	 * @param cmd
	 * @param maxReturn
	 *            超时时间
	 * @return
	 * @throws IOException
	 */
	public String doExecuteCmdByStream(String cmd, Integer maxReturn) throws Exception {
		writeCmdStream(cmd + (cmd.endsWith("\n\r") ? "" : "\n\r"));
		cmdStartTime = new Date();// 开始时间

		return getString(maxReturn);
	}

	/**
	 * 获取 命令结果字符串
	 * 
	 * @param maxReturn
	 * @return
	 * @throws Exception
	 */
	private String getString(Integer maxReturn) throws Exception {
		String r;
		boolean exit = false;

		do {
			Thread.sleep(200);
			exit = checkCommandCompleteExit();
			if (!exit) {
				exit = checkMaxReturnTime(maxReturn);
			}
		} while (!exit);

		r = resultMsg.toString();

		init();

		return replace(r);
	}

	// 校验 命令 执行是否完成
	private boolean checkCommandCompleteExit() throws Exception {
		boolean ret = false;
		ret = defaultCheckComplete();
		return ret;
	}

	private boolean defaultCheckComplete() {
		boolean ret = false;

		// 校验最后一行是否包含指定 字符串
		String[] strs = resultMsg.toString().trim().replace("\r", "").split("\n");
		if (strs.length <= 2) {// 更改
			return false;
		}
		String str = "";
		for (int index = strs.length - 1; index >= 0; index--) {
			str = strs[index].trim();
			if (str.length() > 0) {// 不是空字符
				String m = checkLinuxComplete(str);
				if (m != null && m.trim().length() > 0) {
					ret = true;
				} else {
					ret = false;
				}
				break;
			}
		}

		return ret;
	}

	/** 替换 无意义内容 **/
	private String replace(String src) {
		src = src.replace("\r", "");// 去除回车符
		return src;
	}

	/**
	 * 检测最大返回时间 *
	 * 
	 * @throws Exception
	 */
	private boolean checkMaxReturnTime(Integer maxReturn) throws Exception {
		long last = System.currentTimeMillis() - cmdStartTime.getTime();
		boolean ret = last >= maxReturn ? true : false;
		if (ret) {
			runCtrl();
			Exception e = new Exception("超时， " + "\r\n超过时间： " + (last + "\r\n报文： " + resultMsg.toString()));
			throw e;
			// LogOut.ERROR();// +
		}

		return ret;
	}

	/**
	 * 输出 命令
	 * 
	 * @param cmd
	 * @throws IOException
	 */
	private void writeCmdStream(String cmd) throws IOException {
		byte[] cmds = (cmd + (cmd.endsWith("\n\r") ? "" : "\n\r")).getBytes();
		this.writeCmdStream(cmds);
		try {
			Thread.sleep(waitTime);
		} catch (InterruptedException e) {
		}
	}

	private void writeCmdStream(char cmd) throws IOException {
		this.stdout.write(cmd);
		this.stdout.flush();
		try {
			Thread.sleep(waitTime);
		} catch (InterruptedException e) {
		}
	}

	private void writeCmdStream(byte[] cmd) throws IOException {
		this.stdout.write(cmd);
		this.stdout.flush();
		try {
			Thread.sleep(waitTime);
		} catch (InterruptedException e) {
		}
	}

	/**
	 * 输出 ctrl 按键
	 */
	private void runCtrl() {
		try {
			this.writeCmdStream((char) 3);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 单独执行一条命令
	 * 
	 * @param cmd
	 * @return
	 * @throws Exception
	 */
	public String doSimpleExecute(String cmd) throws Exception {
		String ret = "";
		Channel channel = null;
		InputStream in = null;
		try {
			channel = session.openChannel("exec");
			((ChannelExec) channel).setCommand(cmd);
			channel.setInputStream(null);
			((ChannelExec) channel).setErrStream(System.err);

			channel.connect();
			in = channel.getInputStream();
			ret = readInputStream(in);
			channel.disconnect();

		} catch (Exception e) {
			throw e;
		} finally {
			if (null != channel) {
				channel.disconnect();
			}
			if (null != in) {
				in.close();
			}
		}
		return ret;
	}

	/**
	 * 读取 input流
	 * 
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public String readInputStream(InputStream in) throws IOException {
		StringBuffer sb = new StringBuffer();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in, Charset.forName(charset)));
		String buf = null;
		while ((buf = reader.readLine()) != null) {
			sb.append(buf);
			sb.append("\n");
		}
		reader.close();

		return sb.toString();
	}

	/**
	 * 关闭
	 */
	public void doClose() {
		if (!islogin) {
			return;
		}
		try {
			writeCmdStream("exit");// 退出命令
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (channel != null) {
			channel.disconnect();
		}
		if (session != null) {
			session.disconnect();
		}
		if (stdout != null) {
			try {
				stdout.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (stdin != null) {
			try {
				stdin.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (resultOut != null) {
			try {
				resultOut.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 收集输出的 结果
	 * 
	 * @author Sharped
	 * 
	 */
	final class MyOutStream extends OutputStream {
		public void write(int b) throws IOException {
			if (charFilter(b)) {
				resultMsg.append((char) b);
			}
		}

	}

	private boolean charFilter(int b) {
		boolean ret = true;
		int[] black = new int[] { 13, 0 };
		for (int i : black) {
			if (i == b) {
				ret = false;
				break;
			}
		}

		return ret;
	}

	public void disconnect() {
		this.doClose();
	}

	public String execute(String cmd) throws Exception {
		this.currentCommand = cmd;
		return this.doExecuteCmdByStream(cmd, MaxReturnTime);
	}

	public String execute(int maxReturnTime, String cmd) throws Exception {
		MaxReturnTime = maxReturnTime;
		this.currentCommand = cmd;

		return this.doExecuteCmdByStream(cmd, MaxReturnTime);
	}

	public String execute(String cmd, int timeout) throws Exception {
		this.currentCommand = cmd;
		return this.doExecuteCmdByStream(cmd, timeout);
	}

	public void executeWithoutReturn(String cmd) throws Exception {
		this.currentCommand = cmd;
		this.writeCmdStream(cmd);
		Thread.sleep(waitTime);
	}

	public boolean isLoginSuccess() {
		return islogin;
	}

	public boolean login(UserBean userBean) throws Exception {

		try {
			islogin = this.doLogin(userBean.getIp(), userBean.getUsername(), userBean.getPassword(), userBean.getPort());
			this.initStream();
			if (islogin) {
				getEndStr(defaultEndStr);
			}
		} catch (Exception e) {
			islogin = false;
			throw e;
		}

		return islogin;
	}

	/**
	 * 获取 默认 开始结束串
	 * 
	 * @throws Exception
	 */
	private void getEndStr(String dn) throws Exception {
		String newEndStr = null;
		String cmd = "";
		if (deviceType.toUpperCase().contains("HP_B")) {
			// HP_BLADE 需要每次重新 绑定
			try {
				cmd = "SHOW OA NETWORK";//
				this.writeCmdStream(cmd);
				Thread.sleep(4000);
				newEndStr = GetEndStrUtil.getHP_BladeEndStr(resultMsg.toString(), cmd);

			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			try {

				if (dn == null || dn.trim().length() == 0) {
					cmd = "help";
					this.writeCmdStream(cmd);
					Thread.sleep(2000);
					newEndStr = GetEndStrUtil.getDefaultEndStr(deviceType, resultMsg.toString(), cmd);
				} else {
					newEndStr = dn;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		if ((null == newEndStr || newEndStr.trim().length() == 0) && (dn == null || dn.trim().length() == 0)) {
			// 没有读取到默认 结束字符， 判定为没 有响应
			throw new RuntimeException("服务器没有反应");
		}

		if (!newEndStr.trim().equals(defaultEndStr)) {
			// 写入数据库
			this.defaultEndStr = checkLinuxComplete(newEndStr);
		} else {
		}
		this.init();//
	}

	private String checkLinuxComplete(String str) {
		String ret = null;
		Pattern pattern = Pattern.compile("\\w+@\\w+");
		Matcher match = pattern.matcher(str);
		if (match.find()) {
			ret = match.group();
		}

		return ret;
	}

	public boolean checkIsAlive() throws Exception {
		if (!this.isLoginSuccess()) {// 没登陆成功
			return false;
		}

		boolean ret = false;
		String cmd = "help";
		this.writeCmdStream(cmd);
		Thread.sleep(5000);

		ret = resultMsg.length() > 20 ? true : false;
		this.init();
		return ret;
	}

	public String doSimgleCommand(String cmd) throws Exception {
		return doSimpleExecute(cmd);
	}

	public String getResultMsg() {
		return resultMsg.toString();
	}

	public String getResultMsgAndClear() {
		String ret = resultMsg.toString();
		resultMsg.setLength(0);// 清空
		return ret;
	}

	public boolean stopRunning() throws Exception {
		runCtrl();
		return true;
	}

}
