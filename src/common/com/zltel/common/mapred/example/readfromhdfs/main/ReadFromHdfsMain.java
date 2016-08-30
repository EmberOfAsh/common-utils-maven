package com.zltel.common.mapred.example.readfromhdfs.main;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.compress.GzipCodec;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import com.zltel.common.mapred.example.readfromhdfs.map.ReadFrromHdfsMap;
import com.zltel.common.mapred.example.readfromhdfs.reduce.ReadFrromHdfsReduce;

/**
 * 本例子 主要作用是从HDFS中读取数据，并将结果写入HDFS的过程
 * 
 * @author Wangch
 *
 */
public class ReadFromHdfsMain {

	public static void main(String[] args) throws Exception {
		Configuration _conf = new Configuration();

		Job job = Job.getInstance(_conf, "从HDFS存取数据");
		// 设置HDFS输入路径
		FileInputFormat.addInputPath(job, new Path("in/"));
		// 设置HDFS输出结果
		FileOutputFormat.setOutputPath(job, new Path("out/"));

		job.setJarByClass(ReadFromHdfsMain.class);
		// 设置Map/Reduce 处理类
		job.setMapperClass(ReadFrromHdfsMap.class);
		job.setReducerClass(ReadFrromHdfsReduce.class);

		// map 输出Key的类型
		job.setMapOutputKeyClass(Text.class);
		// map输出Value的类型
		job.setMapOutputValueClass(Text.class);
		// rduce输出Key的类型，是Text，因为使用的OutputFormatClass是TextOutputFormat
		job.setOutputKeyClass(Text.class);
		// rduce输出Value的类型
		job.setOutputValueClass(Text.class);

		// 将输入的数据集分割成小数据块splites，同时提供一个RecordReder的实现。
		job.setInputFormatClass(TextInputFormat.class);
		// 提供一个RecordWriter的实现，负责数据输出。
		job.setOutputFormatClass(TextOutputFormat.class);

		// 设置保存结果压缩
		FileOutputFormat.setCompressOutput(job, true);
		FileOutputFormat.setOutputCompressorClass(job, GzipCodec.class);

		// 设置reduce数量
		job.setNumReduceTasks(70);

		job.waitForCompletion(true);
	}
}
