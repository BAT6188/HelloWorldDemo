package com.goldmsg;

import com.goldmsg.Dao.FriendsDao;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Created by zhouhaiming on 2016/9/26.
 */
public class SelectMain {
    public static final Logger LOG = LogManager.getLogger(SelectMain.class);

    public static void main(String[] args) {
        String etcDir = args[0];
        if (!(etcDir.endsWith("/") || etcDir.endsWith("\\"))) {
            etcDir = etcDir + "/";
        }
        PropertyConfigurator.configure(etcDir + "log4j.properties");//初始化log

        FriendsDao selects = new FriendsDao();
        try {
            selects.ObjFriends();
        } catch (Exception e) {
            LOG.error("错误",e);
        }

    }
}
