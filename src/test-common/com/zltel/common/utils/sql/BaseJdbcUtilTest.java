package com.zltel.common.utils.sql;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import org.junit.Assert;

public class BaseJdbcUtilTest {
	Connection con;

	public void setUp() throws Exception {
		createConnection();
	}

	public void tearDown() throws Exception {
		BaseJdbcUtil.close(con);
	}

	public void createConnection() throws ClassNotFoundException, SQLException {
		if (con == null) {
			String url = "jdbc:oracle:thin:@${jdbc.database.ip}:${jdbc.database.port}:${jdbc.database.instance}";
			url = url.replace("${jdbc.database.ip}", "192.168.1.109");
			url = url.replace("${jdbc.database.port}", "1521");
			url = url.replace("${jdbc.database.instance}", "orcl");
			String un = "zjmonitorv2_new";
			String pwd = un;
			con = BaseJdbcUtil.getLocConnection(BaseJdbcUtil.oracle_drive_name, url, un, pwd);
		} else {
			System.out.println("Connection 以创建");
		}
	}

	public final void testGetLocConnection() {
		Assert.assertNotNull(con);
	}

	public final void testReadDataTableInfo() {
		Map m = BaseJdbcUtil.readDataTableInfo(con, "zjmonitor_sys_log");
		Assert.assertNotNull(m);
	}

}
