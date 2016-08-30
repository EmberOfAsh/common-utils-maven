package com.zltel.common.mapred.base;

import java.io.IOException;
import java.util.List;

import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.mapreduce.Job;

/**
 * HBase读写 配置项设置
 * 
 * @author Wangch
 *
 */
public class HBaseMRJob extends HdfsMRJob {
	/**
	 * 指定 HBase Map处理类，以及多表输入
	 * 
	 * @param job
	 * @param scans
	 *            多表输入的 Scan数组
	 * @param mapper
	 *            map处理类
	 * @param outputKeyClass
	 *            map key输出类型
	 * @param outputValueClass
	 *            map value输出类型
	 * @throws IOException
	 */
	@SuppressWarnings("rawtypes")
	public static void initTableMapperJob(Job job, List<Scan> scans, Class<? extends TableMapper> mapper,
			Class<?> outputKeyClass, Class<?> outputValueClass) throws IOException {
		TableMapReduceUtil.initTableMapperJob(scans, mapper, outputKeyClass, outputValueClass, job);
	}

	/**
	 * 设定 Hbase Reduce输出类
	 * 
	 * @param job
	 * @param cls
	 *            Reduce类
	 * @param writeTable
	 *            输出表名
	 * @throws IOException
	 */
	@SuppressWarnings("rawtypes")
	public static void in(Job job, Class<? extends TableReducer> cls, String writeTable) throws IOException {
		TableMapReduceUtil.initTableReducerJob(writeTable, cls, job);
	}

	/**
	 * 创建 多表输入的 Scan
	 * 
	 * @param tn
	 *            输入表名
	 * @return
	 */
	public static Scan createScan(String tn) {
		Scan scan = new Scan();
		scan.setCaching(500);
		scan.setCacheBlocks(false);
		scan.setAttribute(Scan.SCAN_ATTRIBUTES_TABLE_NAME, tn.getBytes());
		return scan;
	}
}
