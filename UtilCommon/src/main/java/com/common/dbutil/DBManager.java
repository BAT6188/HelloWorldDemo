package com.common.dbutil;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

public class DBManager {
    public static final Logger logger = LogManager.getLogger(DBManager.class);
    private static DBManager instance = null;
    protected ComboPooledDataSource dataSource = null;

    protected String dbUserName = "root";
    protected String dbPassWord = "123456";
    protected String dbIP = "127.0.0.1";
    protected String port = "3306";

    protected String jdbcUrl;
    protected String driverClass = "com.mysql.jdbc.Driver";

    protected int initSize = 10;                               // 设置初始连接池的大小
    protected int minPoolSize = 10;                            // 设置连接池的最小值
    protected int maxPoolSize = 50;                            // 设置连接池的最大值
    protected int maxStatements = 50;                         // 设置连接池中的最大Statements数量
    protected int maxIdleTime = 60;                           // 设置连接池的最大空闲时间

    private DBManager() {
    }

    public static DBManager getInstance() throws RuntimeException {
        if (null == instance) {
            synchronized (DBManager.class) {
                if (null == instance) {
                    instance = new DBManager();
                }
            }
        }
        return instance;
    }

    public final Connection getConnection() throws SQLException {
        if (null == dataSource) {
            synchronized (this) {
                if (null == dataSource) {
                    jdbcUrl = "jdbc:mysql://" + dbIP + ":" + port + "/mysql?relaxAutoCommit=true&zeroDateTimeBehavior=convertToNull&useUnicode=true&characterEncoding=utf-8";

                    dataSource = new ComboPooledDataSource();
                    dataSource.setUser(dbUserName);
                    dataSource.setPassword(dbPassWord);
                    dataSource.setJdbcUrl(jdbcUrl);
                    try {
                        dataSource.setDriverClass(driverClass);
                    } catch (PropertyVetoException e) {
                        e.printStackTrace();
                    }

                    dataSource.setInitialPoolSize(initSize);
                    dataSource.setMinPoolSize(minPoolSize);
                    dataSource.setMaxPoolSize(maxPoolSize);
                    dataSource.setMaxStatements(maxStatements);
                    dataSource.setMaxIdleTime(maxIdleTime);
                    dataSource.setAutoCommitOnClose(true);
                    System.out.println("初始数据源" + jdbcUrl + "完成");
                }
            }
        }
        return dataSource.getConnection();
    }

    public String getDbUserName() {
        return dbUserName;
    }

    public void setDbUserName(String dbUserName) {
        this.dbUserName = dbUserName;
    }

    public String getDbPassWord() {
        return dbPassWord;
    }

    public void setDbPassWord(String dbPassWord) {
        this.dbPassWord = dbPassWord;
    }

    public String getDbIP() {
        return dbIP;
    }

    public void setDbIP(String dbIP) {
        this.dbIP = dbIP;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public int getMinPoolSize() {
        return minPoolSize;
    }

    public void setMinPoolSize(int minPoolSize) {
        this.minPoolSize = minPoolSize;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public int getMaxStatements() {
        return maxStatements;
    }

    public void setMaxStatements(int maxStatements) {
        this.maxStatements = maxStatements;
    }

    public int getMaxIdleTime() {
        return maxIdleTime;
    }

    public void setMaxIdleTime(int maxIdleTime) {
        this.maxIdleTime = maxIdleTime;
    }
}
