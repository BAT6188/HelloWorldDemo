package com.goldmsg.ftputil.bean;

/**
 * Created with IntelliJ IDEA.
 * User: 周海明
 * Date: 2016/12/29
 * Time: 9:23
 */

/**
 * ftp链接常量
 */

public class FtpConnectBean  {

    private String host;//ip地址

    private Integer port;//端口号

    private String username;//用户名

    private String password;//密码

    private String path;//工作路径


    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}