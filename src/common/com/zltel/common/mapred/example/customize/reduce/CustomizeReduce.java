package com.zltel.common.mapred.example.customize.reduce;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import com.zltel.common.mapred.example.customize.part.DoubleSortKey;

public class CustomizeReduce extends Reducer<DoubleSortKey, Text, Text, Text> {

}
