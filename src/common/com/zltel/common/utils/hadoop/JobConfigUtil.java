package com.zltel.common.utils.hadoop;

/**
 * Hadoop MR job conf 设置工具类
 * 
 * @author Wangch
 *
 */
public class JobConfigUtil {
	/** Map过程最大允许失败率(成功/总次数)，超过 则任务失败 **/
	public static final String MAPRED_MAX_MAP_FAILURES_PERCENT = "mapreduce.map.failures.maxpercent";
	/** Reduce过程最大允许失败率(成功/总次数)，超过 则任务失败 **/
	public static final String MAPRED_MAX_REDUCE_FAILURES_PERCENT = "mapreduce.reduce.failures.maxpercent";
	/** Map/Reduce 任务执行最大超时时间 **/
	public static final String MAPRED_TASK_TIMEOUT = "mapreduce.task.timeout";
	/** 开启Map/Reduce中间结果压缩 **/
	public static final String MAPREDUCE_MAP_OUTPUT_COMPRESS = "mapreduce.map.output.compress";
	/** Map/Reduce中间结果压缩算法 **/
	public static final String MAPRED_MAP_OUTPUT_COMPRESS_CODEC = "mapred.map.output.compress.codec";

	/** 压缩算法 Snappy **/
	public static final String COMPRESS_SNAPPYCODEC = "org.apache.hadoop.io.compress.SnappyCodec";

}
