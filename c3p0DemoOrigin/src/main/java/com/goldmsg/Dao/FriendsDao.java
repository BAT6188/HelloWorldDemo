package com.goldmsg.Dao;


import com.goldmsg.bean.Friends;
import com.goldmsg.common.dbutil.C3P0Util;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouhaiming on 2016/9/26.
 */
public class FriendsDao {
    // 要执行的SQL语句
    public static String SQL_QUERY_FRIENDS = "SELECT * FROM friends ";
    public static String sql = "select * from friends where id = ?";

    public static List<Friends> ObjFriends() {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection conn = C3P0Util.getConnection();
        List<Friends> friendses = new ArrayList<Friends>();
        try {
//            stmt = conn.prepareStatement(SQL_QUERY_FRIENDS);
//            rs = stmt.executeQuery(SQL_QUERY_FRIENDS);
/*PrepareStatement用来执行SQL语句（正式开发中一般很少用statement,一般用PrepareStatement。
因为tatement用于执行静态sql语句，在执行时，必须指定一个事先准备好的sql语句。）*/
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, 81);
            rs = stmt.executeQuery();
            // 展开结果集数据库
            while (rs.next()) {
                Friends friends = new Friends();
                friends.setId(rs.getInt("id"));
                friends.setName(rs.getString("name"));
                friends.setNum(rs.getString("num"));
                friends.setCompany(rs.getString("company"));
                friendses.add(friends);
            }
            FriendsDao.printLine(friendses);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            C3P0Util.close(conn, stmt, rs);//每次使用完后都要关闭
        }
        return friendses;
    }

    public static void printLine(List<Friends> objFriendses) {
        for (Friends friends : objFriendses) {
            System.out.println(friends.getId() + "\t" + friends.getName() + "\t" + friends.getNum() + "\t" + friends.getCompany());
        }
    }
}
