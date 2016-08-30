package com.zltel.common.utils.ssh.util;

import java.util.HashMap;
import java.util.Map;

/**
 * SSH 命令默认 固定字符串配置
 * 
 * @author Sharped
 * 
 */
public class SSHParamValueUtil {
	/**
	 * 将 结尾字符串 不会改变的 型号配置到这里
	 */
	public static final Map<String, String> EndValueMap = new HashMap<String, String>();

	static {
		EndValueMap.put("HP_X86", "</>hpiLO->");
		EndValueMap.put("IBM_BLADE", "system>");
		// EndValueMap.put("IBM_X86", "");
		EndValueMap.put("DELL_BLADE", "$");
		// EndValueMap.put("DELL_X86", "");
		// EndValueMap.put("ZTE_BLADE", "root@CMM:~$|CLI(CMM)#");
		// EndValueMap.put("ZTE_X86", "");

	}

}
