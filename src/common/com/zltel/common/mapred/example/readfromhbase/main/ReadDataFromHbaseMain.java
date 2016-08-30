package com.zltel.common.mapred.example.readfromhbase.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;

import com.zltel.common.mapred.example.readfromhbase.map.ReadDataFromHbaseMap;
import com.zltel.common.mapred.example.readfromhbase.reduce.ReadDataFromHbaseReduce;

/**
 * 本例子 主要作用是从HBase中读取数据，并将结果写入HBase的过程
 * 
 * @author Wangch
 *
 */
public class ReadDataFromHbaseMain {
	private static final String columnFamily = "info";
	public static final String writeTable = "userlife";

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		// 在配置文件设置 保存数据表的属性
		conf.set("conf.columnfamily", columnFamily);
		conf.set("writeTable", writeTable);

		Job job = Job.getInstance(conf, "从HBase读取数据");
		job.setJarByClass(ReadDataFromHbaseMain.class);

		TableMapReduceUtil.addDependencyJars(job);
		// 加载多张表的数据
		List<Scan> scans = createScans(Arrays.asList("table1", "table2"));
		TableMapReduceUtil.initTableMapperJob(scans, ReadDataFromHbaseMap.class, Text.class, Text.class, job);

		// 加载 数据输出到 HBase，这里有两种方法可以做到
		// 方法1，通过 制定 写入表
		TableMapReduceUtil.initTableReducerJob(writeTable, ReadDataFromHbaseReduce.class, job);

		// 方法2，设定Reduce类，然后再Reduce类中，手动构建保存表和数据，此方法 可以处理将数据保存到多张表的功能
		// job.setReducerClass(ReadDataFromHbaseReduce.class);

		job.setNumReduceTasks(1);

		System.exit(job.waitForCompletion(true) ? 0 : 1);

	}

	/**
	 * 如果有多张表输入数据，通过此过程 创建Scan
	 * 
	 * @param tables
	 * @return
	 */
	public static List<Scan> createScans(List<String> tables) {
		Scan scan = null;
		List<Scan> scans = new ArrayList<Scan>();

		for (String table : tables) {
			scan = new Scan();
			scan.setCaching(500);
			scan.setCacheBlocks(false);
			scan.setAttribute(Scan.SCAN_ATTRIBUTES_TABLE_NAME, table.getBytes());
			scans.add(scan);
		}

		return scans;
	}
}
