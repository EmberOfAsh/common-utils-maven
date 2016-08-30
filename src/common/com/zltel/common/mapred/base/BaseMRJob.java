package com.zltel.common.mapred.base;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.RawComparator;
import org.apache.hadoop.io.compress.GzipCodec;
import org.apache.hadoop.mapreduce.InputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.OutputFormat;
import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * 基础任务MR 配置项设置
 * 
 * @author Wangch
 *
 */
public class BaseMRJob {
	/**
	 * 创建 配置文件
	 * 
	 * @return
	 */
	public static Configuration createConfiguration() {
		return new Configuration();
	}

	/**
	 * 创建 Job定义
	 * 
	 * @param _conf
	 * @param jobname
	 * @return
	 * @throws IOException
	 */
	public static Job createJob(Configuration _conf, String jobname) throws IOException {
		return Job.getInstance(_conf, jobname);
	}

	/**
	 * 设置 Job Reduce数量
	 * 
	 * @param job
	 * @param reduceCount
	 */
	public static void setReduceCount(Job job, int reduceCount) {
		job.setNumReduceTasks(reduceCount);
	}

	/**
	 * 设置 类从哪一个jar来,防止招不到
	 * 
	 * @param job
	 * @param c
	 */
	public static void setJarByClass(Job job, Class<?> c) {
		job.setJarByClass(c);
	}

	/**
	 * 设置分区类，此类决定数据将交由哪一个Reduce进行处理
	 * 
	 * @param job
	 * @param cls
	 */
	@SuppressWarnings("rawtypes")
	public static void setPartitionerClass(Job job, Class<? extends Partitioner> cls) {
		job.setPartitionerClass(cls);
	}

	/**
	 * 设置 自定义分组类，包含相同条件的数据将会被合并
	 * 
	 * @param job
	 * @param cls
	 */
	@SuppressWarnings("rawtypes")
	public static void setGroupingComparatorClass(Job job, Class<? extends RawComparator> cls) {
		job.setGroupingComparatorClass(cls);
	}

	/**
	 * 设置自定义排序类
	 * 
	 * @param job
	 * @param cls
	 */
	@SuppressWarnings("rawtypes")
	public static void setSortComparatorClass(Job job, Class<? extends RawComparator> cls) {
		job.setSortComparatorClass(cls);
	}

	/**
	 * 设置 Map处理类
	 * 
	 * @param job
	 * @param mapClass
	 */
	@SuppressWarnings("rawtypes")
	public static void setMapperClass(Job job, Class<? extends Mapper> mapClass) {
		job.setMapperClass(mapClass);
	}

	/**
	 * 设置 Map输出Key类型
	 * 
	 * @param job
	 * @param cls
	 */
	public static void setMapOutputKeyClass(Job job, Class<?> cls) {
		job.setMapOutputKeyClass(cls);
	}

	/**
	 * 设置Map输出Value类型
	 * 
	 * @param job
	 * @param cls
	 */
	public static void setMapOutputValueClass(Job job, Class<?> cls) {
		job.setMapOutputValueClass(cls);
	}

	/**
	 * 设置Reduce处理类
	 * 
	 * @param job
	 * @param cls
	 */
	@SuppressWarnings("rawtypes")
	public static void setReducerClass(Job job, Class<? extends Reducer> cls) {
		job.setReducerClass(cls);
	}

	/**
	 * 设置Reduce 输出Key类型
	 * 
	 * @param job
	 * @param cls
	 */
	public static void setOutputKeyClass(Job job, Class<?> cls) {
		job.setOutputKeyClass(cls);
	}

	/**
	 * 设置Reduce 输出Value类型
	 * 
	 * @param job
	 * @param cls
	 */
	public static void setOutputValueClass(Job job, Class<?> cls) {
		job.setOutputValueClass(cls);
	}

	/**
	 * 将输入的数据集分割成小数据块splites，同时提供一个RecordReder的实现
	 * 
	 * @param job
	 * @param cls
	 */
	@SuppressWarnings("rawtypes")
	public static void setInputFormatClass(Job job, Class<? extends InputFormat> cls) {
		job.setInputFormatClass(cls);
	}

	/**
	 * 提供一个RecordWriter的实现，负责数据输出
	 * 
	 * @param job
	 * @param cls
	 */
	@SuppressWarnings("rawtypes")
	public static void setOutputFormatClass(Job job, Class<? extends OutputFormat> cls) {
		job.setOutputFormatClass(cls);
	}

	/**
	 * 设置保存结果 采用 GZip格式压缩
	 * 
	 * @param job
	 */
	public static void setCompressOutputByGZip(Job job) {
		// 设置保存结果压缩
		FileOutputFormat.setCompressOutput(job, true);
		FileOutputFormat.setOutputCompressorClass(job, GzipCodec.class);
	}

}
