package com.helloworld.c3p0Hello.service.impl;


import com.helloworld.c3p0Hello.FriendBean;
import com.helloworld.c3p0Hello.persistent.FriendsDao;
import com.helloworld.c3p0Hello.service.interf.FindFriends;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: 周海明
 * Date: 2016/12/20
 * Time: 10:33
 */

public class StorageServiceImpl implements FindFriends {
    public static Logger LOG = LogManager.getLogger(StorageServiceImpl.class);

    @Override
    public List<FriendBean> queryFriends(int var1) {
        return queryFileEx(var1);
    }

    public List<FriendBean> queryFileEx(int id) {
        List<FriendBean> tList = null;
        try {
            tList = new FriendsDao().queryFriends(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (tList.size() == 0) {
            return null;
        }
        return tList;
    }
}
