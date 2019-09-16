/**
 * 
 */
package c.s.test.sample.demo;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;

import c.s.test.sample.controller.ControllerTestBase;
import c.s.test.sample.controller.SqlHelper;


/**
 * @author chineshine
 *
 */
public class DemoControllerTest extends ControllerTestBase{
	
	private static Connection connection;

	@BeforeClass
	public static void tearUp() throws SQLException {
		// 执行初始化sql脚本
		String sqlFile = "sql/index-config/init.sql";
		connection = SqlHelper.initConnection();
		SqlHelper.executeSqlFile(connection, sqlFile);
	}

	@AfterClass
	public static void tearDown() throws SQLException {
		// 测试用例执行完成后删除脚本
		String sqlFile = "sql/index-config/update.sql";
		SqlHelper.executeSqlFile(connection, sqlFile);
		connection.close();
	}

	/**
	 * Test method for {@link c.s.test.sample.demo.DemoController#post()}.
	 */
	@Test
	public void testPost() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link c.s.test.sample.demo.DemoController#get()}.
	 */
	@Test
	public void testGet() {
		String value = super.search("/demo/get", null, String.class);
		assertEquals("get", value);
	}

	/**
	 * Test method for {@link c.s.test.sample.demo.DemoController#delete()}.
	 */
	@Test
	public void testDelete() {
		fail("Not yet implemented");
	}

	
	public @Autowired TestRestTemplate testRestTemplate;
	@Override
	public TestRestTemplate getTestRestTemplate() {
		return testRestTemplate;
	}

}
