package com.zltel.common.mapred.example.customize.part;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

/**
 * 自定义分组类，决定了相同条件的数据归为一组。这里 根据相同 的IMSI ，将值 放入value集合
 * 
 * @author Wangch
 *
 */
public class GroupingComparator extends WritableComparator {
	protected GroupingComparator() {
		super(DoubleSortKey.class, true);
	}

	@Override
	// Compare two WritableComparables.
	public int compare(WritableComparable w1, WritableComparable w2) {
		DoubleSortKey ip1 = (DoubleSortKey) w1;
		DoubleSortKey ip2 = (DoubleSortKey) w2;
		Text _k1 = ip1.getFirstKey();
		Text _k2 = ip2.getFirstKey();
		// LongWritable _v1 = ip1.getSecondKey();
		// LongWritable _v2 = ip2.getSecondKey();

		// 升序
		return _k1.equals(_k2) ? 0 : _k1.compareTo(_k2);

	}
}
