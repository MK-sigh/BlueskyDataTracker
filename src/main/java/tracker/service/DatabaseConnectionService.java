package tracker.service;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseConnectionService {
    
    private static final Logger logger = LoggerFactory.getLogger(DatabaseConnectionService.class);
    
    private DataSource currentDataSource;
    
    /**
     * データベース接続をテストする
     * 
     * @param url データベースURL
     * @param username ユーザー名
     * @param password パスワード
     * @return 接続成功時true、失敗時false
     */
    public boolean testConnection(String url, String username, String password) {
        logger.info("Testing database connection to: {}", url);
        
        try {
            // 一時的なDataSourceを作成してテスト
            DataSource testDataSource = createDataSource(url, username, password);
            
            try (Connection connection = testDataSource.getConnection()) {
                // 接続テスト用の簡単なクエリを実行
                boolean isValid = connection.isValid(5); // 5秒タイムアウト
                logger.info("Database connection test result: {}", isValid);
                return isValid;
            }
        } catch (SQLException e) {
            logger.error("Database connection test failed: {}", e.getMessage());
            return false;
        } catch (Exception e) {
            logger.error("Unexpected error during connection test: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * DataSourceを切り替える
     * 
     * @param url データベースURL
     * @param username ユーザー名
     * @param password パスワード
     * @throws SQLException 接続に失敗した場合
     */
    public void switchDataSource(String url, String username, String password) throws SQLException {
        logger.info("Switching DataSource to: {}", url);
        
        // 新しいDataSourceを作成
        DataSource newDataSource = createDataSource(url, username, password);
        
        // 接続テストを実行
        try (Connection connection = newDataSource.getConnection()) {
            if (!connection.isValid(5)) {
                throw new SQLException("Invalid database connection");
            }
        }
        
        // 古いDataSourceをクローズ（HikariDataSourceの場合）
        if (currentDataSource instanceof HikariDataSource) {
            ((HikariDataSource) currentDataSource).close();
        }
        
        // 新しいDataSourceを設定
        this.currentDataSource = newDataSource;
        logger.info("DataSource switched successfully");
    }
    
    /**
     * 現在のDataSourceを取得する
     * 
     * @return 現在のDataSource
     */
    public DataSource getCurrentDataSource() {
        return currentDataSource;
    }
    
    /**
     * DataSourceが設定されているかチェック
     * 
     * @return DataSourceが設定されている場合true
     */
    public boolean isDataSourceConfigured() {
        return currentDataSource != null;
    }
    
    /**
     * DataSourceを作成する
     * 
     * @param url データベースURL
     * @param username ユーザー名
     * @param password パスワード
     * @return 作成されたDataSource
     */
    private DataSource createDataSource(String url, String username, String password) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName("org.postgresql.Driver");
        
        // 接続プール設定
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);
        config.setConnectionTimeout(30000); // 30秒
        config.setIdleTimeout(600000); // 10分
        config.setMaxLifetime(1800000); // 30分
        
        return new HikariDataSource(config);
    }
}
