package com.zltel.common.utils.string;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.zltel.common.utils.regex.res.RegexStrResourceUtil;

/**
 * String 类处理工具类
 * 
 * @author Wangch
 * 
 */
public class StringUtil {

	private static boolean isNullOrEmpty(String in) {
		return null == in ? true : in.trim().length() > 0 ? false : true;
	}

	private static boolean isNotNullAndEmpty(String in) {
		return !isNullOrEmpty(in);
	}

	/**
	 * 字符串是否是 null 或者空字符
	 * 
	 * @param ins
	 *            连续多个字符串
	 * @return 如果其中有一个为空，则返回true，否则false
	 */
	public static boolean isNullOrEmpty(String... ins) {
		boolean ret = false;
		for (String i : ins) {
			if (isNullOrEmpty(i)) {
				ret = true;
				break;
			} else {
				ret = false;
			}
		}
		return ret;
	}

	/**
	 * 判断 输入的所有 是否都不是空并且null
	 * 
	 * @param ins
	 *            多个变量
	 * @return true不是空，false是空
	 */
	public static boolean isNotNullAndEmpty(String... ins) {
		boolean ret = false;
		for (String i : ins) {
			if (isNotNullAndEmpty(i)) {
				ret = true;
			} else {
				ret = false;
				break;
			}
		}
		return ret;
	}

	/**
	 * 去除多次重复的字符
	 * 
	 * @param in
	 * @param count
	 * @return
	 */
	public static String replaceMoreChar(String in, char c, int count) {
		StringBuffer sb = new StringBuffer();
		String[] ss = in.split("[" + c + "]{" + count + "}");
		for (String s : ss) {
			if (!StringUtil.isNullOrEmpty(s)) {
				sb.append(s.trim() + c);
			}
		}

		return sb.toString().trim();
	}

	/**
	 * 是否是数字
	 * 
	 * @param string
	 * @return
	 */
	public static boolean isNum(String str) {
		if (StringUtil.isNullOrEmpty(str)) {
			return false;
		}
		str = str.trim();
		Pattern pattern = Pattern.compile("^" + RegexStrResourceUtil.FLOAT_NUM_STR + "$");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * 按 多次重复的字符，将字符串分割成一个数组
	 * 
	 * @param line
	 *            替换字符串
	 * @param c
	 *            多次重复字符
	 * @param i
	 *            次数
	 */
	public static String[] splitToArray(String line, char c, int count) {
		StringBuffer sb = new StringBuffer();
		List<String> rets = new ArrayList<String>();
		String[] ss = line.split("[" + c + "]{" + count + "}");
		for (String s : ss) {
			if (!StringUtil.isNullOrEmpty(s)) {
				sb.append(s.trim() + c);
				rets.add(s.trim());
			}
		}
		return rets.toArray(new String[rets.size()]);
	}

	/**
	 * 切割字符串 并将 每个字符串 trim
	 * 
	 * @param line
	 * @param regex
	 * @param trim
	 *            是否进行 trim ，不填 即默认调用
	 * @return
	 */
	public static String[] splitAndTrim(String line, String regex, boolean... trim) {
		String[] ret = line.split(regex);
		if (trim.length == 0 || (trim.length > 0 && trim[0])) {
			String[] nret = new String[ret.length];
			for (int i = 0; i < ret.length; i++) {
				nret[i] = ret[i].trim();
			}
			return nret;
		}
		return ret;
	}

	/**
	 * 获取分隔符说对应的 数据, 如果没有 返回空
	 * 
	 * @param _l
	 * @param string
	 * @param i
	 * @return
	 */
	public static String getSplitStrByIndex(String _l, String regexStr, int i) {
		String[] ss = _l.split(regexStr);
		if (ss.length > i) {
			return ss[i].trim();
		}
		return null;
	}

	/**
	 * 字符串是否 以 指定正则表达式开头
	 * 
	 * @param _source
	 *            匹配字符串
	 * @param regexStr
	 *            正则表达式
	 * @return
	 */
	public static final boolean startWithByRegex(String _source, String regexStr) {
		boolean r = false;
		Pattern p = Pattern.compile(regexStr);
		Matcher m = p.matcher(_source);

		if (m.find()) {
			r = true;
		}
		return r;
	}

	/**
	 * 获取 字符串 以 指定正则表达式开头
	 * 
	 * @param _source
	 * @param regexStr
	 * @return
	 */
	public static final String startWithByRegexAndGetIt(String _source, String regexStr) {
		String r = null;
		Pattern p = Pattern.compile(regexStr);
		Matcher m = p.matcher(_source);

		if (m.find()) {
			r = m.group();
		}
		return r;
	}

	public static final String containWithByRegexAndGetIt(String _source, String regexStr) {
		String r = null;
		Pattern p = Pattern.compile(regexStr);
		Matcher m = p.matcher(_source);

		if (m.find()) {
			r = m.group();
		}
		return r;
	}

	/**
	 * 转换字符串 到Map 结构 k:v k2:v2
	 * 
	 * @param _in
	 *            字符串
	 * @param splitRegex
	 *            分割的 正则字符串
	 * @return
	 */
	public static Map<String, String> convertLineToMap(String _in, String splitRegex) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		String[] ss = splitAndTrim(_in, RegexStrResourceUtil.LINE_SPLIT_Regex_Str);
		if (ss != null && ss.length > 0) {
			for (String s : ss) {
				String[] _ss = s.split(splitRegex);
				String k = null;
				String v = null;
				if (_ss.length >= 1) {
					k = _ss[0].trim();
					if (_ss.length >= 2) {
						v = _ss[1].trim();
					}
				}
				if (k != null) {
					map.put(k, v);
				}
			}
		}
		return map;
	}

