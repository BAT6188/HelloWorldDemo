package ooopen.hessian.test;

import com.caucho.hessian.client.HessianProxyFactory;

import java.net.MalformedURLException;

public class Client {

    public static void main(String[] args){
        String url = "http://localhost:9011/hessian";
        HessianProxyFactory factory = new HessianProxyFactory();
        try {
            Hello hello = (Hello)factory.create(Hello.class, url);
            System.out.println(hello.getCurUser());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

}
