package com.tcp;

import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * Created by zhouhaiming on 2017-5-11 11:38
 * Email: dg_chow@163.com
 *
 * @Description: 客户端
 */

/*
下面给出一个客户端服务端TCP通信的Demo，该客户端在20006端口请求与服务端建立TCP连接，客户端不断接收键盘输入，并将其发送到服务端，
服务端在接收到的数据前面加上“echo”字符串，并将组合后的字符串发回给客户端，如此循环，直到客户端接收到键盘输入“bye”为止。
*/

public class Client {
    public static void main(String[] args) throws IOException {
//        FilterInputStream stream = new DataInputStream();
//        OutputStream outputStream = new FileOutputStream();
//        OutputStreamWriter outputStreamWriter= new OutputStreamWriter(outputStream);
//        Writer client = outputStreamWriter;

//        //客户端请求与本机在20006端口建立TCP连接
        Socket client = new Socket("127.0.0.1", 20006);
//        client.setSoTimeout(4000);
        //获取键盘输入
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        //获取Socket的输出流，用来发送数据到服务端
        PrintStream out = new PrintStream(client.getOutputStream());
        //获取Socket的输入流，用来接收从服务端发送过来的数据
        BufferedReader buf = new BufferedReader(new InputStreamReader(client.getInputStream()));
        boolean flag = true;
        while (flag) {
            System.out.print("输入信息：");
            String str = input.readLine();
            //发送数据到服务端
            out.println(str);
            out.flush();
            if ("bye".equals(str)) {
                flag = false;
            } else {
                try {
                    //从服务器端接收数据有个时间限制（系统自设，也可以自己设置），超过了这个时间，便会抛出该异常
                    String echo = buf.readLine();
                    System.out.println(echo);
                } catch (SocketTimeoutException e) {
                    System.out.println("Time out, No response");
                }
            }
        }
        input.close();
        out.close();
        if (client != null) {
            //如果构造函数建立起了连接，则关闭套接字，如果没有建立起连接，自然不用关闭
            client.close(); //只关闭socket，其关联的输入输出流也会被关闭
        }
    }
}

