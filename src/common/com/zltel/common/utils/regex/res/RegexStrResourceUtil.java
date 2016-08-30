package com.zltel.common.utils.regex.res;

/**
 * 正则 匹配字符串资源， 此工具类纪录常用的 正则字符串
 * 
 * @author Wangch
 * @time 2015.10.31
 * 
 */
public class RegexStrResourceUtil {
	/**
	 * IP V4 正则校验字符串
	 */
	public static final String IP_V4_Regex_Str = "((?:(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d))))";
	/**
	 * 换行正则表达式
	 */
	public static final String LINE_SPLIT_Regex_Str = "[\\n\\r]";
	/**
	 * 浮点型数字 正则表达式
	 * 
	 * @warn 此正则 有问题，无法匹配出 11.11.11这种结构，如果需要精确匹配，需要在前后加上限定条件
	 */
	public static final String FLOAT_NUM_STR = "[0-9]+(\\.?)[0-9]*";
	/**
	 * 中文汉字内容
	 */
	public static final String CHINESE_WORD = "[u4e00-u9fa5]";
	/**
	 * 空白行
	 */
	public static final String EMPTY_LINE = "ns*r";
	/**
	 * 电子邮件格式
	 */
	public static final String EMAIL = "w+([-+.]w+)*@w+([-.]w+)*.w+([-.]w+)*";
	/**
	 * 网址
	 */
	public static final String URL = "[a-zA-z]+://[^s]*";
}
