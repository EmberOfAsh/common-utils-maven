package com.zltel.common.utils.sql;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.zltel.common.utils.sql.SQLUtil.TableInfoBean;

public class SQLUtilTest {

	Connection con;

	public void setUp() throws Exception {
		createConnection();
	}

	public void tearDown() throws Exception {
		BaseJdbcUtil.close(con);
	}

	public void createConnection() throws ClassNotFoundException, SQLException {
		if (con == null) {
			String url = "jdbc:mysql://${jdbc.mysql.ip}:${jdbc.mysql.port}/${jdbc.mysql.instance}";
			url = url.replace("${jdbc.mysql.ip}", "192.168.1.119");
			url = url.replace("${jdbc.mysql.port}", "3307");
			url = url.replace("${jdbc.mysql.instance}",
					"hadoop_date?useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull");
			String un = "root";
			String pwd = "123456";
			con = BaseJdbcUtil.getLocConnection(BaseJdbcUtil.oracle_drive_name, url, un, pwd);
		} else {
			System.out.println("Connection 以创建");
		}
	}

	@Test
	public final void testReadTableInfo() throws SQLException, ClassNotFoundException {
		createConnection();
		TableInfoBean ti = SQLUtil.readTableInfo(con, "analyse_job");

		System.out.println(ti.getTableName() + " 列:" + ti.tableFieldNames());
	}

	@Test
	public final void testcreateInsertSql() throws Exception {
		createConnection();
		Map<String, Object> v = new HashMap<String, Object>();
		v.put("OID", "123");
		v.put("JOB_TYPE", "1");

		List<Object> params = new ArrayList<Object>();
		String sql = SQLUtil.createInsertSql(con, "analyse_job", v, params);

		System.out.println("insert: " + sql);
		System.out.println(params);
	}

	@Test
	public final void testcreateUpdateSql() throws Exception {
		createConnection();
		Map<String, Object> v = new HashMap<String, Object>();
		v.put("OID", "123");
		v.put("JOB_TYPE", "1");
		Map<String, Object> where = new HashMap<String, Object>();
		where.put("OID", "3");

		List<Object> params = new ArrayList<Object>();
		String sql = SQLUtil.createUpdateSql(con, "analyse_job", v, where, params);

		System.out.println("UPDATE: " + sql);
		System.out.println(params);
	}

	@Test
	public final void testcreateDeleteSql() throws Exception {
		createConnection();
		Map<String, Object> v = new HashMap<String, Object>();
		v.put("OID", "123");
		v.put("JOB_TYPE", "1");
		Map<String, Object> where = new HashMap<String, Object>();
		where.put("OID", "3");

		List<Object> params = new ArrayList<Object>();
		String sql = SQLUtil.createDeleteSql(con, "analyse_job", where, params);

		System.out.println("DELETE: " + sql);
		System.out.println(params);
	}

	@Test
	public final void testcreateQuerySql() throws Exception {
		createConnection();
		Map<String, Object> v = new HashMap<String, Object>();
		v.put("OID", "123");
		v.put("JOB_TYPE", "1");
		Map<String, Object> where = new HashMap<String, Object>();
		where.put("OID", "3");

		List<Object> params = new ArrayList<Object>();
		String sql = SQLUtil.createSelectSql(con, "analyse_job", where, params);

		System.out.println("Query: " + sql);
		System.out.println(params);
	}

}
