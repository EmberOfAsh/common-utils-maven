package com.zltel.common.utils.ssh.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 获取 命令最后的 结束字符串
 * 
 * @author Sharped
 * 
 */
public class GetEndStrUtil {
	private static GetEndStrUtil currentInstince;

	public static String getDefaultEndStr(String deviceType, String inStr, String currentCmd) {
		if (null == currentInstince) {
			currentInstince = new GetEndStrUtil();
		}
		// 提取 不变的 结尾字符串库
		String str = SSHParamValueUtil.EndValueMap.get(deviceType.toUpperCase());

		return (str == null || str.trim().length() == 0) ? currentInstince.get_DefaultStartStr(deviceType, inStr,
				currentCmd) : str;
	}

	/**
	 * 主入口函数
	 * 
	 * @param deviceType
	 *            型号 HP_BLADE 等
	 * @param inStr
	 *            要解析的字符串
	 * @param currentCmd
	 *            当前执行的命令
	 * @return 结尾字符串
	 */
	private String get_DefaultStartStr(String deviceType, String inStr, String currentCmd) {
		String ret = "";
		deviceType = deviceType.toUpperCase();
		if (deviceType.indexOf("HP_B") != -1) {
			ret = this.getHP_BL_DefaultStartStr(inStr, currentCmd);
		} else if (deviceType.indexOf("IBM") != -1) {
			ret = this.getIBM_DefaultStartStr(inStr, currentCmd);
		} else if (deviceType.indexOf("DELL") != -1) {
			ret = this.getDELL_DefaultStartStr(inStr, currentCmd);
		} else {// 返回默认的字符串
			ret = this.getDefault_DefaultStartStr(inStr, currentCmd);
		}

		// HP_X86 固定

		return ret;
	}

	/**
	 * 获取 HP_BLADE 的结束字符串
	 * 
	 * @param inStr
	 * @return
	 */
	public static String getHP_BladeEndStr(String inStr, String cmd) {
		String endStr = "";
		if ("SHOW OA NETWORK".equals(cmd.trim().toUpperCase())) {// 由于是脚本可以直接命令

			Pattern pattern = Pattern.compile(".+");
			Matcher matcher = pattern.matcher(inStr);
			while (matcher.find()) {
				String s = matcher.group();
				if (s.trim().startsWith("Name")) {
					String[] st = s.trim().split(":");
					endStr = st[1].trim();
					break;
				}
			}
		} else {
			endStr = getDefaultEndStr("HP_BLADE", inStr, cmd);
		}
		return endStr;
	}

	private String getDefault_DefaultStartStr(String instr, String currentCommand) {
		String ret = "";

		String str = instr.trim();
		String[] lines = str.replace("\r", "").split("\n");
		if (lines.length < 3) {
			return "";
		}
		String line = null;
		boolean iscontinue = true;
		for (int index = 0; index < lines.length; index++) {
			line = lines[index];
			if (line.indexOf(currentCommand) != -1) {// 有当前命令，且包含其他字符串

				if (line.indexOf(">") != -1) {
					ret = line.replace(currentCommand, "").trim();
					iscontinue = false;
					break;
				}

			}
		}

		if (iscontinue) {
			for (int index = lines.length - 1; index >= 0; index--) {
				String lastline = lines[index];
				lastline = lastline.replace(currentCommand, "");
				if (lastline.length() > 1) { // &&
					ret = lastline.trim();
					break;
				}
			}
		}

		return ret;

	}

