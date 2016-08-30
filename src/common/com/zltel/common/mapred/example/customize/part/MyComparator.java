package com.zltel.common.mapred.example.customize.part;

import org.apache.hadoop.io.WritableComparator;

/**
 * 自定义 排序比较函数
 * 
 * @author Wangch
 *
 */
public class MyComparator extends WritableComparator {

	public MyComparator() {
		super(DoubleSortKey.class, true);
	}
}
