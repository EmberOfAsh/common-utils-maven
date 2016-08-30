package com.zltel.common.utils.sql;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zltel.common.utils.reflect.BeanUtil;

public class BaseJdbcUtil {
	/** Oracle 连接字符串 **/
	public static final String oracle_url = "jdbc:oracle:thin:@${jdbc.database.ip}:${jdbc.database.port}:${jdbc.database.instance}";
	/** Oracle 驱动类 **/
	public static final String oracle_drive_name = "oracle.jdbc.OracleDriver";

	/** Oracle 连接字符串 **/
	public static final String mysql_url = "jdbc:mysql://${jdbc2.mysql.ip}:${jdbc2.mysql.port}/${jdbc2.mysql.instance}";
	/** Oracle 驱动类 **/
	public static final String mysql_drive_name = "com.mysql.jdbc.Driver";

	private static final Log log = LogFactory.getLog(BaseJdbcUtil.class);

	private static final String SQL_INSERT = "insert";
	private static final String SQL_UPDATE = "update";
	private static final String SQL_DELETE = "delete";

	/**
	 * 获取 Connection连接
	 * 
	 * @param driveName
	 *            驱动名
	 * @param url
	 *            连接字符串
	 * @param un
	 *            用户名
	 * @param pwd
	 *            密码
	 * @return 数据库连接
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static Connection getLocConnection(String driveName, String url, String un, String pwd)
			throws ClassNotFoundException, SQLException {
		Connection conn = null;
		Class.forName(driveName);
		conn = DriverManager.getConnection(url, un, pwd);

		return conn;
	}

	/**
	 * 关闭 连接
	 * 
	 * @param in
	 *            连接
	 */
	public static void close(Connection in) {
		if (in != null) {
			try {
				in.close();
			} catch (Exception e) {
			}
		}
	}

	/**
	 * 关闭 statement
	 * 
	 * @param in
	 *            statement
	 */
	public static void close(Statement in) {
		if (in != null) {
			try {
				in.close();
			} catch (Exception e) {
			}
		}
	}

	/**
	 * 关闭 PreparedStatement
	 * 
	 * @param in
	 *            PreparedStatement
	 */
	public static void close(PreparedStatement in) {
		if (in != null) {
			try {
				in.close();
			} catch (Exception e) {
			}
		}
	}

