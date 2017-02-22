package com.goldmsg.ftputil.commonUtils;

/**
 * Created with IntelliJ IDEA.
 * User: 周海明
 * Date: 2016/12/29
 * Time: 9:15
 */

import com.goldmsg.ftputil.FileUpload;
import com.goldmsg.ftputil.bean.FtpConnectBean;
import com.goldmsg.ftputil.FtpUploadMain;
import com.sun.org.apache.regexp.internal.RE;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.*;

public class FtpUtils {
    public static Logger logger = LogManager.getLogger(FtpUtils.class);

    /**
     * 获取ftp连接
     *
     * @param ftpConnectBean
     * @return
     * @throws Exception
     */
    public static FTPClient connectFtp(FtpConnectBean ftpConnectBean) throws Exception {
        FTPClient ftpClient = new FTPClient();
        int reply;
        if (ftpConnectBean.getPort() == null) {
            ftpClient.connect(ftpConnectBean.getHost(), 21);
        } else {
            ftpClient.connect(ftpConnectBean.getHost(), ftpConnectBean.getPort());
        }
        ftpClient.login(ftpConnectBean.getUsername(), ftpConnectBean.getPassword());
        ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
        ftpClient.setBufferSize(1024);//设置上传缓存大小
        ftpClient.setControlEncoding("UTF-8");//设置编码
        ftpClient.setDataTimeout(1000 * 60 * 60 * 12);//设置传输超时（12小时）
        reply = ftpClient.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftpClient.disconnect();
            throw new ClientException("Ftp连接失败：" + reply);
        }
        ftpClient.changeWorkingDirectory(ftpConnectBean.getPath());
        return ftpClient;
    }

    /**
     * 关闭ftp连接
     */
    public static void closeFtp(FTPClient ftpClient) {
        if (ftpClient != null && ftpClient.isConnected()) {
            try {
                ftpClient.logout();
                ftpClient.disconnect();
            } catch (IOException e) {
                logger.error("关闭FTP连接失败", e);
            }
        }
    }

    /**
     * ftp上传文件
     *
     * @param file
     * @throws Exception
     */
    public static UploadStatus upload(File file, FTPClient ftpClient) throws Exception {
        FileInputStream input = new FileInputStream(file);
        String path = new String(file.getName().getBytes("GBK"), "ISO-8859-1");//中文转码解决中文文件不能生成问题
        FTPFile[] files = ftpClient.listFiles(path);
        //检查远程是否存在文件
        if (files.length == 1) {
            long remoteSize = files[0].getSize();
            long localSize = file.length();
            if (remoteSize == localSize) {
                return UploadStatus.File_Exits;
            } else if (remoteSize > localSize) {
                return UploadStatus.Remote_Bigger_Local;
            }
            // 尝试移动文件内读取指针,实现断点续传
            if (input.skip(remoteSize) == remoteSize) {
                ftpClient.setRestartOffset(remoteSize);
                if (ftpClient.storeFile(path, input)) {
                    input.close();
                    return UploadStatus.Upload_From_Break_Success;
                }
            }
            //如果断点续传没有成功，则删除服务器上文件，重新上传
            if (!ftpClient.deleteFile(path)) {
                input.close();
                return UploadStatus.Delete_Remote_Faild;
            }
            input = new FileInputStream(file);
            return storeFile(ftpClient, path, input);
        } else {//直接上传文件
            return storeFile(ftpClient, path, input);
        }
    }

    /**
     * 存储文件
     *
     * @param ftpClient
     * @param path      新上传的文件名
     * @param input     文件输入流
     * @throws Exception
     */
    private static UploadStatus storeFile(FTPClient ftpClient, String path, FileInputStream input) throws IOException {
        if (ftpClient.storeFile(path, input)) {
            input.close();
            return UploadStatus.Upload_New_File_Success;
        } else {
            input.close();
            return UploadStatus.Upload_New_File_Failed;
        }

    }

    //枚举类UploadStatus代码
    public enum UploadStatus {
        Upload_File_Failed,//上传文件失败
        Upload_New_File_Success, //上传新文件成功
        Upload_New_File_Failed,   //上传新文件失败
        File_Exits,      //文件已经存在
        Remote_Bigger_Local,   //远程文件大于本地文件
        Upload_From_Break_Success, //断点续传成功
        Upload_From_Break_Failed, //断点续传失败
        Delete_Remote_Faild;   //删除远程文件失败
    }

}
