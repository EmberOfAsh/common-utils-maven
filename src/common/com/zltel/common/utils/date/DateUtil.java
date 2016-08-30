package com.zltel.common.utils.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 日期 计算工具类
 * 
 * @warn SimpleDateFormat 类 非线程安全的,多线程使用时请注意
 * @author Wangch
 * 
 */
public class DateUtil {
	public static final String SDF_STR = "yyyy-MM-dd HH:mm:ss";
	public static final String SDF_DAY_STR = "yyyy-MM-dd";
	public static final String SDF_TIME_STR = "HH:mm:ss";
	public static final String SDF_TIME_ZONE_STR = "yyyy-MM-dd'T'HH:mm:ssZZ";

	/** 格式模式: yyyy-MM-dd HH:mm:ss , eg: 2015-12-23 23:11:11 */
	@Deprecated
	public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/** 格式模式: yyyy-MM-dd , eg: 2015-12-23 **/
	@Deprecated
	public static final SimpleDateFormat sdf_day = new SimpleDateFormat("yyyy-MM-dd");
	/** 格式模式: HH:mm:ss , eg: 23:11:11 */
	@Deprecated
	public static final SimpleDateFormat sdf_time = new SimpleDateFormat("HH:mm:ss");
	/** 格式模式: yyyy-MM-dd HH:mm:ss , eg:2015-12-23 23 **/
	@Deprecated
	public static final SimpleDateFormat sdf_hour = new SimpleDateFormat("yyyy-MM-dd HH");
	/**
	 * 一个小时
	 */
	public static final long RANGE_HOUR = 3600 * 1000;
	/**
	 * 一天
	 */
	public static final long RANGE_DAY = 24 * RANGE_HOUR;

	// -----------------------------------------------------
	/**
	 * 获取 yyyy-MM-dd HH:mm:ss 格式的 SimpleDateFormat
	 * 
	 * @return SimpleDateFormat(新建)
	 */
	public static final SimpleDateFormat getFullSdf() {
		return new SimpleDateFormat(SDF_STR);
	}

	/**
	 * 获取 yyyy-MM-dd 格式的 SimpleDateFormat
	 * 
	 * @return SimpleDateFormat(新建)
	 */
	public static final SimpleDateFormat getSdf_Day() {
		return new SimpleDateFormat(SDF_DAY_STR);
	}

	/**
	 * 获取 HH:mm:ss 格式的 SimpleDateFormat
	 * 
	 * @return SimpleDateFormat(新建)
	 */
	public static final SimpleDateFormat getSdf_Time() {
		return new SimpleDateFormat(SDF_TIME_STR);
	}

	/**
	 * 获取 yyyy-MM-dd'T'HH:mm:ssZZ 包含时区信息 格式的 SimpleDateFormat
	 * 
	 * @return SimpleDateFormat(新建)
	 */
	public static final SimpleDateFormat getSdf_Time_Zone() {
		return new SimpleDateFormat(SDF_TIME_ZONE_STR);
	}

	// -----------------------------------------------------

	public static SimpleDateFormat createFormat(String format) {
		return new SimpleDateFormat(format);
	}

	/**
	 * 转换 日期格式
	 * 
	 * @param sdf
	 *            转换器 定义
	 * @param dt
	 *            日期字符串
	 * @return 日期对象
	 * @warn 注意 SimpleDateFormat线程不安全
	 * @throws ParseException
	 *             转换异常
	 */
	public static Date converToDate(SimpleDateFormat sdf, String dt) throws ParseException {
		return new Date(sdf.parse(dt).getTime());
	}

	/**
	 * 时间间隔
	 * 
	 * @param sdt
	 *            开始时间
	 * @param edt
	 *            解释时间
	 * @param range
	 *            时间间隔
	 * @notice 请注意，如果 基础时间+时间范围 > 结束时间， 则此结束时间将 不会包含在返回列表里边
	 * @return
	 */
	public static List<Date> dateSplit(Date sdt, Date edt, long range) {
		List<Date> rets = new ArrayList<Date>();
		Date ndt = sdt;
		while (ndt.getTime() <= edt.getTime()) {
			rets.add(ndt);
			ndt = new Date(ndt.getTime() + RANGE_DAY);
		}
		return rets;
	}

	public static List<java.util.Date> dateSplit(java.util.Date sdt, java.util.Date edt, long range) {
		Date _sdt = new Date(sdt.getTime());
		Date _edt = new Date(edt.getTime());

		List<Date> dts = dateSplit(_sdt, _edt, range);
		List<java.util.Date> rets = new ArrayList<java.util.Date>();
		for (Date dt : dts) {
			rets.add(new java.util.Date(dt.getTime()));
		}
		return rets;
	}
}
