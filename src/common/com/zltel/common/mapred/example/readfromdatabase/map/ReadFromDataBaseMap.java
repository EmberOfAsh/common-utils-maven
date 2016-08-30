package com.zltel.common.mapred.example.readfromdatabase.map;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import com.zltel.common.mapred.example.readfromdatabase.part.SaveDataBean;
import com.zltel.common.mapred.example.readfromdatabase.support.DbRWSupport;

public class ReadFromDataBaseMap extends Mapper<LongWritable, DbRWSupport, Text, Text> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.hadoop.mapreduce.Mapper#map(java.lang.Object,
	 * java.lang.Object, org.apache.hadoop.mapreduce.Mapper.Context)
	 */
	@Override
	protected void map(LongWritable key, DbRWSupport value,
			Mapper<LongWritable, DbRWSupport, Text, Text>.Context context) throws IOException, InterruptedException {
		SaveDataBean sdb = value.getSaveDataBean();
		// 获取到数据库查询记录
	}

}
