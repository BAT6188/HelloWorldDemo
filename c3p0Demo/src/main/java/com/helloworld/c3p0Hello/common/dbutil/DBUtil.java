package com.helloworld.c3p0Hello.common.dbutil;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class DBUtil {
    public static final Logger logger = LogManager.getLogger(DBUtil.class);

    private static ComboPooledDataSource dataSource = new ComboPooledDataSource("mysql");//初始化数据库
//    private static ComboPooledDataSource doracleDataSource = new ComboPooledDataSource("oracle");//初始化数据库

    public static ComboPooledDataSource getDataSource() {
        return dataSource;
    }

    public static void setDataSource(ComboPooledDataSource ds) {
        dataSource = ds;
    }

    public static int executeUpdate(String vSql, Object... params) throws SQLException {
        Connection conn = dataSource.getConnection();
        QueryRunner qRunner = new QueryRunner();

        int count = 0;
        try {
            if (logger.isDebugEnabled()) {
                logger.debug("SQL DEBUG:" + vSql + " " + Arrays.toString(params));
            }
            count = qRunner.update(conn, vSql, params);
        } finally {
            DbUtils.closeQuietly(conn);
        }

        return count;
    }
public static  int excuteUpDate (String sql, Object... params) throws SQLException {
    int count =0 ;
    Connection connection = dataSource.getConnection();
    QueryRunner runner = new QueryRunner();
    try {
        if (logger.isTraceEnabled())
        count = runner.update(connection, sql, params);

    } catch (Exception e) {
        logger.error("更新失败",e);

    }finally {
        DbUtils.close(connection);
    }
    return count;
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

    public static void executeTransaction(List<String> tList_sql) throws SQLException {
        Connection conn = dataSource.getConnection();
        try {
            conn.setAutoCommit(false);
            Statement statement = conn.createStatement();
            for (String tSQL : tList_sql) {
                if (logger.isDebugEnabled()) {
                    logger.debug("SQL DEBUG:" + tSQL + " []");
                }
                statement.executeUpdate(tSQL);
            }
            conn.commit();
        } catch (SQLException ex) {
            conn.rollback();
            throw new SQLException(ex);
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }

    public static void executePreTransaction(List<Map> tList_sql) throws SQLException {
        Connection conn = dataSource.getConnection();
        try {
            conn.setAutoCommit(false);
            QueryRunner qRunner = new QueryRunner();

            for (Map reqMap : tList_sql) {
                String vSql = reqMap.get("sql").toString();
                Object[] params = (Object[]) reqMap.get("params");
                if (logger.isDebugEnabled()) {
                    logger.debug("SQL DEBUG:" + vSql + " " + Arrays.toString(params));
                }
                qRunner.update(conn, vSql, params);
            }
            conn.commit();
        } catch (SQLException ex) {
            conn.rollback();
            throw new SQLException(ex);
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }

    public static int getRecordCountBySQL(String vSQL) throws SQLException {
        if (logger.isDebugEnabled()) {
            logger.debug("SQL DEBUG:" + vSQL + " []");
        }
        List<Map> tList = DBUtil.executeQuery(vSQL);
        Map tMap = tList.get(0);

        int tRet = Integer.parseInt(tMap.get("count").toString());
        return tRet;
    }

    public static int getPreRecordCountBySQL(String vSQL, Object... params) throws SQLException {
        if (logger.isDebugEnabled()) {
            logger.debug("SQL DEBUG:" + vSQL + " []");
        }
        List<Map> tList = DBUtil.executeQuery(vSQL, params);
        Map tMap = tList.get(0);

        int tRet = Integer.parseInt(tMap.get("count").toString());
        return tRet;
    }

    public static void executeTransaction(Connection conn, List<Map> tList_sql)
            throws SQLException {
        try {
            QueryRunner qRunner = new QueryRunner();

            for (Map reqMap : tList_sql) {
                String vSql = reqMap.get("sql").toString();
                Object[] params = (Object[]) reqMap.get("params");
                if (logger.isDebugEnabled()) {
                    logger.debug("SQL DEBUG:" + vSql + " " + Arrays.toString(params));
                }
                qRunner.update(conn, vSql, params);
            }
        } catch (SQLException ex) {
            conn.rollback();
            throw new SQLException(ex);
        }

    }
}
