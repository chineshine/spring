/**
 * 
 */
package c.s.test.sample.controller;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author chineshine
 *
 */
@Slf4j
public final class SqlHelper {

	private static final String driver = "com.mysql.jdbc.Driver";
	private static final String url = "jdbc:mysql://192.168.50.235:3306/yl-application-management?useSSL=false&useUnicode=yes&autoReconnect=true&failOverReadOnly=false&amp;characterEncoding=UTF-8";
	private static final String user = "root";
	private static final String password = "123456";

	public static Connection initConnection() throws SQLException {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			log.error(e.getMessage(), e);
		}
		return DriverManager.getConnection(url, user, password);
	}

	private static void executeBatchSql(Connection connection, List<String> sqlBatch) throws SQLException {
		String prepareSql = "select 1;";
		PreparedStatement statement = connection.prepareStatement(prepareSql);
		connection.setAutoCommit(false);
		for (String sql : sqlBatch) {
			statement.addBatch(sql);
		}
		statement.executeBatch();
		connection.commit();
	}

	private static List<String> getSqls(String sqlFile) {
		if (!StringUtils.hasText(sqlFile)) {
			return null;
		}
		ClassPathResource resource = new ClassPathResource(sqlFile);
		assertTrue("文件不存在", resource.exists());
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
			List<String> sqlBatch = new ArrayList<>();
			String line = null;
			while ((line = reader.readLine()) != null) {
				if (line.isEmpty()) {
					continue;
				}
				sqlBatch.add(line);
			}
			return sqlBatch;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void executeSqlFile(Connection connection, String sqlFile) throws SQLException {
		log.info("开始执行sql脚本");
		List<String> sqlBatch = getSqls(sqlFile);
		if (CollectionUtils.isEmpty(sqlBatch)) {
			log.info("未检测到sql文件或未检测到sql执行脚本");
			return;
		}
		executeBatchSql(connection, sqlBatch);
	}

}
