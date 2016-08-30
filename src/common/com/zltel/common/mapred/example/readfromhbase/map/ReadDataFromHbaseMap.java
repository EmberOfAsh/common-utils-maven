package com.zltel.common.mapred.example.readfromhbase.map;

import java.io.IOException;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * 从HBase读取数据 进行处理
 * 
 * @author Wangch
 *
 */
public class ReadDataFromHbaseMap extends TableMapper<Text, Text> {

	/**
	 * 读取数据 进行处理
	 * 
	 * @param key
	 *            读取HBase的rowkey
	 * @param value
	 *            rowkey对应的结果
	 * @see org.apache.hadoop.mapreduce.Mapper#map(java.lang.Object,
	 *      java.lang.Object, org.apache.hadoop.mapreduce.Mapper.Context)
	 */
	@Override
	protected void map(ImmutableBytesWritable key, Result value,
			Mapper<ImmutableBytesWritable, Result, Text, Text>.Context context)
			throws IOException, InterruptedException {
		super.map(key, value, context);
		// 处理 读取结果，并写入输出
	}

}
