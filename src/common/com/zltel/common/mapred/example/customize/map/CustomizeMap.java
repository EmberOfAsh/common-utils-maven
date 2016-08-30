package com.zltel.common.mapred.example.customize.map;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import com.zltel.common.mapred.example.customize.part.DoubleSortKey;

public class CustomizeMap extends Mapper<LongWritable, Text, DoubleSortKey, Text> {

}
