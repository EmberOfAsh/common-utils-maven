package com.zltel.common.utils.hbase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.NamespaceDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.RegexStringComparator;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.filter.SubstringComparator;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * HBase 工具类
 * 
 * @author Wangch
 * 
 */
public class HBaseUtil {

	/**
	 * 通过config 创建一个 hbase连接
	 * 
	 * @param config
	 *            配置参数
	 * @return hbase连接
	 * @throws IOException
	 * @notice 请注意，使用完成后一定要 关闭 连接！！！
	 */
	public static final Connection getConnection(Configuration config) throws IOException {
		return ConnectionFactory.createConnection(config);
	}

	/**
	 * 获取 管理对象
	 * 
	 * @param con
	 *            连接
	 * @return admin对象
	 * @throws IOException
	 */
	public static final Admin getAdmin(Connection con) throws IOException {
		return con.getAdmin();
	}

	/**
	 * 获取 Table对象
	 * 
	 * @notice 建议在获取之前 判断下表是否存在
	 * @param con
	 *            连接
	 * @param tn
	 *            表名
	 * @return table对象
	 * @throws IOException
	 */
	public static final Table getTable(Connection con, String tn) throws IOException {
		TableName _tableName = TableName.valueOf(tn);
		Table table = con.getTable(_tableName);
		return table;
	}

	/**
	 * 创建hbase表空间
	 * 
	 * @param config
	 *            hbase连接配置
	 * @param namespace
	 *            表空间名称
	 * @return 是否创建成功
	 * @throws IOException
	 */
	public static final boolean createNameSpace(Configuration config, String namespace) throws IOException {
		Connection con = null;
		Admin admin = null;
		try {
			con = getConnection(config);
			admin = getAdmin(con);
			// create namespace named "my_ns"
			admin.createNamespace(NamespaceDescriptor.create(namespace).build());
		} finally {
			closeAdmin(admin);
			close(con);
		}
		return true;
	}

	/**
	 * 删除 hbase表
	 * 
	 * @param config
	 *            连接配置
	 * @param tableName
	 *            表名
	 * @return 是否产出成功（没有异常默认为true）
	 * @throws IOException
	 *             抛出异常
	 */
	public static final boolean deleteTable(Configuration config, String tableName) throws IOException {
		Connection con = null;
		Admin admin = null;
		try {
			con = getConnection(config);
			admin = con.getAdmin();
			TableName tn = TableName.valueOf(tableName);
			admin.disableTable(tn);
			admin.deleteTable(tn);
		} finally {
			closeAdmin(admin);
			close(con);
		}
		return true;
	}

	/**
	 * 判断表 是否存在
	 * 
	 * @param config
	 *            hbase连接配置
	 * @param tableName
	 *            表名
	 * @return hbase表是否存在
	 * @throws IOException
	 */
	public static final boolean isTableExists(Configuration config, String tableName) throws IOException {
		Connection con = null;
		Admin admin = null;
		try {
			con = getConnection(config);
			admin = con.getAdmin();
			TableName tn = TableName.valueOf(tableName);
			return admin.tableExists(tn);
		} finally {
			closeAdmin(admin);
			close(con);
		}
	}

	/**
	 * 判断表 是否存在
	 * 
	 * @param con
	 *            连接
	 * @param tableName
	 *            表
	 * @return
	 * @throws IOException
	 */
	public static final boolean isTableExists(Connection con, String tableName) throws IOException {
		Admin admin = null;
		try {
			admin = con.getAdmin();
			TableName tn = TableName.valueOf(tableName);
			return admin.tableExists(tn);
		} finally {
			closeAdmin(admin);
		}
	}

	/**
	 * 创建 put对象
	 * 
	 * @param rowkey
	 * @param datas
	 *            插入数据
	 * @return put对象
	 */
	public static final Put createPut(String rowkey, List<ColumnData> datas) {
		Put put = new Put(Bytes.toBytes(rowkey));
		for (ColumnData pd : datas) {
			put.addColumn(Bytes.toBytes(pd.getFamily()), Bytes.toBytes(pd.getQualifier()),
					Bytes.toBytes(pd.getValue()));
		}
		return put;
	}

	/**
	 * 创建 delete对象
	 * 
	 * @param rowkey
	 *            根据rowkey删除数据
	 * @return
	 */
	public static final Delete createDelete(String rowkey) {
		Delete delete = new Delete(Bytes.toBytes(rowkey));
		return delete;
	}

