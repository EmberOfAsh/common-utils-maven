package com.zltel.common.mapred.example.readfromhbase.reduce;

import java.io.IOException;
import java.util.List;

import org.apache.hadoop.hbase.client.Mutation;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import com.zltel.common.mapred.example.readfromhbase.main.ReadDataFromHbaseMain;

public class ReadDataFromHbaseReduce extends TableReducer<Text, Text, ImmutableBytesWritable> {

	/**
	 * 
	 * 
	 * @see org.apache.hadoop.mapreduce.Reducer#reduce(java.lang.Object,
	 *      java.lang.Iterable, org.apache.hadoop.mapreduce.Reducer.Context)
	 */
	@Override
	protected void reduce(Text arg0, Iterable<Text> arg1,
			Reducer<Text, Text, ImmutableBytesWritable, Mutation>.Context context)
			throws IOException, InterruptedException {
		// 保存 hbase 两种方法
		// 第一种 ,构建 Put对象,直接保存，不需要指定表名
		List<Put> puts = createPuts();
		if (puts != null && !puts.isEmpty()) {
			for (Put put : puts) {
				context.write(null, put);
			}
		}
		// 第二种,制定表名，保存Put对象
		// 创建保存表,如果有需要 可以创建多张表
		ImmutableBytesWritable imrowkey = new ImmutableBytesWritable(Bytes.toBytes(ReadDataFromHbaseMain.writeTable));
		// 保存数据
		if (puts != null && !puts.isEmpty()) {
			for (Put put : puts) {
				context.write(imrowkey, put);
			}
		}
	}

	private List<Put> createPuts() {
		// 创建插入 Put对象
		return null;
	}
}
