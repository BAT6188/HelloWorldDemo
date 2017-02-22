package com.goldmsg.ftputil;

import com.goldmsg.ftputil.bean.FtpConnectBean;
import com.goldmsg.ftputil.bean.GetFileBean;
import com.goldmsg.ftputil.commonUtils.ClientException;
import com.goldmsg.storage.protocol.bean.ReplicaTaskException;
import org.apache.commons.net.ftp.FTPClient;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;


/**
 * Created with IntelliJ IDEA.
 * User: 周海明
 * Date: 2017/1/4
 * Time: 15:00
 */
public abstract class FileUpload {
    /**
     * 获取需要上传的重要文件
     */
    public abstract GetFileBean getFileBean() throws IOException, ClientException, ReplicaTaskException;

    /**
     * ftp上传文件，提交重要文件上传状态
     *
     * @param body  获取需要上传的重要文件响应参数
     * @param ftpClient
     */
    public abstract void postFileStatus(GetFileBean.Body body,FTPClient ftpClient) throws SQLException, ClientException, MalformedURLException, ReplicaTaskException;

    /**
     * 初始化FtpBasebean
     *
     * @param body  获取需要上传的重要文件响应参数
     */
    public abstract FtpConnectBean initFtp(GetFileBean.Body body);

}
