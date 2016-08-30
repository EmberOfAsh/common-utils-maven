package com.zltel.common.utils.num;

import java.text.DecimalFormat;

/**
 * 浮点型 工具类
 * 
 * @author Wangch
 * 
 */
public class DoubleUtil {
	/**
	 * 截取 浮点型精度
	 * 
	 * @param d
	 *            浮点数
	 * @param len
	 *            要保留的精度位数
	 * @return 截取后的值
	 */
	public static double KeepAccuracy(double d, int len) {
		double ret = 0;
		if (len == 0) {
			len = 1;
		}
		StringBuffer sb = new StringBuffer();
		sb.append("0.");
		for (int i = 0; i < len; i++) {
			sb.append("0");
		}
		DecimalFormat df = new DecimalFormat(sb.toString());
		String s = df.format(d);
		return Double.valueOf(s);
	}

	public static void main(String[] args) {
		System.out.println(KeepAccuracy(1300.43424234, 2));
	}
}
