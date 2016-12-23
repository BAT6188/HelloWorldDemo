package com.helloworld.c3p0Hello;

import com.helloworld.c3p0Hello.service.impl.StorageServiceImpl;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: 周海明
 * Date: 2016/12/23
 * Time: 14:18
 */
public class Main {
    private static Logger log = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        StorageServiceImpl storageService = new StorageServiceImpl();
        List<Map> mapList = storageService.queryFriends(89);
        System.out.println(mapList.toString());
    }
}
