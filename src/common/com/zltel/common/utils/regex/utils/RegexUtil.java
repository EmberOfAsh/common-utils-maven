package com.zltel.common.utils.regex.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.zltel.common.utils.string.StringUtil;

public class RegexUtil {
	/**
	 * 获取正则表达式 对应的分组
	 * 
	 * @param _s
	 * @param string
	 * @return
	 */
	public static List<Map<Integer, String>> regexMatchGroups(String _s, String regex) {
		List<Map<Integer, String>> rets = new ArrayList<Map<Integer, String>>();
		if (StringUtil.isNullOrEmpty(_s)) {
			return rets;
		}
		Pattern _p = Pattern.compile(regex);
		Matcher _m = _p.matcher(_s);
		Map<Integer, String> map = null;
		while (_m.find()) {
			map = new HashMap<Integer, String>();
			for (int i = 0; i <= _m.groupCount(); i++) {
				map.put(i, _m.group(i));
			}
			rets.add(map);
		}
		return rets;
	}

	/**
	 * 字符串是否包含指定 正则
	 * 
	 * @param _source
	 * @param regexStr
	 * @return
	 */
	public static final boolean containWithByRegex(String _source, String regexStr) {
		boolean r = false;
		Pattern p = Pattern.compile(regexStr);
		Matcher m = p.matcher(_source);

		if (m.find()) {
			r = true;
		}
		return r;
	}
	

}
