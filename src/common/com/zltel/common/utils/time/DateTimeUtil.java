package com.zltel.common.utils.time;

import java.util.Calendar;
import java.util.Date;

/**
 * 日期时间 获取工具类
 * 
 * @author Wangch
 * 
 */
public class DateTimeUtil {

	private Calendar c;

	private DateTimeUtil() {
		c = Calendar.getInstance();
	}

	/**
	 * 获取 实例
	 * 
	 * @return
	 */
	public static final DateTimeUtil getInstince() {
		return new DateTimeUtil();
	}

	/**
	 * 获取 当前年份
	 * 
	 * @return
	 */
	public static final int getCurrentYear() {
		return Calendar.getInstance().get(Calendar.YEAR);
	}

	/**
	 * 获取当前 月份, 从0开始
	 * 
	 * @return
	 */
	public static final int getCurrentMonth() {
		return Calendar.getInstance().get(Calendar.MONTH) + 1;
	}

	/**
	 * 获取当前 月份, 从1开始
	 * 
	 * @return
	 */
	public static final int getCurrentMonth_H() {
		return Calendar.getInstance().get(Calendar.MONTH) + 1;
	}

	/**
	 * 获取当前 几号,从1开始
	 * 
	 * @return
	 */
	public static final int getCurrentDayOfMonth() {
		return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
	}

