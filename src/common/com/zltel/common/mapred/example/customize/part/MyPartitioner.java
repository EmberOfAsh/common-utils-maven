package com.zltel.common.mapred.example.customize.part;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

/**
 * 自定义分区函数类,此类决定数据送往哪一个Reduce进行处理
 * 
 * @author Wangch
 *
 */
public class MyPartitioner extends Partitioner<DoubleSortKey, Text> {

	@Override
	public int getPartition(DoubleSortKey key, Text value, int numPartitions) {
		return Math.abs(key.getFirstKey().hashCode() * 257) % numPartitions;
	}
}
