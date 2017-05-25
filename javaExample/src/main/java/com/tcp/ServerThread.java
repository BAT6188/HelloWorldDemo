package com.tcp;

import javax.print.attribute.standard.Chromaticity;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.nio.ByteBuffer;

/**
 * Created by zhouhaiming on 2017-5-11 11:40
 * Email: dg_chow@163.com
 *
 * @Description:该类为多线程类，用于服务端
 * 服务端需要用到多线程，这里单独写了一个多线程类
 */
public class ServerThread implements Runnable {
    private Socket client = null;
    public ServerThread(Socket client){
        this.client = client;
    }

    @Override
    public void run() {
        try{
            //获取Socket的输出流，用来向客户端发送数据
            PrintStream out = new PrintStream(client.getOutputStream());
            //获取Socket的输入流，用来接收从客户端发送过来的数据
            BufferedReader buf = new BufferedReader(new InputStreamReader(client.getInputStream()));
            boolean flag =true;
            while(flag){
                //接收从客户端发送过来的数据
                System.out.println(Thread.currentThread().getName()+"请求：");
                String str =  buf.readLine();
                if(str == null || "".equals(str)){
                    out.println("echo:" + str);
                }else{
                    String name=null;
                    String password=null;
                    try {
                        name = str.substring(0, 7);
                        password = str.substring(7);
                    } catch (Exception e) {
                        out.println("echo:请输入7位用户名和6位密码:goldmsg123456\n" + e.getMessage());
//                        flag = false;
                        continue;
                    }
                    System.out.println(name+":"+password);
                    boolean isName = Server.map.containsKey(name);
                    boolean isPasswrod=Server.map.get(name).equals(password);
                    if (isName==false) {
                        out.println("echo:" + "不存在用户");
                        flag = false;
                    }
                    if (isPasswrod == false) {
                        out.println("echo:" + "秘钥错误");
                        flag = false;
                    }
                    if("bye".equals(str)){
                        out.println("echo:" + str);
                        flag = false;
                    }else{
                        //将接收到的字符串前面加上echo，发送到对应的客户端
                        out.println("echo:" + str);
                    }
                }
            }
            out.close();
            client.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
