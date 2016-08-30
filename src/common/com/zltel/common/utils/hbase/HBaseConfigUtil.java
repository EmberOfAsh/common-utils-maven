package com.zltel.common.utils.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;

public class HBaseConfigUtil {
	/** ZooKeeper客户端口 **/
	public static final String ZOOKEEPER_CLIENT_PORT = "2181";

	public static Configuration createConfig() {
		Configuration configuration = HBaseConfiguration.create();
		return configuration;
	}

	public static Configuration createConfig(String hdfs, String hbase, String zookeeper) {
		Configuration configuration = createConfig();
		configuration.set("hbase.zookeeper.quorum", zookeeper);
		configuration.set("fs.defaultFS", hdfs);
		configuration.set("hbase.zookeeper.property.clientPort", ZOOKEEPER_CLIENT_PORT);
		configuration.set("hbase.master", hbase);
		return configuration;
	}

}
