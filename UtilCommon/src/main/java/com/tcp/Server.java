package com.tcp;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by zhouhaiming on 2017-5-11 11:49
 * Email: dg_chow@163.com
 *
 * @Description:服务端 服务端处理TCP连接请求
 */
public class Server {

    //用户授权
    public static Map<String, String> map = new LinkedHashMap<>();

    private static final ExecutorService es = Executors.newFixedThreadPool(10);

    public static void main(String[] args) throws Exception {
        addUser();
        //服务端在20006端口监听客户端请求的TCP连接
        ServerSocket server = new ServerSocket(20006);
        //设置超时
//        server.setSoTimeout(4000);
        Socket client = null;
        boolean f = true;
        while (f) {
            //等待客户端的连接，如果没有获取连接
            client = server.accept();
            System.out.println("与客户端连接成功！");
            //为每个客户端连接开启一个线程
            es.execute(new ServerThread(client));
//            new Thread(new ServerThread(client)).start();
        }
        server.close();
    }
    public static void addUser() {
        map.put("goldmsg","123456");
    }
}
