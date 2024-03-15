package moe.moti.simplewindow.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;
import java.util.*;

/**
 * 手动 HikariCP 连接
 */
public class SQLUtil {
    private final HikariDataSource dataSource;

    public SQLUtil() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(EnvironmentConstant.SQL_DATA_URL);
        config.setUsername(EnvironmentConstant.SQL_DATA_USERNAME);
        config.setPassword(EnvironmentConstant.SQL_DATA_PASSWORD);
        dataSource = new HikariDataSource(config);
    }

    /**
     * 执行单条 SQL 语句
     * @param sql SQL 语句
     * @return 影响行数
     */
    public int execute(String sql) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            return preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 执行多条 SQL 语句
     * @param sqlList 多条 SQL 语句
     * @return 影响行数
     */
    public int executeBatch(Collection<String> sqlList) {
        if (null == sqlList || sqlList.size() == 0) {
            return 0;
        }
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            for (String sql : sqlList) {
                statement.addBatch(sql);
            }
            int[] result = statement.executeBatch();
            return Arrays.stream(result).sum();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 查询单条 SQL 语句
     * @param sql SQL 语句
     * @return 查询结果
     */
    public List<Map<String, Object>> query(String sql) {
        System.out.println("sql:" + sql);
        List<Map<String, Object>> resultList = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (resultSet.next()) {
                Map<String, Object> row = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    String columnLabel = metaData.getColumnLabel(i);
                    Object value = resultSet.getObject(i);
                    row.put(columnLabel, value);
                }
                resultList.add(row);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultList;
    }

}