	/**
	 * 关闭resultset
	 * 
	 * @param rs
	 *            resultset
	 */
	public static void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
			}
		}
	}

	public static int executeUpdate(Connection con, List<String> sqls) throws SQLException {
		String[] s = new String[sqls.size()];
		s = sqls.toArray(s);
		return executeUpdate(con, s);
	}

	public static int executeUpdate(Connection con, String[] sqls) throws SQLException {
		int ret = 0;
		Statement sta = null;
		String sql1 = "";
		try {
			sta = con.createStatement();
			for (String sql : sqls) {
				sql1 = sql;
				ret += sta.executeUpdate(sql);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			log.error("sql:" + sql1);
			throw e;
		}

		return ret;
	}

	public static int executeUpdate(Connection con, String sql) throws SQLException {
		return executeUpdate(con, sql, null);
	}

	public static int[] executeUpdates(Connection con, List<String> sqls) throws SQLException {
		String[] s = new String[sqls.size()];
		s = sqls.toArray(s);
		return executeUpdates(con, s);
	}

	public static int[] executeUpdates(Connection con, String[] sqls) throws SQLException {
		int[] rets = new int[sqls.length];
		try {
			int index = 0;
			for (String sql : sqls) {
				rets[index] = executeUpdate(con, sql);
				index++;
			}

		} finally {
		}

		return rets;
	}

	/**
	 * 执行更新 sql 语句
	 * 
	 * @param con
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public static int executeUpdate(Connection con, String sql, Object[] params) throws SQLException {
		int ret = 0;
		PreparedStatement ps = null;

		try {
			ps = con.prepareStatement(sql);
			if (params != null && params.length > 0) {
				int i = 1;
				for (Object o : params) {
					if (null != o) {
						ps.setObject(i, o);
						i++;
					}
				}
			}
			ret = ps.executeUpdate();
		} finally {
			close(ps);
		}

		return ret;
	}

	/**
	 * 批量 执行更改SQL，可以包含 insert 、 update 、delete 等 没有返回值的sql
	 * 
	 * @param con
	 * @param sql
	 *            批量sql语句
	 * @param params
	 *            多条值
	 * @return
	 * @throws SQLException
	 */
	public static int[] executeBatchEdit(Connection con, String sql, List<Object[]> params) throws SQLException {
		int[] ret = null;
		PreparedStatement ps = null;
		try {
			con.setAutoCommit(false);
			ps = con.prepareStatement(sql);
			for (Object[] param : params) {
				for (int i = 1; i <= param.length; i++) {
					ps.setObject(i, param[i - 1]);
				}
				ps.addBatch();
			}
			ret = ps.executeBatch();// 执行批量
			con.commit();// 提交

		} finally {
			close(ps);
		}

		return ret;
	}

	/**
	 * 批量 操作SQL
	 * 
	 * @param con
	 * @param sqls
	 * @return
	 * @throws SQLException
	 */
	public static int[] executeBatchSqls(Connection con, List<String> sqls) throws SQLException {
		int[] ret = null;
		Statement sm = null;
		try {
			con.setAutoCommit(false);
			sm = con.createStatement();
			DatabaseMetaData md = con.getMetaData();
			if (md.supportsBatchUpdates()) {// 支持批插入
				for (String sql : sqls) {
					try {
						sm.addBatch(sql);
					} catch (Exception e) {
						log.error("执行批量错误： " + sql);
					}
				}
				ret = sm.executeBatch();
				con.commit();
			} else {
				// 普通插入
				executeUpdates(con, sqls);
			}
		} finally {
			close(sm);
		}

		return ret;
	}

	/**
	 * 执行查询 sql语句
	 * 
	 * @param con
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public static ResultSet executeQuery(Connection con, String sql) throws SQLException {

		return executeQuery(con, sql, null);
	}

	/**
	 * 执行查询 sql语句
	 * 
	 * @param con
	 * @param sql
	 * @return
	 */
	public static ResultSet executeQuery(Connection con, PreparedStatement ps, String sql) throws SQLException {

		return executeQuery(con, ps, sql, null);
	}

	/**
	 * 执行查询 sql语句
	 * 
	 * @param con
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public static ResultSet executeQuery(Connection con, String sql, List<Object> params) throws SQLException {
		ResultSet rs = null;
		PreparedStatement ps = null;

		try {
			ps = con.prepareStatement(sql);
			if (params != null && params.size() > 0) {
				int i = 1;
				for (Object o : params) {
					ps.setObject(i, o);
					i++;
				}
			}
			rs = ps.executeQuery();
		} finally {
		}

		return rs;
	}

	public static <T> List<T> executeQueryToObject(Connection con, String sql, Object[] params, Class<T> c)
			throws SQLException {
		List<T> lists = new ArrayList<T>();
		executeQueryToObject(con, sql, params, c, lists);
		return lists;
	}

	/**
	 * 执行SQL语句 返回 resultset
	 * 
	 * @param con
	 * @param ps
	 * @param sql
	 * @param params
	 * @return rs
	 * @throws SQLException
	 */
	public static ResultSet executeQuery(Connection con, PreparedStatement ps, String sql, Object[] params)
			throws SQLException {
		ResultSet rs = null;
		try {
			// ps = con.prepareStatement(sql);
			if (params != null && params.length > 0) {
				int i = 1;
				for (Object o : params) {
					ps.setObject(i, o);
					i++;
				}
			}
			rs = ps.executeQuery();

		} finally {

		}

		return rs;
	}

	/**
	 * 将查询sql 语句 结果 转Map
	 * 
	 * @param con
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public static List<Map<String, Object>> executeQueryToMap(Connection con, String sql) throws SQLException {
		return executeQueryToMap(con, sql, null);
	}

	/**
	 * 将查询sql 语句 结果 转Map
	 * 
	 * @param con
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public static List<Map<String, Object>> executeQueryToMap(Connection con, String sql, Object[] params)
			throws SQLException {
		List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
		PreparedStatement ps = null;
		try {

			Map<String, Object> mpa = null;
			ps = con.prepareStatement(sql);

			ResultSet rs = executeQuery(con, ps, sql, params);
			ResultSetMetaData data = rs.getMetaData();

			while (rs.next()) {
				mpa = new HashMap<String, Object>();
				for (int i = 1; i <= data.getColumnCount(); i++) {
					String columnName = data.getColumnName(i);
					Object value = rs.getObject(columnName);
					mpa.put(columnName.toUpperCase(), value);
				}

				lists.add(mpa);
			}

		} finally {
			close(ps);
		}
		return lists;
	}

	/**
	 * 查询结果 返回为 Object
	 * 
	 * @param con
	 *            连接
	 * @param sql
	 *            sql语句
	 * @param params
	 *            参数
	 * @param c
	 *            对象Class
	 * @param lists
	 *            返回数据列表
	 * @throws SQLException
	 */
	public static <T> void executeQueryToObject(Connection con, String sql, Object[] params, Class<T> c, List<T> lists)
			throws SQLException {

		try {
			List<Map<String, Object>> mpas = executeQueryToMap(con, sql, params);
			List<T> l = BeanUtil.ConvertMapToObjects(mpas, c);
			if (l != null && !l.isEmpty()) {
				lists.addAll(l);
			}
		} catch (Exception e) {
			throw new SQLException(e.getMessage());
		}

	}

	/**
	 * 保存 数据
	 * 
	 * @param con
	 * @param data
	 * @param table
	 * @return
	 * @throws SQLException
	 */
	public static <T> int save(Connection con, T data, String table) throws SQLException {
		List<Object> params = new ArrayList<Object>();
		String sql = SQLUtil.createInsertSql(con, table, data, params);
		return executeUpdate(con, sql, params.toArray());
		// return _save(con, getObjectValueToMap(data), table);
	}

	/**
	 * 更新 数据
	 * 
	 * @param con
	 *            连接
	 * @param table
	 *            表明
	 * @param data
	 *            操作对象
	 * @param where
	 *            过滤条件
	 * @return 符合条件条数
	 * @throws SQLException
	 */
	public static <T> int update(Connection con, String table, T data, Map<String, Object> where) throws SQLException {
		List<Object> params = new ArrayList<Object>();
		String sql = SQLUtil.createUpdateSql(con, table, data, where, params);
		return executeUpdate(con, sql, params.toArray());
	}

	/**
	 * 删除数据
	 * 
	 * @param con
	 *            连接
	 * @param table
	 *            表明
	 * @param data
	 *            操作对象
	 * @param where
	 *            过滤条件
	 * @return 符合条件条数
	 * @throws SQLException
	 */
	public static <T> int delete(Connection con, String table, Map<String, Object> where) throws SQLException {
		List<Object> params = new ArrayList<Object>();
		String sql = SQLUtil.createDeleteSql(con, table, where, params);
		return executeUpdate(con, sql, params.toArray());
	}

	/**
	 * 批量 保存
	 * 
	 * @param con
	 * @param datas
	 * @param table
	 * @return
	 * @throws SQLException
	 */
	public static <T> int[] saveBat(Connection con, List<T> datas, String table) throws SQLException {
		// executeBatchEdit
		int[] ret = null;
		String sql = null;
		// 将相同 SQL语句的放到一起 批量执行
		Map<String, List<Object[]>> group = new HashMap<String, List<Object[]>>();
		for (T o : datas) {
			List<Object> _ps = new ArrayList<Object>();
			sql = SQLUtil.createInsertSql(con, table, o, _ps);
			List<Object[]> _pss = group.get(sql);
			if (_pss == null) {
				_pss = new ArrayList<Object[]>();
			}
			_pss.add(_ps.toArray());
			group.put(sql, _pss);
		}
		List<Integer> r = new ArrayList<Integer>();
		for (Entry<String, List<Object[]>> entry : group.entrySet()) {
			ret = executeBatchEdit(con, entry.getKey(), entry.getValue());
			for (int _i : ret) {
				r.add(_i);
			}
		}
		Integer[] _rs = r.toArray(new Integer[r.size()]);
		int[] _rs2 = new int[_rs.length];
		System.arraycopy(_rs, 0, _rs2, 0, _rs.length);
		return _rs2;
	}

	/**
	 * 读取 数据库表 结构信息
	 * 
	 * @time 2014.7.11
	 * @param table
	 * @Deprecated 使用SQLUtil
	 * @return Map<字段名,字段大小>
	 */
	@Deprecated
	public static Map<String, TableFieldBean> readDataTableInfo(Connection con, String table) {
		Map<String, TableFieldBean> ret = new HashMap<String, TableFieldBean>();

		String sql = "select * from " + table + " where  1=0";

		Statement ps = null;
		ResultSet rs = null;

		try {
			ps = con.createStatement();
			rs = ps.executeQuery(sql);

			ResultSetMetaData md = rs.getMetaData();
			for (int i = 1; i <= md.getColumnCount(); i++) {
				TableFieldBean tfb = new TableFieldBean();
				tfb.setName(md.getColumnName(i).toUpperCase());
				tfb.setLength(md.getColumnDisplaySize(i));
				tfb.setType(md.getColumnTypeName(i));

				ret.put(tfb.getName(), tfb);
			}

			return ret;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(ps);
		}

		return ret;
	}

	/**
	 * 表字段定义bean
	 * 
	 * @author Wangch
	 * @Deprecated 使用SQLUtil
	 */
	@Deprecated
	public static class TableFieldBean {
		private String name;// 字段名称
		private String type;// 类型
		private int length;// 长度

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public int getLength() {
			return length;
		}

		public void setLength(int length) {
			this.length = length;
		}

	}

}
