package com.zltel.common.mapred.base;

import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * HDFS 读写 配置项设置
 * 
 * @author Wangch
 *
 */
public class HdfsMRJob extends BaseMRJob {
	/**
	 * 设置 HDFS 输入路径
	 * 
	 * @param job
	 *            任务
	 * @param inpath
	 *            输入hdfs路径
	 * @throws IllegalArgumentException
	 * @throws IOException
	 */
	public static void addInputPath(Job job, String inpath) throws IllegalArgumentException, IOException {
		FileInputFormat.addInputPath(job, new Path(inpath));
	}

	/**
	 * 设置HDFS 输出路径
	 * 
	 * @param job
	 *            任务
	 * @param outpath
	 *            输出hdfs路径
	 */
	public static void setOutputPath(Job job, String outpath) {
		FileOutputFormat.setOutputPath(job, new Path(outpath));
	}

}
