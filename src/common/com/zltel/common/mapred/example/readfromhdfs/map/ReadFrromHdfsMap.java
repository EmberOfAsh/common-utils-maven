package com.zltel.common.mapred.example.readfromhdfs.map;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * HDFS文件切片后按行分割后的处理
 * 
 * @author Wangch
 *
 */
public class ReadFrromHdfsMap extends Mapper<LongWritable, Text, Text, Text> {

}
