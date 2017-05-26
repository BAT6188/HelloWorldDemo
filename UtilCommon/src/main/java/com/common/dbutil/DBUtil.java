package com.common.dbutil;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;


public class DBUtil {
    public static final Logger logger = LogManager.getLogger(DBUtil.class);

    public static int executeUpdate(String vSql, Object... params) throws SQLException {
        Connection conn = DBManager.getInstance().getConnection();
        QueryRunner qRunner = new QueryRunner();

        int count = 0;
        try {
            count = qRunner.update(conn, vSql, params);    
        } finally {
            DbUtils.closeQuietly(conn);
        }

        return count;
    }

    public static List executeQuery(String vSql, Object... params) throws SQLException {
        Connection conn = DBManager.getInstance().getConnection();
        QueryRunner qRunner = new QueryRunner();

        List list = null;
        try {
            ResultSetHandler rsh = new MapListHandler();
            list = (List) qRunner.query(conn, vSql, rsh, params);  
        } finally {
            DbUtils.closeQuietly(conn);
        }
 

        return list;
    }

    public static void executeTransaction(List<String> tList_sql) throws SQLException {
        Connection conn = DBManager.getInstance().getConnection();
        try {
            conn.setAutoCommit(false);
            Statement statement = conn.createStatement();
            for (String tSQL : tList_sql) {
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
        Connection conn = DBManager.getInstance().getConnection();
        try {
            conn.setAutoCommit(false);
            QueryRunner qRunner = new QueryRunner();

            for(Map reqMap : tList_sql) {
                String vSql = reqMap.get("sql").toString();
                Object[] params = (Object[])reqMap.get("params");
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
        List<Map> tList = DBUtil.executeQuery(vSQL);
        Map tMap = tList.get(0);

        int tRet = Integer.parseInt(tMap.get("count").toString());
        return tRet;
    }
    
    public static int getPreRecordCountBySQL(String vSQL,Object... params) throws SQLException {
        List<Map> tList = DBUtil.executeQuery(vSQL,params);
        Map tMap = tList.get(0);

        int tRet = Integer.parseInt(tMap.get("count").toString());
        return tRet;
    }
    
    public static void executeTransaction(Connection conn, List<Map> tList_sql)
            throws SQLException{
        try {
            QueryRunner qRunner = new QueryRunner();

            for(Map reqMap : tList_sql) {
                String vSql = reqMap.get("sql").toString();
                Object[] params = (Object[])reqMap.get("params");
                qRunner.update(conn, vSql, params);    
            }
        } catch (SQLException ex) {
            conn.rollback();
            throw new SQLException(ex);
        }
        
    }
}
