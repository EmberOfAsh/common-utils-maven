package com.zltel.common.mapred.example.customize.part;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 自定义 二次排序 的key 值 <br>
 * firstKey imsi <br>
 * secondKey stime的时间戳
 * 
 * @author Wangch
 *
 */
public class DoubleSortKey implements WritableComparable<DoubleSortKey> {
	private static final Logger logger = LoggerFactory.getLogger(DoubleSortKey.class);
	private Text firstKey;
	private LongWritable secondKey;

	public DoubleSortKey() {
		this.firstKey = new Text();
		this.secondKey = new LongWritable();
	}

	public Text getFirstKey() {
		return this.firstKey;
	}

	public void setFirstKey(Text firstKey) {
		this.firstKey = firstKey;
	}

	public LongWritable getSecondKey() {
		return this.secondKey;
	}

	public void setSecondKey(LongWritable secondKey) {
		this.secondKey = secondKey;
	}

	public void readFields(DataInput dateInput) throws IOException {
		this.firstKey.readFields(dateInput);
		this.secondKey.readFields(dateInput);
	}

	public void write(DataOutput outPut) throws IOException {
		this.firstKey.write(outPut);
		this.secondKey.write(outPut);
	}

	/**
	 * 自定义比较策略 注意：该比较策略用于mapreduce的第一次默认排序，也就是发生在map阶段的sort小阶段，
	 * 发生地点为环形缓冲区(可以通过io.sort.mb进行大小调整)
	 */
	public int compareTo(DoubleSortKey combinationKey) {
		// logger.debug("-------CombinationKey flag-------");
		int fc = this.firstKey.compareTo(combinationKey.getFirstKey());
		int sc = this.secondKey.compareTo(combinationKey.getSecondKey());
		return fc == 0 ? sc : fc;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DoubleSortKey [firstKey=");
		builder.append(firstKey);
		builder.append(", secondKey=");
		builder.append(secondKey);
		builder.append("]");
		return builder.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((firstKey == null) ? 0 : firstKey.hashCode());
		result = prime * result + ((secondKey == null) ? 0 : secondKey.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DoubleSortKey other = (DoubleSortKey) obj;
		if (firstKey == null) {
			if (other.firstKey != null)
				return false;
		} else if (!firstKey.equals(other.firstKey))
			return false;
		if (secondKey == null) {
			if (other.secondKey != null)
				return false;
		} else if (!secondKey.equals(other.secondKey))
			return false;
		return true;
	}

}