	/**
	 * @param rowkey
	 * @param columns
	 * @return
	 */
	public static final Delete createDelete(String rowkey, List<ColumnData> columns) {
		Delete delete = createDelete(rowkey);
		if (columns != null && columns.size() > 0) {
			for (ColumnData pd : columns) {
				// addColumns 删除所有数据,addColumn 删除最新版本数据
				delete.addColumns(Bytes.toBytes(pd.getFamily()), Bytes.toBytes(pd.getQualifier()));
			}
		}
		return delete;
	}

	/**
	 * 创建 get对象
	 * 
	 * @param rowkey
	 * @return
	 */
	public static final Get createGet(String rowkey) {
		Get get = new Get(Bytes.toBytes(rowkey));
		return get;
	}

	/**
	 * 创建 开始-结束 rowkey 扫描
	 * 
	 * @param startRow
	 *            开始rowkey
	 * @param endRow
	 *            结束rowkey
	 * @return scan对象
	 */
	public static final Scan createScan(String startRow, String endRow) {
		Scan scan = new Scan(Bytes.toBytes(startRow), Bytes.toBytes(endRow));
		return scan;
	}

	public static final Scan createScan(Filter filter) {
		Scan scan = new Scan();
		scan.setFilter(filter);
		return scan;
	}

	/**
	 * 创建 rowkey 正则过滤器
	 * 
	 * <pre>
	 * Filter filter3 = new RowFilter(CompareFilter.CompareOp.EQUAL, new SubstringComparator(&quot;5&quot;));
	 * scan.setFilter(filter3);
	 * ResultScanner scanner3 = table.getScanner(scan);
	 * for (Result res : scanner3) {
	 * 	System.out.println(res);
	 * }
	 * scanner3.close();
	 * </pre>
	 * 
	 * @param regexStr
	 *            正则字符串
	 * @return 过滤器
	 */
	public static final Filter createRegexFilter(String regexStr) {
		Filter filter = new RowFilter(CompareFilter.CompareOp.EQUAL, new RegexStringComparator(regexStr));
		return filter;
	}

	/**
	 * 创建rowkey 包含过滤器(调用contain比较)
	 * 
	 * @param containStr
	 *            需要包含的字符串
	 * @return 过滤器
	 */
	public static final Filter createContainFilter(String containStr) {
		Filter filter = new RowFilter(CompareFilter.CompareOp.EQUAL, new SubstringComparator(containStr));
		return filter;
	}

	/**
	 * 插入 数据
	 * 
	 * @param table
	 *            表对象
	 * @param rowkey
	 * @param datas
	 * @throws IOException
	 */
	public static final void insertData(Table table, String rowkey, List<ColumnData> datas) throws IOException {
		Put put = createPut(rowkey, datas);
		table.put(put);
	}

	/**
	 * 删除表数据
	 * 
	 * @param table
	 *            表对象
	 * @param rowkey
	 *            rowkey
	 * @throws IOException
	 */
	public static final void deleteData(Table table, String rowkey) throws IOException {
		Delete delete = createDelete(rowkey);
		table.delete(delete);
	}

	/**
	 * 根据rowkey 获取数据
	 * 
	 * @param table
	 * @param rowkey
	 * @return
	 * @throws IOException
	 */
	public static final Result getByRowkey(Table table, String rowkey) throws IOException {
		Get get = createGet(rowkey);
		return table.get(get);
	}

	/**
	 * 根据 start-end rowkey扫描
	 * 
	 * @param table
	 * @param startRow
	 *            开始rowkey
	 * @param endRow
	 *            结束rowkey
	 * @return ResultScanner
	 * @throws IOException
	 */
	public static final ResultScanner getByStartEndRowkey(Table table, String startRow, String endRow)
			throws IOException {
		Scan scan = createScan(startRow, endRow);
		return table.getScanner(scan);
	}

	// --------------------------- 调用/遍历 数据 ------------------------------
	/**
	 * 读取 result 对象
	 * 
	 * @param result
	 * @return
	 */
	public static final RowData readResult(Result result) {
		RowData rd = new RowData();
		List<ColumnData> cds = new ArrayList<ColumnData>();
		for (Cell rowKV : result.rawCells()) {
			String f = new String(CellUtil.cloneFamily(rowKV));// family
			String q = new String(CellUtil.cloneQualifier(rowKV));// qualifer
			String v = new String(CellUtil.cloneValue(rowKV)); // 值
			String rowkey = new String(CellUtil.cloneRow(rowKV));
			rd.setRowkey(rowkey);
			cds.add(new ColumnData(f, q, v));
		}
		rd.setColumnDatas(cds);
		return rd;
	}

