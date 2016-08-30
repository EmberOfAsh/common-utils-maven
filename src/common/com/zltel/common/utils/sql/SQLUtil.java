package com.zltel.common.utils.sql;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zltel.common.utils.reflect.BeanUtil;
import com.zltel.common.utils.string.StringUtil;

/**
 * SQL 工具类
 * 
 * @change 2016.6.23 初步完成
 * @author Wangch
 *
 */
public class SQLUtil {
	private static Map<String, TableInfoBean> _cacheTableInfo = null;

	/**
	 * 表信息
	 * 
	 * @author Wangch
	 *
	 */
	public static class TableInfoBean {
		private String tableName;
		private String tableComment;
		private List<ColumnBean> columnList = new ArrayList<ColumnBean>();
		private Map<String, ColumnBean> _columnMap = new HashMap<String, SQLUtil.ColumnBean>();

		/**
		 * 增加 列定义
		 * 
		 * @param columnBean
		 */
		public void addColumnBean(ColumnBean columnBean) {
			this.columnList.add(columnBean);
			_columnMap.put(columnBean.getColumnName(), columnBean);
		}

		/**
		 * 获取 表所有列字段
		 * 
		 * @return
		 */
		public java.util.Set<String> tableFieldNames() {
			return _columnMap.keySet();
		}

		public TableInfoBean() {
			super();
		}

		public TableInfoBean(String tableName, String tableComment) {
			super();
			this.tableName = tableName;
			this.tableComment = tableComment;
		}

		/**
		 * @return the tableName
		 */
		public final String getTableName() {
			return tableName;
		}

		/**
		 * @return the tableComment
		 */
		public final String getTableComment() {
			return tableComment;
		}

		/**
		 * @return the columnList
		 */
		public final List<ColumnBean> getColumnList() {
			return columnList;
		}

		/**
		 * @param tableName
		 *            the tableName to set
		 */
		public final void setTableName(String tableName) {
			this.tableName = tableName;
		}

		/**
		 * @param tableComment
		 *            the tableComment to set
		 */
		public final void setTableComment(String tableComment) {
			this.tableComment = tableComment;
		}

		/**
		 * @param columnList
		 *            the columnList to set
		 */
		public final void setColumnList(List<ColumnBean> columnList) {
			this.columnList = columnList;
		}

	}

	/**
	 * 表 列信息
	 * 
	 * @author Wangch
	 *
	 */
	public static class ColumnBean {
		private String columnName;
		private String columnComment;
		private String SqlType;

		public ColumnBean() {
			super();
		}

		public ColumnBean(String columnName, String columnComment, String sqlType) {
			super();
			this.columnName = columnName;
			this.columnComment = columnComment;
			SqlType = sqlType;
		}

		/**
		 * @return the columnName
		 */
		public final String getColumnName() {
			return columnName;
		}

		/**
		 * @return the columnComment
		 */
		public final String getColumnComment() {
			return columnComment;
		}

		/**
		 * @return the sqlType
		 */
		public final String getSqlType() {
			return SqlType;
		}

		/**
		 * @param columnName
		 *            the columnName to set
		 */
		public final void setColumnName(String columnName) {
			this.columnName = columnName;
		}

		/**
		 * @param columnComment
		 *            the columnComment to set
		 */
		public final void setColumnComment(String columnComment) {
			this.columnComment = columnComment;
		}

		/**
		 * @param sqlType
		 *            the sqlType to set
		 */
		public final void setSqlType(String sqlType) {
			SqlType = sqlType;
		}
	}

