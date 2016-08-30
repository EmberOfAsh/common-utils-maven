package com.zltel.common.mapred.example.readfromdatabase.reduce;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import com.zltel.common.mapred.example.readfromdatabase.part.SaveDataBean;
import com.zltel.common.mapred.example.readfromdatabase.support.DbRWSupport;

public class ReadFromDataBaseReduce extends Reducer<Text, Text, DbRWSupport, DbRWSupport> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.hadoop.mapreduce.Reducer#reduce(java.lang.Object,
	 * java.lang.Iterable, org.apache.hadoop.mapreduce.Reducer.Context)
	 */
	@Override
	protected void reduce(Text arg0, Iterable<Text> arg1, Reducer<Text, Text, DbRWSupport, DbRWSupport>.Context arg2)
			throws IOException, InterruptedException {
		SaveDataBean _saveDataBean = new SaveDataBean();
		_saveDataBean.setUsername("wangch");
		_saveDataBean.setPassword("123456");

		// 将数据写入 数据库
		DbRWSupport key = new DbRWSupport(_saveDataBean);
		arg2.write(key, null);
	}

}
