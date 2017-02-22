package com.goldmsg.ftputil.commonUtils;

import com.goldmsg.ftputil.FtpUploadMain;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;


public class DBUtils {
    public static final Logger logger = LogManager.getLogger(DBUtils.class);

    private static ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();

    private static DataSource dataSource;

    public static DataSource getDataSource() {
        try {
            comboPooledDataSource.setDriverClass(FtpUploadMain.xmlConfiguration.getConf("jdbc.driver", "com.mysql.jdbc.Driver"));
            comboPooledDataSource.setJdbcUrl(FtpUploadMain.xmlConfiguration.getConf("jdbc.url", "jdbc:mysql://10.10.6.153:3306/gmvcsws"));
            comboPooledDataSource.setUser(FtpUploadMain.xmlConfiguration.getConf("jdbc.username", "root"));
            comboPooledDataSource.setPassword(FtpUploadMain.xmlConfiguration.getConf("jdbc.password", "123456"));
            dataSource = comboPooledDataSource;
            logger.info("数据源初始化成功!");
        } catch (PropertyVetoException e) {
            e.printStackTrace();
            logger.info("数据源初始化失败:" + e.getMessage());
        }
        return dataSource;
    }

    public static List executeQuery(String vSql, Object... params) throws SQLException {
        Connection conn = dataSource.getConnection();
        QueryRunner qRunner = new QueryRunner();

        List list = null;
        try {
            if (logger.isDebugEnabled()) {
                logger.debug("SQL DEBUG:" + vSql + " " + Arrays.toString(params));
            }
            ResultSetHandler rsh = new MapListHandler();
            list = (List) qRunner.query(conn, vSql, rsh, params);
        } finally {
            DbUtils.closeQuietly(conn);
        }
        return list;
    }
}