	public static String getSqlTypeName(int type) {
		switch (type) {
		case Types.BIT:
			return "BIT";
		case Types.TINYINT:
			return "TINYINT";
		case Types.SMALLINT:
			return "SMALLINT";
		case Types.INTEGER:
			return "INTEGER";
		case Types.BIGINT:
			return "BIGINT";
		case Types.FLOAT:
			return "FLOAT";
		case Types.REAL:
			return "REAL";
		case Types.DOUBLE:
			return "DOUBLE";
		case Types.NUMERIC:
			return "NUMERIC";
		case Types.DECIMAL:
			return "DECIMAL";
		case Types.CHAR:
			return "CHAR";
		case Types.VARCHAR:
			return "VARCHAR";
		case Types.LONGVARCHAR:
			return "LONGVARCHAR";
		case Types.DATE:
			return "DATE";
		case Types.TIME:
			return "TIME";
		case Types.TIMESTAMP:
			return "TIMESTAMP";
		case Types.BINARY:
			return "BINARY";
		case Types.VARBINARY:
			return "VARBINARY";
		case Types.LONGVARBINARY:
			return "LONGVARBINARY";
		case Types.NULL:
			return "NULL";
		case Types.OTHER:
			return "OTHER";
		case Types.JAVA_OBJECT:
			return "JAVA_OBJECT";
		case Types.DISTINCT:
			return "DISTINCT";
		case Types.STRUCT:
			return "STRUCT";
		case Types.ARRAY:
			return "ARRAY";
		case Types.BLOB:
			return "BLOB";
		case Types.CLOB:
			return "CLOB";
		case Types.REF:
			return "REF";
		case Types.DATALINK:
			return "DATALINK";
		case Types.BOOLEAN:
			return "BOOLEAN";
		case Types.ROWID:
			return "ROWID";
		case Types.NCHAR:
			return "NCHAR";
		case Types.NVARCHAR:
			return "NVARCHAR";
		case Types.LONGNVARCHAR:
			return "LONGNVARCHAR";
		case Types.NCLOB:
			return "NCLOB";
		case Types.SQLXML:
			return "SQLXML";
		}

		return "?";
	}

	/**
	 * 初始化 加载 table 信息，并加入缓存
	 * 
	 * @param conn
	 * @throws SQLException
	 */
	private static void initTableInfo(Connection conn) throws SQLException {
		if (null == _cacheTableInfo) {
			_cacheTableInfo = new HashMap<String, SQLUtil.TableInfoBean>();
			DatabaseMetaData databaseMetaData = conn.getMetaData();
			// 获取所有表
			ResultSet tableSet = databaseMetaData.getTables(null, "%", "%", new String[] { "TABLE" });
			while (tableSet.next()) {
				String tableName = tableSet.getString("TABLE_NAME").toUpperCase();
				String tableComment = tableSet.getString("REMARKS");
				TableInfoBean tfb = new TableInfoBean(tableName, tableComment);

				// 获取tableName表列信息
				ResultSet columnSet = databaseMetaData.getColumns(null, "%", tableName, "%");
				// 上面代码会得到两个结果集，对照DatabaseMetaData 的文档，我们可以通过结果集的列名来获取想要的信息，例如
				while (columnSet.next()) {
					String columnName = columnSet.getString("COLUMN_NAME");
					String columnComment = columnSet.getString("REMARKS");
					String sqlType = getSqlTypeName(columnSet.getInt("DATA_TYPE"));
					ColumnBean cb = new ColumnBean(columnName.toUpperCase(), columnComment, sqlType);
					tfb.addColumnBean(cb);
				}
				_cacheTableInfo.put(tableName, tfb);
			}
		}
	}

	/**
	 * 根据表明获取表结构
	 * 
	 * @param conn
	 * @param table
	 * @return
	 * @throws SQLException
	 */
	public static TableInfoBean readTableInfo(Connection conn, String table) throws SQLException {
		initTableInfo(conn);
		if (null != _cacheTableInfo && StringUtil.isNotNullAndEmpty(table)) {
			return _cacheTableInfo.get(table.toUpperCase());
		}
		return null;
	}

