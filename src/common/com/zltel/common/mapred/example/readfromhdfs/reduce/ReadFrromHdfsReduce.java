package com.zltel.common.mapred.example.readfromhdfs.reduce;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/**
 * 处理数据，并将结果保存输出
 * 
 * @author Wangch
 *
 */
public class ReadFrromHdfsReduce extends Reducer<Text, Text, Text, Text> {

}
