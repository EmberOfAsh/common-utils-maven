package com.zltel.common.utils.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * 重写 Date 类
 * 
 * @warn SimpleDateFormat 类 非线程安全的,多线程使用时请注意
 * @author Wangch
 * 
 */
public class Date extends java.util.Date {
	private static final long serialVersionUID = 1L;

	public static final String SDF_STR = "yyyy-MM-dd HH:mm:ss";
	public static final String SDF_DAY_STR = "yyyy-MM-dd";
	public static final String SDF_TIME_STR = "HH:mm:ss";
	public static final String SDF_TIME_ZONE_STR = "yyyy-MM-dd'T'HH:mm:ssZZ";

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final SimpleDateFormat sdf_day = new SimpleDateFormat("yyyy-MM-dd");
	private static final SimpleDateFormat sdf_time = new SimpleDateFormat("HH:mm:ss");

	@SuppressWarnings("unused")
	private static final SimpleDateFormat sdf_time_zone = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZ");

	// -----------------------------------------------------
	private SimpleDateFormat _sdf;
	private SimpleDateFormat _sdf_day;
	private SimpleDateFormat _sdf_time;

	public Date(Long valueOf) {
		super(valueOf);
		init();
	}

	public Date() {
		super();
		init();
	}

	private void init() {
		this._sdf = DateUtil.getFullSdf();
		this._sdf_day = DateUtil.getSdf_Day();
		this._sdf_time = DateUtil.getSdf_Time();
	}

	/**
	 * 返回字符串格式 2015-11-06 17:29:26
	 */
	public String toLocaleString() {

		return this._sdf.format(this);
	}

	/**
	 * 2015-11-06 17:29:26
	 * 
	 * @return
	 */
	public String toDateTime() {
		return this._sdf.format(this);
	}

	/**
	 * 2015-11-06
	 * 
	 * @return
	 */
	public String toDate() {
		return this._sdf_day.format(this);
	}

	/**
	 * 17:29:26
	 * 
	 * @return
	 */
	public String toTime() {
		return this._sdf_time.format(this);
	}

	// --------------------------------静态方法区----------------------------------------------------

	/**
	 * 转换 日期格式
	 * 
	 * @param sdf
	 *            转换器 定义
	 * @param dt
	 *            日期字符串
	 * @return 日期对象
	 * @throws ParseException
	 *             转换异常
	 */
	public static final synchronized Date converToDate(SimpleDateFormat sdf, String dt) throws ParseException {
		return new Date(sdf.parse(dt).getTime());
	}

	/**
	 * 转换 成日期时间格式，像：1989-01-28 12:00:00
	 * 
	 * @param dt
	 * @return
	 */
	public static final synchronized String formatToDateTimeStr(Date dt) {
		return sdf.format(dt);
	}

	/**
	 * 转换成 日期格式 ， 像： 1989-01-28
	 * 
	 * @param dt
	 * @return
	 */
	public static final synchronized String formatToDate(Date dt) {
		return sdf_day.format(dt);
	}

	/**
	 * 转化成时间格式，像：12:33:33
	 * 
	 * @param dt
	 * @return
	 */
	public static final synchronized String formatToTime(Date dt) {
		return sdf_time.format(dt);
	}

}