	/**
	 * 创建 Insert SQL语句
	 * 
	 * @param conn
	 *            连接
	 * @param tableName
	 *            插入表名
	 * @param v
	 *            保存对象
	 * @param params
	 *            返回参数引用list
	 * @return Sql语句
	 * @throws SQLException
	 */
	public static <T> String createInsertSql(Connection conn, String tableName, T v, List<Object> params)
			throws SQLException {
		Map<String, Object> tomap = BeanUtil.ConvertBeanToMap(v);
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO ").append(tableName);
		List<String> fields = new ArrayList<String>();
		TableInfoBean tfb = readTableInfo(conn, tableName);
		if (tfb == null) {
			throw new SQLException(tableName + " 不存在");
		}
		// 只有在 table 中定义的数据 才保存
		for (String fn : tfb.tableFieldNames()) {
			Object _v = tomap.get(fn.toUpperCase());
			if (_v != null) {
				fields.add(fn);
				params.add(_v);
			}
		}
		StringBuffer sb_f = new StringBuffer();
		StringBuffer sb_v = new StringBuffer();
		sb_f.append("(");
		sb_v.append("(");
		boolean isf = true;
		for (String f : fields) {
			if (isf) {
				isf = false;
			} else {
				sb_f.append(",");
				sb_v.append(",");
			}
			//
			sb_f.append(f);
			sb_v.append(" ? ");
		}
		sb_f.append(")");
		sb_v.append(")");
		sql.append(sb_f.toString()).append(" VALUES ").append(sb_v.toString());
		return sql.toString();
	}

	/**
	 * 创建 UPDATE SQL 语句
	 * 
	 * @param conn
	 * @param tableName
	 * @param v
	 * @param Map<String,
	 *            Object> where WHERE 条件，无 则为空
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public static <T> String createUpdateSql(Connection conn, String tableName, T v, Map<String, Object> where,
			List<Object> params) throws SQLException {
		Map<String, Object> tomap = BeanUtil.ConvertBeanToMap(v);
		StringBuffer sql = new StringBuffer();
		sql.append(" UPDATE ").append(tableName).append(" SET ");
		List<String> fields = new ArrayList<String>();
		TableInfoBean tfb = readTableInfo(conn, tableName);
		if (tfb == null) {
			throw new SQLException(tableName + " 不存在");
		}
		// 只有在 table 中定义的数据 才保存
		for (String fn : tfb.tableFieldNames()) {
			Object _v = tomap.get(fn.toUpperCase());
			if (_v != null) {
				fields.add(fn);
				params.add(_v);
			}
		}
		StringBuffer sb_f = new StringBuffer();
		sb_f.append(" ");
		boolean isf = true;
		for (String f : fields) {
			if (isf) {
				isf = false;
			} else {
				sb_f.append(",");
			}
			//
			sb_f.append(f).append("=").append("?");
		}
		sb_f.append(" ");
		sql.append(sb_f.toString()).append(" WHERE 1=1 ");
		calcWhere(tfb, sql, where, params);

		return sql.toString();
	}

	/**
	 * 创建 DELETE 语句
	 * 
	 * @param conn
	 * @param tableName
	 * @param where
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public static <T> String createDeleteSql(Connection conn, String tableName, Map<String, Object> where,
			List<Object> params) throws SQLException {
		StringBuffer sql = new StringBuffer();
		sql.append(" DELETE FROM ").append(tableName).append(" WHERE 1=1");
		TableInfoBean tfb = readTableInfo(conn, tableName);
		if (tfb == null) {
			throw new SQLException(tableName + " 不存在");
		}
		calcWhere(tfb, sql, where, params);
		return sql.toString();
	}

	/**
	 * 创建 SELECT 查询语句
	 * 
	 * @param conn
	 * @param tableName
	 * @param v
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public static <T> String createSelectSql(Connection conn, String tableName, T v, List<Object> params)
			throws SQLException {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT * FROM ").append(tableName).append(" WHERE 1=1");
		TableInfoBean tfb = readTableInfo(conn, tableName);
		if (tfb == null) {
			throw new SQLException(tableName + " 不存在");
		}
		Map<String, Object> tomap = BeanUtil.ConvertBeanToMap(v);
		calcWhere(tfb, sql, tomap, params);
		return sql.toString();
	}

	/**
	 * 拼接 Where 条件
	 * 
	 * @param sql
	 * @param where
	 * @param params
	 */
	private static void calcWhere(TableInfoBean tfb, StringBuffer sql, Map<String, Object> where, List<Object> params) {
		if (where != null && !where.isEmpty()) {
			java.util.Set<String> fns = tfb.tableFieldNames();
			for (Map.Entry<String, Object> entry : where.entrySet()) {
				String _k = entry.getKey();
				Object _v = entry.getValue();
				if (fns.contains(_k.toUpperCase().trim())) {
					sql.append(" AND ").append(_k).append(" = ?");
					params.add(_v);
				}
			}
		}
	}

}
