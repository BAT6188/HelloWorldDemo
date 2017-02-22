package com.helloworld.c3p0Hello.persistent;


import com.helloworld.c3p0Hello.FriendBean;
import com.helloworld.c3p0Hello.common.dbutil.DBUtil;

import java.sql.SQLException;
import java.util.List;

public class FriendsDao {
    public static String SQL_QUERY_Fridends =
            "SELECT " +
                    "id," +
                    "name," +
                    "num," +
                    "company " +
                    "FROM " +
                    "friends" +
                    " WHERE id=?";

    /**
     * @param friendId
     * @return
     */
    public List<FriendBean> queryFriends(int friendId) throws SQLException {
        List<FriendBean> result = null;
        result = DBUtil.executeQuery(SQL_QUERY_Fridends, friendId);
        return result;
    }

}
