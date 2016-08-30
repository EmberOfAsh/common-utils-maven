package com.zltel.common.utils.ssh;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.zltel.common.utils.ssh.bean.UserBean;
import com.zltel.common.utils.ssh.impl.JschImpl;

/**
 * 根据需要自动创建 脚本类
 * 
 * @author Sharped
 * 
 */
public class RemoteShellScriptFactory {

	private static String classPath = "com.ssh.impl.";
	/**
	 * 是否启用 缓存；目标：解决某些型号 连接数过多的问题
	 */
	public static final Boolean openCache = false;

	// shell 缓存
	private static final Map<String, RemoteShellScript> shellCache = new HashMap<String, RemoteShellScript>();
	// 读写锁同步
	private static final ReadWriteLock rwLock = new ReentrantReadWriteLock();

	public static RemoteShellScript createRemoteShellScript(UserBean userbean) throws Exception {
		RemoteShellScript shell = null;
		if (!openCache) {
			return createShell(userbean);
		}

		return shell;

	}

	/**
	 * 创建 命令执行对象
	 * 
	 * @param handlerMonitor
	 * @param monitorScript
	 * @return
	 * @throws Exception
	 */
	private static RemoteShellScript createShell(UserBean userbean) throws Exception {
		RemoteShellScript shell = new JschImpl();
		shell.login(userbean);

		return shell;

	}

	public static void main(String[] args) {
		String rt = "MINDTERM";
		String f = rt.substring(0, 1).toUpperCase();
		String result = rt.substring(1, rt.length());

	}

}