	/**
	 * 读取 多个result对象
	 * 
	 * @param rs
	 * @return
	 */
	public static final List<RowData> readResultScanner(ResultScanner rs) {
		List<RowData> list = new ArrayList<HBaseUtil.RowData>();
		for (Result result : rs) {
			list.add(readResult(result));
		}
		return list;
	}

	// --------------------------- close -----------------------------

	/**
	 * 关闭hbase 连接
	 * 
	 * @param con
	 *            hbase连接
	 */
	public static final void close(Connection con) {
		if (null != con) {
			try {
				con.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 关闭hbase管理对象
	 * 
	 * @param hadmin
	 */
	public static final void closeAdmin(Admin hadmin) {
		if (null != hadmin) {
			try {
				hadmin.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static final void closeTable(Table htable) {
		if (htable != null) {
			try {
				htable.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void closeResultScanner(ResultScanner rs) {
		if (rs != null) {
			rs.close();
		}
	}

	// --------------------------- close -----------------------------

	/**
	 * hbase 一行数据
	 * 
	 * @author Wangch
	 * 
	 */
	public static final class RowData {
		private String rowkey;
		private List<ColumnData> columnDatas;

		private Map<String, Map<String, ColumnData>> _cacheMap;

		/**
		 * @return the rowkey
		 */
		public final String getRowkey() {
			return rowkey;
		}

		/**
		 * @return the columnDatas
		 */
		public final List<ColumnData> getColumnDatas() {
			return columnDatas;
		}

		/**
		 * @param rowkey
		 *            the rowkey to set
		 */
		public final void setRowkey(String rowkey) {
			this.rowkey = rowkey;
		}

		/**
		 * @param columnDatas
		 *            the columnDatas to set
		 */
		public final void setColumnDatas(List<ColumnData> columnDatas) {
			this.columnDatas = columnDatas;
			// this.initCache();
		}

		/**
		 * 生成 内部缓存结构,提供快速调用服务
		 */
		private void initCache() {
			_cacheMap = new HashMap<String, Map<String, ColumnData>>();
			if (columnDatas != null && columnDatas.size() > 0) {
				for (ColumnData cd : this.columnDatas) {
					Map<String, ColumnData> fmap = _cacheMap.get(cd.getFamily());
					if (fmap == null) {
						fmap = new HashMap<String, ColumnData>();
					}
					fmap.put(cd.getQualifier(), cd);
					_cacheMap.put(cd.getFamily(), fmap);
				}
			}
		}

		/**
		 * 根据 列簇 获取数据
		 * 
		 * @param family
		 *            列簇名
		 * @return map数据
		 */
		public Map<String, ColumnData> get(String family) {
			if (_cacheMap == null) {
				this.initCache();
			}
			return _cacheMap.get(family);
		}

		/**
		 * 根据列簇 ，列定义 获取数据
		 * 
		 * @param family
		 *            列簇
		 * @param qualifier
		 *            列定义
		 * @return
		 */
		public ColumnData get(String family, String qualifier) {
			Map<String, ColumnData> fm = get(family);
			if (fm != null) {
				return fm.get(qualifier);
			}
			return null;

		}
	}

	/**
	 * 列数据 模型
	 * 
	 * @author Wangch
	 */
	public static final class ColumnData {
		/** 列簇 **/
		private String family;
		/** 列定义 **/
		private String qualifier;
		/** 值 **/
		private String value;

		public ColumnData() {
			super();
		}

		public ColumnData(String family, String qualifier, String value) {
			super();
			this.family = family;
			this.qualifier = qualifier;
			this.value = value;
		}

		/**
		 * @return the 列簇
		 */
		public final String getFamily() {
			return family;
		}

		/**
		 * @return the 列定义
		 */
		public final String getQualifier() {
			return qualifier;
		}

		/**
		 * @return the 值
		 */
		public final String getValue() {
			return value;
		}

		/**
		 * @param 列簇
		 *            the family to set
		 */
		public final void setFamily(String family) {
			this.family = family;
		}

		/**
		 * @param 列定义
		 *            the qualifier to set
		 */
		public final void setQualifier(String qualifier) {
			this.qualifier = qualifier;
		}

		/**
		 * @param 值
		 *            the value to set
		 */
		public final void setValue(String value) {
			this.value = value;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("ColumnData [family=");
			builder.append(family);
			builder.append(", qualifier=");
			builder.append(qualifier);
			builder.append(", value=");
			builder.append(value);
			builder.append("]");
			return builder.toString();
		}

	}

}
