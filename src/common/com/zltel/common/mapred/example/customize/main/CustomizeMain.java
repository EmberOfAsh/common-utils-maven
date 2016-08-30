package com.zltel.common.mapred.example.customize.main;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.compress.GzipCodec;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import com.zltel.common.mapred.example.customize.map.CustomizeMap;
import com.zltel.common.mapred.example.customize.part.DoubleSortKey;
import com.zltel.common.mapred.example.customize.part.GroupingComparator;
import com.zltel.common.mapred.example.customize.part.MyComparator;
import com.zltel.common.mapred.example.customize.part.MyPartitioner;
import com.zltel.common.mapred.example.customize.reduce.CustomizeReduce;

/**
 * 本例子定义了自定义 MapReduce各个过程的过程
 * 
 * @author Wangch
 *
 */
public class CustomizeMain {

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		Configuration _conf = new Configuration();

		Job job = Job.getInstance(_conf, "自定义属性");
		// 设置输入路径
		FileInputFormat.addInputPath(job, new Path("in/"));

		job.setJarByClass(CustomizeMain.class);
		// 设置Map/Reduce 处理类
		job.setMapperClass(CustomizeMap.class);
		job.setReducerClass(CustomizeReduce.class);
		// 分区函数
		job.setPartitionerClass(MyPartitioner.class);
		// 分组函数( 根据此 方法确定 相同的 key进行 value分组)
		job.setGroupingComparatorClass(GroupingComparator.class);
		// 设定自定义排序函数
		job.setSortComparatorClass(MyComparator.class);
		// map 输出Key的类型
		job.setMapOutputKeyClass(DoubleSortKey.class);
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

		// 设置输出结果
		FileOutputFormat.setOutputPath(job, new Path("out/"));
		// 设置reduce数量
		job.setNumReduceTasks(70);

		job.waitForCompletion(true);
	}
}