	/**
	 * 获取 （值） 总的值
	 * 
	 * @param splitStrByIndex
	 * @param string
	 * @param string2
	 * @return
	 */
	public static String getInnerValue(String _a, String startStr, String endStr) {
		String ret = "";
		if (_a.contains(startStr)) {
			ret = _a.split(startStr)[1];
		}
		if (ret.contains(endStr)) {
			ret = ret.split(endStr)[0];
		}
		return ret;
	}

	/**
	 * 获取正则表达式 对应的分组
	 * 
	 * @param _s
	 * @param string
	 * @return
	 */
	public static List<Map<String, String>> regexMatchGroups(String _s, String regex) {
		List<Map<String, String>> rets = new ArrayList<Map<String, String>>();
		Pattern _p = Pattern.compile(regex);
		Matcher _m = _p.matcher(_s);
		Map<String, String> map = null;
		while (_m.find()) {
			map = new HashMap<String, String>();
			for (int i = 0; i <= _m.groupCount(); i++) {
				map.put(String.valueOf(i), _m.group(i));
			}
			rets.add(map);
		}
		return rets;
	}

	/**
	 * 检测目标字符串 是否包含 子字符串
	 * 
	 * @param str
	 *            目标字符串
	 * @param strs
	 *            被包含字符串,不传入则默认为空，返回 true
	 * @return true:至少有一个包含，false:不包含
	 */
	public static boolean contains(String str, String... strs) {
		boolean ret = false;
		if (isNullOrEmpty(str)) {
			return false;
		}
		if (strs != null && strs.length > 0) {
			for (String s : strs) {
				if (str.contains(s)) {
					ret = true;
					break;
				}
			}
		} else {
			ret = true;
		}
		return ret;
	}

	/**
	 * 格式化 数字 为固定长度,不够长度填充0
	 * 
	 * @param v
	 * @param length
	 * @return
	 */
	public static String toNumFix(int v, int length) {
		return toFix(v, length);
	}

	/**
	 * 格式化为固定长度
	 * 
	 * @warn 这里 v 必须是可以 隐 转换为 int 的 类型，否则会抛异常
	 * @param v
	 * @param length
	 * @return
	 */
	public static String toFix(Object v, int length) {
		return String.format("%0" + length + "d", v);
	}

	/**
	 * 倒转 字符串 <br>
	 * <blockquote>
	 * 
	 * <pre>
	 * String s1 = "abc";
	 * String s2 = StringUtil.reverse(s1);
	 * System.out.println(s2);//cba
	 * 输出: cba
	 * </pre>
	 * 
	 * </blockquote>
	 * 
	 * @param s
	 *            要倒转的字符串
	 * @return 被倒转的字符串
	 */
	public static String reverse(String s) {
		return new StringBuffer(s).reverse().toString();
	}

	/**
	 * 判断是否 相等
	 * 
	 * @param o1
	 *            参数1
	 * @param o2
	 *            参数2
	 * @return 如果都为 String ,调用 equals比较;否则 == 比较
	 */
	public static boolean equals(Object o1, Object o2) {
		if (o1 == o2) {
			return true;
		}
		if (o1 instanceof String && o2 instanceof String) {
			return o1.equals(o2);
		}
		return false;
	}

	public static void main(String[] args0) {
		String a = "a";
		String b = new String("a");
		System.out.println(equals(a, b));
	}

}