	/**
	 * 获取 IBM 默认开始字符串
	 * 
	 * @return
	 */
	private String getIBM_DefaultStartStr(String in, String currentCommand) {
		String ret = "";
		String str = in.trim();
		String[] lines = str.replace("\r", "").split("\n");

		if (lines.length < 3) {
			return "";
		}
		String line = null;
		boolean iscontinue = true;
		for (int index = 0; index < lines.length; index++) {
			line = lines[index];
			if (line.indexOf(currentCommand) != -1) {// 有当前命令，且包含其他字符串
				if (index >= 1) {// 当前命令的前一行
					line = lines[index - 1];
					if (line.indexOf(">") != -1) {
						String defaultStartStr = line.trim();
						ret = defaultStartStr;
						// LogOut.INFO("获取到默认开始字符串： " + defaultStartStr);
						iscontinue = false;
						break;
					}
				}

			}
		}

		if (iscontinue) {
			for (int index = lines.length - 1; index >= 0; index--) {
				String lastline = lines[index];
				lastline = lastline.replace(currentCommand, "");
				if (lastline.indexOf(">") != -1) { // &&
					// lastline.indexOf("-")
					// != -1
					// 包含 > 类似 OA-FASFDDF>
					// lastline = lastline.split(">")[0]+">";
					String defaultStartStr = lastline.trim();
					ret = defaultStartStr;
					// LogOut.INFO("获取到默认开始字符串： " + defaultStartStr);
					break;
				}

			}

		}
		return ret;

	}

	/**
	 * 获取 HP_BL 默认开始字符串
	 * 
	 * @return
	 */
	private String getHP_BL_DefaultStartStr(String in, String currentCommand) {
		String ret = "";
		String str = in.trim();
		String[] lines = str.replace("\r", "").split("\n");

		if (lines.length < 3) {
			return "";
		}

		String line = null;
		boolean iscontinue = true;
		for (int index = 0; index < lines.length; index++) {
			line = lines[index];

			if (line.indexOf(currentCommand) != -1) {// 有当前命令，且包含其他字符串
				String startWord = line.replace(currentCommand, "");
				if (startWord.trim().length() > 1) {
					String defaultStartStr = startWord.trim();
					ret = defaultStartStr;
					// LogOut.INFO("获取到默认开始字符串： " + defaultStartStr);
					iscontinue = false;
					break;
				}
			}
		}

		if (iscontinue) {
			for (int index = lines.length - 1; index >= 0; index--) {
				String lastline = lines[index];
				lastline = lastline.replace(currentCommand, "");
				if (lastline.indexOf(">") != -1) { // &&
					// lastline.indexOf("-")
					// != -1
					// 包含 > 类似 OA-FASFDDF>
					lastline = lastline.split(">")[0] + ">";
					String defaultStartStr = lastline.trim();
					ret = defaultStartStr;
					// LogOut.INFO("获取到默认开始字符串： " + defaultStartStr);
					break;
				}

			}
		}

		return ret;
	}

	/**
	 * 获取 DELL 默认开始字符串
	 * 
	 * @return
	 */
	private String getDELL_DefaultStartStr(String in, String currentCommand) {
		String ret = "$";

		return ret;
	}

	/**
	 * 获取 HP_DL 默认开始字符串
	 * 
	 * @deprecated 此方法 无用，因为 HP_X86的结束字符串 不变，因此用不到。
	 * @return
	 */
	private String getHP_DL_DefaultStartStr(String in, String currentCommand) {
		String ret = "";
		String str = in.trim();
		String[] lines = str.replace("\r", "").split("\n");

		if (lines.length < 3) {
			return "";
		}

		String line = null;
		boolean iscontinue = true;
		for (int index = 0; index < lines.length; index++) {
			line = lines[index];
			if (line.indexOf(currentCommand) != -1) {// 有当前命令，且包含其他字符串
				if (index >= 1) {// 当前命令的前一行
					line = lines[index - 1];
					if (line.indexOf(">") != -1) {
						String defaultStartStr = line.trim();
						ret = defaultStartStr;
						// LogOut.INFO("获取到默认开始字符串： " + defaultStartStr);
						iscontinue = false;
						break;
					}
				}

			}
		}

		if (iscontinue) {
			for (int index = lines.length - 1; index >= 0; index--) {
				String lastline = lines[index];
				lastline = lastline.replace(currentCommand, "");
				if (lastline.indexOf(">") != -1) { // &&
					// lastline.indexOf("-")
					// != -1
					// 包含 > 类似 OA-FASFDDF>
					// lastline = lastline.split(">")[0]+">";
					String defaultStartStr = lastline.trim();
					ret = defaultStartStr;
					// LogOut.INFO("获取到默认开始字符串： " + defaultStartStr);
					break;
				}

			}
		}

		return ret;
	}
}
