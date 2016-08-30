/**
 * 
 */
package com.zltel.common.utils.num;

import java.util.HashMap;
import java.util.Map;

import com.zltel.common.utils.string.StringUtil;


/**
 * 数字英文转 数字
 * 
 * @author Wangch
 * 
 */
public class NumWordConverToNum {
	private static Map<String, Integer> _knowMap = new HashMap<String, Integer>();
	static {
		_knowMap.put("zero", 0);
		_knowMap.put("one", 1);
		_knowMap.put("two", 2);
		_knowMap.put("three", 3);
		_knowMap.put("four", 4);
		_knowMap.put("five", 5);
		_knowMap.put("six", 6);
		_knowMap.put("seven", 7);
		_knowMap.put("eight", 8);
		_knowMap.put("nine", 9);
		_knowMap.put("ten", 10);
		_knowMap.put("eleven", 11);
		_knowMap.put("twelve", 12);
		_knowMap.put("thirteen", 13);
		_knowMap.put("fourteen", 14);
		_knowMap.put("fifteen", 15);
		_knowMap.put("sixteen", 16);
		_knowMap.put("seventeen", 17);
		_knowMap.put("eighteen", 18);
		_knowMap.put("nineteen", 19);
		_knowMap.put("twenty", 20);
		_knowMap.put("thirty", 30);
		_knowMap.put("forty", 40);
		_knowMap.put("fifty", 50);
		_knowMap.put("sixty", 60);
		_knowMap.put("seventy", 70);
		_knowMap.put("eighty", 80);
		_knowMap.put("ninety", 90);
	}

	/**
	 * 转换数字
	 * 
	 * @param in
	 * @return 返回转换后的数字，如果失败者返回null
	 */
	public static Integer convert(String in) {
		if (StringUtil.isNotNullAndEmpty(in)) {
			in = in.trim().toLowerCase();
			return _knowMap.get(in);
		}
		return null;
	}

}