	public static final int getCurrentWeekOfYear() {
		return Calendar.getInstance().get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * 获取 当前 月份的 第几周, 从1开始
	 * 
	 * @return
	 */
	public static final int getCurrentWeekOfMonth() {
		return Calendar.getInstance().get(Calendar.WEEK_OF_MONTH);
	}

	/**
	 * 获取当前 星期几，0-6 星期天=0
	 * 
	 * @return
	 */
	public static final int getCurrentDayOfWeek() {
		return Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 获取 12小时进制的时间
	 * 
	 * @return 0-11 小时
	 */
	public static final int getCurrentHourOf12() {
		return Calendar.getInstance().get(Calendar.HOUR);
	}

	/**
	 * 获取24小时进制的时间
	 * 
	 * @return 0-23小时
	 */
	public static final int getCurrentHourOf24() {
		return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 获取 分钟
	 * 
	 * @return
	 */
	public static final int getCurrentMinute() {
		return Calendar.getInstance().get(Calendar.MINUTE);
	}

	/**
	 * 获取 秒数
	 * 
	 * @return
	 */
	public static final int getCurrentSecond() {
		return Calendar.getInstance().get(Calendar.SECOND);
	}

	/**
	 * 获取 毫秒数
	 * 
	 * @return
	 */
	public static final int getCurrentMileSecond() {
		return Calendar.getInstance().get(Calendar.MILLISECOND);
	}

	/**
	 * 获取当前日期字符串： 2015-12-23
	 * 
	 * @return
	 */
	public static final String toCurrentDateStr() {
		return getCurrentYear() + "-" + getCurrentMonth_H() + "-" + getCurrentDayOfMonth();
	}

	/**
	 * 获取当时间字符串：23：33：33
	 * 
	 * @return
	 */
	public static final String toCurrent24TimeStr() {
		return getCurrentHourOf24() + ":" + getCurrentMinute() + ":" + getCurrentSecond();
	}

	/**
	 * 获取12小时 时间
	 * 
	 * @return 11:33:33
	 */
	public static final String toCurrent12TimeStr() {
		return getCurrentHourOf12() + ":" + getCurrentMinute() + ":" + getCurrentSecond();
	}

	/**
	 * 获取24小时 时间日期字符串
	 * 
	 * @return 1989-01-28 23：44：44
	 */
	public static final String toCurrent24DateTimeStr() {
		return toCurrentDateStr() + " " + toCurrent24TimeStr();
	}

	/**
	 * 获取24小时 时间日期字符串
	 * 
	 * @return 1989-01-28 11：44：44
	 */
	public static final String toCurrent12DateTimeStr() {
		return toCurrentDateStr() + " " + toCurrent12TimeStr();
	}

	// --------------------------------------------------------------------
	/**
	 * 获取 年份
	 * 
	 * @return 当前年份
	 */
	public int getYear() {
		return c.get(Calendar.YEAR);
	}

	/**
	 * 获取 月份 天
	 * 
	 * @return
	 */
	public int getDayOfMonth() {
		return c.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获取 第几月 , 从1月份开始
	 * 
	 * @return
	 */
	public int getMonth() {
		return c.get(Calendar.MONTH) + 1;
	}

	/**
	 * 获取 星期,星期天=1 - 星期6=7
	 * 
	 * @return
	 */
	public int getWeekDay() {
		return c.get(Calendar.DAY_OF_WEEK);
	}

	public final int getWeekOfYear() {
		return c.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * 获取 当前 月份的 第几周, 从1开始
	 * 
	 * @return
	 */
	public final int getWeekOfMonth() {
		return c.get(Calendar.WEEK_OF_MONTH);
	}

	/**
	 * 获取 12小时进制的时间
	 * 
	 * @return 0-11 小时
	 */
	public int getHourOf12() {
		return c.get(Calendar.HOUR);
	}

	/**
	 * 获取24小时进制的时间
	 * 
	 * @return 0-23小时
	 */
	public int getHourOf24() {
		return c.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 获取 分钟
	 * 
	 * @return
	 */
	public int getMinute() {
		return c.get(Calendar.MINUTE);
	}

	/**
	 * 获取 秒数
	 * 
	 * @return
	 */
	public int getSecond() {
		return c.get(Calendar.SECOND);
	}

	/**
	 * 获取 毫秒数
	 * 
	 * @return
	 */
	public int getMileSecond() {
		return c.get(Calendar.MILLISECOND);
	}

	/**
	 * 获取 日期格式字符串
	 * 
	 * @return 2015-12-23
	 */
	public String toDateStr() {
		return getYear() + "-" + getMonth() + "-" + getDayOfMonth();
	}

	/**
	 * 获取 24小时时间字符串
	 * 
	 * @return
	 */
	public String to24TimeStr() {
		return getHourOf24() + ":" + getMinute() + ":" + getSecond();
	}

	/**
	 * 获取12小时时间字符串
	 * 
	 * @return
	 */
	public String to12TimeStr() {
		return getHourOf12() + ":" + getMinute() + ":" + getSecond();
	}

	/**
	 * 获取24小时 日期时间字符串
	 * 
	 * @return
	 */
	public String to24DateTimeStr() {
		return toDateStr() + " " + to24TimeStr();
	}

	/**
	 * 获取 12小时日期时间字符串
	 * 
	 * @return
	 */
	public String to12DateTimeStr() {
		return toDateStr() + " " + to12TimeStr();
	}

	/**
	 * 获取 时间毫秒数
	 * 
	 * @return 时间毫秒数
	 */
	public long getTime() {
		return c.getTimeInMillis();
	}

	/**
	 * 设置 当前月份 时间前/后 时间
	 * 
	 * @param befOrAfVal
	 *            比如 -1 即为设置前一月份， +1 设置为下一月份
	 */
	public void setMonthOfNow(int befOrAfVal) {
		c.add(Calendar.MONTH, befOrAfVal);
	}

	/**
	 * 设置 当前日期 时间前/后 时间
	 * 
	 * @param befOrAfVal
	 *            比如 -1 即为设置前一天， +1 设置为 明天
	 */
	public void setDayOfNow(int befOrAfVal) {
		c.add(Calendar.DAY_OF_MONTH, befOrAfVal);
	}

	public void set24HourOfnNow(int befOrAfVal) {
		c.add(Calendar.HOUR_OF_DAY, befOrAfVal);
	}

	public void setHourOfNow(int befOrAfVal) {
		c.add(Calendar.HOUR, befOrAfVal);
	}

	public void setMinOfNow(int befOrAfVal) {
		c.add(Calendar.MINUTE, befOrAfVal);
	}

	public void setSecOfNow(int befOrAfVal) {
		c.add(Calendar.SECOND, befOrAfVal);
	}

	/**
	 * 设置 时间
	 * 
	 * @param date
	 */
	public void setTime(Date date) {
		c.setTime(date);
	}

	/**
	 * 是否 是 工作日(星期1-5）
	 * 
	 * @return
	 */
	public boolean isWeekday() {
		int wd = this.getWeekDay();
		boolean ret = wd == Calendar.SUNDAY || wd == Calendar.SATURDAY;
		return !ret;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.to24DateTimeStr() + "-" + this.getTime();
	}

	public static void main(String[] args) throws InterruptedException {
		DateTimeUtil dt = DateTimeUtil.getInstince();
		System.out.println(dt.getMonth());
		System.out.println("0当前时间：" + dt.to24DateTimeStr() + "," + dt.getTime());
		dt.setMonthOfNow(-1);
		System.out.println("1个月以前： " + dt.to24DateTimeStr() + "," + dt.getTime());
		dt.setMonthOfNow(3);
		System.out.println("3个月以前： " + dt.to24DateTimeStr() + "," + dt.getTime());

		System.out.println("==================");
		dt.setHourOfNow(-2);
		System.out.println("10小时以后： " + dt.to24DateTimeStr() + "," + dt.getTime());

	}
}
